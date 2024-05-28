package io.github.togar2.pvp.feature.block;

import io.github.togar2.pvp.damage.DamageTypeInfo;
import io.github.togar2.pvp.entity.Tracker;
import io.github.togar2.pvp.enums.Tool;
import io.github.togar2.pvp.events.DamageBlockEvent;
import io.github.togar2.pvp.feature.CombatFeature;
import io.github.togar2.pvp.feature.config.DefinedFeature;
import io.github.togar2.pvp.feature.config.FeatureConfiguration;
import io.github.togar2.pvp.feature.config.FeatureType;
import io.github.togar2.pvp.feature.item.ItemDamageFeature;
import io.github.togar2.pvp.utils.CombatVersion;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.metadata.LivingEntityMeta;
import net.minestom.server.entity.metadata.projectile.AbstractArrowMeta;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;

import java.util.concurrent.ThreadLocalRandom;

public class VanillaBlockFeature implements BlockFeature, CombatFeature {
	public static final DefinedFeature<VanillaBlockFeature> DEFINED = new DefinedFeature<>(
			FeatureType.BLOCK, VanillaBlockFeature::new,
			FeatureType.ITEM_DAMAGE, FeatureType.VERSION
	);
	
	private final ItemDamageFeature itemDamageFeature;
	
	private final CombatVersion version;
	
	public VanillaBlockFeature(FeatureConfiguration configuration) {
		this.itemDamageFeature = configuration.get(FeatureType.ITEM_DAMAGE);
		this.version = configuration.get(FeatureType.VERSION);
	}
	
	protected boolean isPiercing(Damage damage) {
		Entity source = damage.getSource();
		if (source != null && source.getEntityMeta() instanceof AbstractArrowMeta) {
			return ((AbstractArrowMeta) source.getEntityMeta()).getPiercingLevel() > 0;
		}
		
		return false;
	}
	
	@Override
	public boolean isDamageBlocked(LivingEntity entity, Damage damage) {
		if (damage.getAmount() <= 0) return false;
		DamageTypeInfo info = DamageTypeInfo.of(damage.getType());
		
		// If damage doesn't bypass armor, no piercing, and a shield is active
		if (!info.bypassesArmor() && !isPiercing(damage)
				&& entity.getEntityMeta() instanceof LivingEntityMeta meta
				&& meta.isHandActive() && entity.getItemInHand(meta.getActiveHand()).material() == Material.SHIELD) {
			if (version.legacy()) return true;
			
			if (damage.getSource() != null) {
				Pos attackerPos = damage.getSource().getPosition();
				Pos entityPos = entity.getPosition();
				
				Vec attackerPosVector = attackerPos.asVec();
				Vec entityRotation = entityPos.direction();
				Vec attackerDirection = entityPos.asVec().sub(attackerPosVector).normalize();
				attackerDirection = attackerDirection.withY(0);
				
				// Dot product is lower than zero when the angle between the vectors is >90 degrees
				return attackerDirection.dot(entityRotation) < 0.0;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean applyBlock(LivingEntity entity, Damage damage) {
		float amount = damage.getAmount();
		float resultingDamage = version.legacy() ? Math.max(0, (amount + 1) * 0.5f) : 0;
		
		DamageBlockEvent damageBlockEvent = new DamageBlockEvent(entity, amount, resultingDamage, version.modern());
		EventDispatcher.call(damageBlockEvent);
		if (damageBlockEvent.isCancelled()) return false;
		damage.setAmount(damageBlockEvent.getResultingDamage());
		
		if (amount >= 3) {
			int shieldDamage = 1 + (int) Math.floor(amount);
			Player.Hand hand = ((LivingEntityMeta) entity.getEntityMeta()).getActiveHand();
			itemDamageFeature.damageEquipment(
					entity,
					hand == Player.Hand.MAIN ?
							EquipmentSlot.MAIN_HAND : EquipmentSlot.OFF_HAND,
					shieldDamage
			);
			
			if (entity.getItemInHand(hand).isAir()) {
				((LivingEntityMeta) entity.getEntityMeta()).setHandActive(false);
				entity.getViewersAsAudience().playSound(Sound.sound(
						SoundEvent.ITEM_SHIELD_BREAK, Sound.Source.PLAYER,
						0.8f, 0.8f + ThreadLocalRandom.current().nextFloat(0.4f)
				));
			}
		}
		
		// Take shield hit (knockback and disabling)
		DamageTypeInfo info = DamageTypeInfo.of(damage.getType());
		if (!info.projectile() && damage.getAttacker() instanceof LivingEntity attacker)
			takeShieldHit(entity, attacker, damageBlockEvent.knockbackAttacker());
		
		return amount == 0;
	}
	
	protected void takeShieldHit(LivingEntity entity, LivingEntity attacker, boolean applyKnockback) {
		if (applyKnockback) {
			Pos entityPos = entity.getPosition();
			Pos attackerPos = attacker.getPosition();
			attacker.takeKnockback(0.5F,
					attackerPos.x() - entityPos.x(),
					attackerPos.z() - entityPos.z()
			);
		}
		
		if (!(entity instanceof Player)) return;
		Tool tool = Tool.fromMaterial(attacker.getItemInMainHand().material());
		if (tool != null && tool.isAxe()) {
			disableShield((Player) entity);
		}
	}
	
	protected static void disableShield(Player player) {
		Tracker.setCooldown(player, Material.SHIELD, 100);
		
		// Shield disable status
		player.triggerStatus((byte) 30);
		player.triggerStatus((byte) 9);
		
		Player.Hand hand = player.getPlayerMeta().getActiveHand();
		player.refreshActiveHand(false, hand == Player.Hand.OFF, false);
	}
}
