package io.github.OminousOne.pvp.feature.potion;

import io.github.OminousOne.pvp.feature.CombatFeature;

/**
 * Combat feature which handles drinking and throwing potions.
 */
public interface PotionFeature extends CombatFeature {
	PotionFeature NO_OP = new PotionFeature() {};
}
