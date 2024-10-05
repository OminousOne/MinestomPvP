package io.github.OminousOne.pvp.feature.attributes;

import io.github.OminousOne.pvp.feature.CombatFeature;

/**
 * Combat feature which handles equipment changes (applies weapon and armor attributes).
 */
public interface EquipmentFeature extends CombatFeature {
	EquipmentFeature NO_OP = new EquipmentFeature() {};
}
