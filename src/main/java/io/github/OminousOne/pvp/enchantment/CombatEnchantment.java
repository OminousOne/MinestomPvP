package io.github.OminousOne.pvp.enchantment;

import io.github.OminousOne.pvp.feature.FeatureType;
import io.github.OminousOne.pvp.feature.config.FeatureConfiguration;
import io.github.OminousOne.pvp.feature.enchantment.EnchantmentFeature;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.registry.DynamicRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CombatEnchantment {
	private final DynamicRegistry.Key<Enchantment> enchantment;
	private final EquipmentSlot[] slotTypes;
	
	private final Set<FeatureType<?>> dependencies;
	
	public CombatEnchantment(DynamicRegistry.Key<Enchantment> enchantment, EquipmentSlot... slotTypes) {
		this(enchantment, Set.of(), slotTypes);
	}
	
	public CombatEnchantment(DynamicRegistry.Key<Enchantment> enchantment,
	                         Set<FeatureType<?>> dependencies, EquipmentSlot... slotTypes) {
		this.enchantment = enchantment;
		this.dependencies = dependencies;
		this.slotTypes = slotTypes;
	}
	
	public DynamicRegistry.Key<Enchantment> getEnchantment() {
		return enchantment;
	}
	
	public Set<FeatureType<?>> getDependencies() {
		return dependencies;
	}
	
	public Map<EquipmentSlot, ItemStack> getEquipment(LivingEntity entity) {
		Map<EquipmentSlot, ItemStack> map = new HashMap<>();
		
		for (EquipmentSlot slot : this.slotTypes) {
			ItemStack itemStack = entity.getEquipment(slot);
			if (!itemStack.isAir()) {
				map.put(slot, itemStack);
			}
		}
		
		return map;
	}
	
	public int getProtectionAmount(int level, DamageType damageType, EnchantmentFeature feature, FeatureConfiguration configuration) {
		return 0;
	}
	
	public float getAttackDamage(int level, EntityGroup group, EnchantmentFeature feature, FeatureConfiguration configuration) {
		return 0.0F;
	}
	
	public void onTargetDamaged(LivingEntity user, Entity target, int level, EnchantmentFeature feature, FeatureConfiguration configuration) {}
	public void onUserDamaged(LivingEntity user, LivingEntity attacker, int level, EnchantmentFeature feature, FeatureConfiguration configuration) {}
}
