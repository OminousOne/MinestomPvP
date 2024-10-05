package io.github.OminousOne.pvp.potion.effect;

import io.github.OminousOne.pvp.utils.CombatVersion;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.potion.PotionEffect;

public class HealthBoostPotionEffect extends CombatPotionEffect {
	public HealthBoostPotionEffect() {
		super(PotionEffect.HEALTH_BOOST);
	}
	
	@Override
	public void onRemoved(LivingEntity entity, byte amplifier, CombatVersion version) {
		super.onRemoved(entity, amplifier, version);
		
		if (entity.getHealth() > entity.getAttributeValue(Attribute.GENERIC_MAX_HEALTH)) {
			entity.setHealth((float) entity.getAttributeValue(Attribute.GENERIC_MAX_HEALTH));
		}
	}
}
