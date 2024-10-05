package io.github.OminousOne.pvp.feature.armor;

import io.github.OminousOne.pvp.feature.CombatFeature;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.damage.DamageType;

/**
 * Combat feature used for determining the resulting damage after armor usage.
 */
public interface ArmorFeature extends CombatFeature {
	ArmorFeature NO_OP = (entity, type, amount) -> amount;
	
	float getDamageWithProtection(LivingEntity entity, DamageType type, float amount);
}
