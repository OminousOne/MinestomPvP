package io.github.OminousOne.pvp.feature.projectile;

import io.github.OminousOne.pvp.feature.CombatFeature;

/**
 * Combat feature which handles crossbow shooting.
 */
public interface CrossbowFeature extends CombatFeature {
	CrossbowFeature NO_OP = new CrossbowFeature() {};
}
