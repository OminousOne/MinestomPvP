package io.github.OminousOne.pvp.feature.projectile;

import io.github.OminousOne.pvp.feature.CombatFeature;

/**
 * Combat feature which handles bow shooting.
 */
public interface BowFeature extends CombatFeature {
	BowFeature NO_OP = new BowFeature() {};
}
