package io.github.OminousOne.pvp.feature.damage;

import io.github.OminousOne.pvp.feature.CombatFeature;

/**
 * Combat feature which handles entities being damaged.
 */
public interface DamageFeature extends CombatFeature {
	DamageFeature NO_OP = new DamageFeature() {};
}
