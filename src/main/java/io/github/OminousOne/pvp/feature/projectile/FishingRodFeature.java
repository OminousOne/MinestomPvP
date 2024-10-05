package io.github.OminousOne.pvp.feature.projectile;

import io.github.OminousOne.pvp.feature.CombatFeature;

/**
 * Combat feature which handles throwing and retrieving fishing rods.
 */
public interface FishingRodFeature extends CombatFeature {
	FishingRodFeature NO_OP = new FishingRodFeature() {};
}
