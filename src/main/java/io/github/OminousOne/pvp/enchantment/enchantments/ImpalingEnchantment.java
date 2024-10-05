package io.github.OminousOne.pvp.enchantment.enchantments;

import io.github.OminousOne.pvp.enchantment.CombatEnchantment;
import io.github.OminousOne.pvp.enchantment.EntityGroup;
import io.github.OminousOne.pvp.feature.config.FeatureConfiguration;
import io.github.OminousOne.pvp.feature.enchantment.EnchantmentFeature;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.item.enchant.Enchantment;

public class ImpalingEnchantment extends CombatEnchantment {
	public ImpalingEnchantment(EquipmentSlot... slotTypes) {
		super(Enchantment.IMPALING, slotTypes);
	}
	
	@Override
	public float getAttackDamage(int level, EntityGroup group,
	                             EnchantmentFeature feature, FeatureConfiguration configuration) {
		return group == EntityGroup.AQUATIC ? (float) level * 2.5F : 0.0F;
	}
}
