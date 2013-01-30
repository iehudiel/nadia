/*
 * BaseModifier.java
 * 
 * Copyright (c) 2013, Emmanuel Arana Corzo. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package com.redarctic.nadia.baseengine.character;

import java.io.Serializable;

import com.redarctic.nadia.ext.MathHelper;

public class BaseModifier implements Serializable {
	public static final String MP_INITIALS = "MP";
	public static final String HP_INITIALS = "HP";
	public static final String DIVINE_SKILL_INITIALS = "DVS";
	public static final String ENHANCE_SKILL_INITIALS = "EHS";
	public static final String BLACK_SKILL_INITIALS = "BKS";
	public static final String ENFEEBLE_SKILL_INITIALS = "EFS";
	public static final String DARK_SKILL_INITIALS = "DKS";
	public static final String HEALING_SKILL_INITIALS = "HES";
	public static final String MAGIC_EVASION_INITIALS = "MEV";
	public static final String MAGIC_DEFENSE_INITIALS = "MDF";
	public static final String MAGIC_ACCURACY_INITIALS = "MAC";
	public static final String MAGIC_ATTACK_INITIALS = "MAT";
	public static final String EVASION_INITIALS = "EVA";
	public static final String ACCURACY_INITIALS = "ACC";
	public static final String ATTACK_INITIALS = "ATK";
	public static final String DEFENSE_INITIALS = "DEF";
	public static final String AGILITY_INITIALS = "AGI";
	public static final String INTELLIGENCE_INITIALS = "INT";
	public static final String MIND_INITIALS = "MND";
	public static final String VITALITY_INITIALS = "VIT";
	public static final String DEXTERITY_INITIALS = "DEX";
	public static final String STRENGTH_INITIALS = "STR";

	private static final long serialVersionUID = -2804793927570847945L;

	public static final int MAX_MODIFIER = 999;
	
	private int hpModifier;
	private int mpModifier;
	
	private int dexterityModifier;
	private int strengthModifier;
	private int vitalityModifier;
	private int mindModifier;
	private int agilityModifier;
	private int intelligenceModifier;
	
	private int defenseModifier;
	private int attackModifier;
	private int accuracyModifier;
	private int evasionModifier;
	
	private int magicAttackModifier;
	private int magicAccuracyModifier;
	private int magicDefenseModifier;
	private int magicEvasionModifier;
	
	private int healingSkillModifier;
	private int enfeebleSkillModifier;
	private int darkSkillModifier;
	private int blackSkillModifier;
	private int enhanceSkillModifier;
	private int divineSkillModifier;
	
	private boolean fireElementAbsorb;
	private boolean earthElementAbsorb;
	private boolean waterElementAbsorb;
	private boolean iceElementAbsorb;
	private boolean thunderElementAbsorb;
	private boolean darkElementAbsorb;
	private boolean divineElementAbsorb;
	
	private String formula = "";
	
	public BaseModifier(String formula) {
		reset();
		this.setFormula(formula);
	}

	public void reset() {
		this.hpModifier = 0;
		this.mpModifier = 0;
		
		this.attackModifier = 0;
		this.accuracyModifier = 0;
		this.defenseModifier = 0;
		this.evasionModifier = 0;
		
		this.magicAccuracyModifier = 0;
		this.magicAttackModifier = 0;
		this.magicDefenseModifier = 0;
		this.magicEvasionModifier = 0;
		
		this.agilityModifier = 0;
		this.dexterityModifier = 0;
		this.intelligenceModifier = 0;
		this.mindModifier = 0;
		this.strengthModifier = 0;
		this.vitalityModifier = 0;
		
		this.blackSkillModifier = 0;
		this.darkSkillModifier = 0;		
		this.divineSkillModifier = 0;
		this.enfeebleSkillModifier = 0;
		this.enhanceSkillModifier = 0;
		this.healingSkillModifier = 0;
		
		this.divineElementAbsorb = false;
		this.earthElementAbsorb = false;
		this.darkElementAbsorb = false;
		this.fireElementAbsorb = false;
		this.iceElementAbsorb = false;
		this.thunderElementAbsorb = false;
		this.waterElementAbsorb = false;		
		
		this.formula = "";
	}
	
	private void formulaParser(String formula) throws FormulaModifierException {
		formula.trim();
		
		if (formula.contains("+")) { 
			formula.replaceAll(" ", "");
			Integer modifierValue = Integer.getInteger(formula.substring(formula.indexOf("+")));
			
			setterAddedAttributes(formula, modifierValue);
		}
		else if (formula.contains("-")) {
			formula.replaceAll(" ", "");
			Integer modifierValue = Integer.getInteger(formula.substring(formula.indexOf("-")));
			
			setterAddedAttributes(formula, modifierValue);
		}
		else if (formula.contains("Element Absorb")) {
			String element = formula.substring(0, formula.indexOf("Element Absorb"));
			
			element.trim();
			setterElementAbsorb(element);
		}
	}

	private void setterElementAbsorb(String element) {
		if (element.equalsIgnoreCase("Fire")) {
			this.setFireElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("Earth")) {
			this.setEarthElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("Water")) {
			this.setWaterElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("Ice")) {
			this.setIceElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("Thunder")) {
			this.setThunderElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("Dark")) {
			this.setDarkElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("Divine")) {
			this.setDivineElementAbsorb(true);
		}
		else if (element.equalsIgnoreCase("All")) {
			this.setFireElementAbsorb(true);
			this.setEarthElementAbsorb(true);
			this.setWaterElementAbsorb(true);
			this.setIceElementAbsorb(true);
			this.setThunderElementAbsorb(true);
			this.setDarkElementAbsorb(true);
			this.setDivineElementAbsorb(true);
		}
	}
	
	private void setterAddedAttributes(String formula, Integer modifierValue) {
		if (formula.substring(0, 1).equalsIgnoreCase(HP_INITIALS)) {
			this.setHpModifier(modifierValue);
		}
		else if (formula.substring(0, 1).equalsIgnoreCase(MP_INITIALS)) {
			this.setMpModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(STRENGTH_INITIALS)) {
			this.setStrengthModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(DEXTERITY_INITIALS)) {
			this.setDexterityModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(VITALITY_INITIALS)) {
			this.setVitalityModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(MIND_INITIALS)) {
			this.setMindModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(INTELLIGENCE_INITIALS)) {
			this.setIntelligenceModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(AGILITY_INITIALS)) {
			this.setAgilityModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(DEFENSE_INITIALS)) {
			this.setDefenseModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(ATTACK_INITIALS)) {
			this.setAttackModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(ACCURACY_INITIALS)) {
			this.setAccuracyModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(EVASION_INITIALS)) {
			this.setEvasionModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(MAGIC_ATTACK_INITIALS)) {
			this.setMagicAttackModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(MAGIC_ACCURACY_INITIALS)) {
			this.setMagicAccuracyModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(MAGIC_DEFENSE_INITIALS)) {
			this.setMagicDefenseModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(MAGIC_EVASION_INITIALS)) {
			this.setMagicEvasionModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(HEALING_SKILL_INITIALS)) {
			this.setHealingSkillModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(DARK_SKILL_INITIALS)) {
			this.setDarkSkillModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(ENFEEBLE_SKILL_INITIALS)) {
			this.setEnfeebleSkillModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(BLACK_SKILL_INITIALS)) {
			this.setBlackSkillModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(ENHANCE_SKILL_INITIALS)) {
			this.setEnhanceSkillModifier(modifierValue);
		}
		else if (formula.substring(0, 2).equalsIgnoreCase(DIVINE_SKILL_INITIALS)) {
			this.setDivineSkillModifier(modifierValue);
		}
	}
	
	@Override
	public String toString() {
		return this.getFormula();
	}
		
	public int getHpModifier() {
		return hpModifier;
	}

	public void setHpModifier(int hpModifier) {
		this.hpModifier = hpModifier;
	}

	public int getMpModifier() {
		return mpModifier;
	}

	public void setMpModifier(int mpModifier) {
		this.mpModifier = mpModifier;
	}

	public int getDexterityModifier() {
		return dexterityModifier;
	}
	public void setDexterityModifier(int dexterityModifier) {
		this.dexterityModifier = dexterityModifier;
	}
	
	public int getStrengthModifier() {
		return strengthModifier;
	}
	public void setStrengthModifier(int strengthModifier) {
		this.strengthModifier = (int)MathHelper.clamp(strengthModifier, 0, MAX_MODIFIER);
	}
	
	public int getVitalityModifier() {
		return vitalityModifier;
	}
	public void setVitalityModifier(int vitalityModifier) {
		this.vitalityModifier = (int)MathHelper.clamp(vitalityModifier, 0, MAX_MODIFIER);
	}
	
	public int getMindModifier() {
		return mindModifier;
	}
	public void setMindModifier(int mindModifier) {
		this.mindModifier = (int)MathHelper.clamp(mindModifier, 0, MAX_MODIFIER);
	}
	
	public int getAgilityModifier() {
		return agilityModifier;
	}
	public void setAgilityModifier(int agilityModifier) {
		this.agilityModifier = (int)MathHelper.clamp(agilityModifier, 0, MAX_MODIFIER);
	}
	
	public int getIntelligenceModifier() {
		return intelligenceModifier;
	}
	public void setIntelligenceModifier(int intelligenceModifier) {
		this.intelligenceModifier = (int)MathHelper.clamp(intelligenceModifier, 0, MAX_MODIFIER);
	}
	
	public int getDefenseModifier() {
		return defenseModifier;
	}
	public void setDefenseModifier(int defenseModifier) {
		this.defenseModifier = (int)MathHelper.clamp(defenseModifier, 0, MAX_MODIFIER);
	}
	
	public int getAttackModifier() {
		return attackModifier;
	}
	public void setAttackModifier(int attackModifier) {
		this.attackModifier = (int)MathHelper.clamp(attackModifier, 0, MAX_MODIFIER);
	}
	
	public int getAccuracyModifier() {
		return accuracyModifier;
	}
	public void setAccuracyModifier(int accuracyModifier) {
		this.accuracyModifier = (int)MathHelper.clamp(accuracyModifier, 0, MAX_MODIFIER);
	}
	
	public int getEvasionModifier() {
		return evasionModifier;
	}
	public void setEvasionModifier(int evasionModifier) {
		this.evasionModifier = (int)MathHelper.clamp(evasionModifier, 0, MAX_MODIFIER);
	}
	
	public int getMagicAttackModifier() {
		return magicAttackModifier;
	}
	public void setMagicAttackModifier(int magicAttackModifier) {
		this.magicAttackModifier = (int)MathHelper.clamp(magicAttackModifier, 0, MAX_MODIFIER);
	}
	
	public int getMagicAccuracyModifier() {
		return magicAccuracyModifier;
	}
	public void setMagicAccuracyModifier(int magicAccuracyModifier) {
		this.magicAccuracyModifier = (int)MathHelper.clamp(magicAccuracyModifier, 0, MAX_MODIFIER);
	}
	
	public int getMagicDefenseModifier() {
		return magicDefenseModifier;
	}
	public void setMagicDefenseModifier(int magicDefenseModifier) {
		this.magicDefenseModifier = (int)MathHelper.clamp(magicDefenseModifier, 0, MAX_MODIFIER);
	}
	
	public int getMagicEvasionModifier() {
		return magicEvasionModifier;
	}
	public void setMagicEvasionModifier(int magicEvasionModifier) {
		this.magicEvasionModifier = (int)MathHelper.clamp(magicEvasionModifier, 0, MAX_MODIFIER);
	}
		
	public int getHealingSkillModifier() {
		return healingSkillModifier;
	}
	public void setHealingSkillModifier(int healingSkillModifier) {
		this.healingSkillModifier = (int)MathHelper.clamp(healingSkillModifier, 0, MAX_MODIFIER);
	}
	
	public int getEnfeebleSkillModifier() {
		return enfeebleSkillModifier;
	}
	public void setEnfeebleSkillModifier(int enfeebleSkillModifier) {
		this.enfeebleSkillModifier = (int)MathHelper.clamp(enfeebleSkillModifier, 0, MAX_MODIFIER);
	}
	
	public int getDarkSkillModifier() {
		return darkSkillModifier;
	}
	public void setDarkSkillModifier(int darkSkillModifier) {
		this.darkSkillModifier = (int)MathHelper.clamp(darkSkillModifier, 0, MAX_MODIFIER);
	}
	
	public int getBlackSkillModifier() {
		return blackSkillModifier;
	}
	public void setBlackSkillModifier(int blackSkillModifier) {
		this.blackSkillModifier = (int)MathHelper.clamp(blackSkillModifier, 0, MAX_MODIFIER);
	}
	
	public int getEnhanceSkillModifier() {
		return enhanceSkillModifier;
	}
	public void setEnhanceSkillModifier(int enhanceSkillModifier) {
		this.enhanceSkillModifier = (int)MathHelper.clamp(enhanceSkillModifier, 0, MAX_MODIFIER);
	}
	
	public int getDivineSkillModifier() {
		return divineSkillModifier;
	}
	public void setDivineSkillModifier(int divineSkillModifier) {
		this.divineSkillModifier = (int)MathHelper.clamp(divineSkillModifier, 0, MAX_MODIFIER);
	}
	
	public boolean isFireElementAbsorb() {
		return fireElementAbsorb;
	}
	public void setFireElementAbsorb(boolean fireElementAbsorb) {
		this.fireElementAbsorb = fireElementAbsorb;
	}
	
	public boolean isEarthElementAbsorb() {
		return earthElementAbsorb;
	}
	public void setEarthElementAbsorb(boolean earthElementAbsorb) {
		this.earthElementAbsorb = earthElementAbsorb;
	}
	
	public boolean isWaterElementAbsorb() {
		return waterElementAbsorb;
	}
	public void setWaterElementAbsorb(boolean waterElementAbsorb) {
		this.waterElementAbsorb = waterElementAbsorb;
	}
	
	public boolean isIceElementAbsorb() {
		return iceElementAbsorb;
	}
	public void setIceElementAbsorb(boolean iceElementAbsorb) {
		this.iceElementAbsorb = iceElementAbsorb;
	}
	
	public boolean isThunderElementAbsorb() {
		return thunderElementAbsorb;
	}
	public void setThunderElementAbsorb(boolean thunderElementAbsorb) {
		this.thunderElementAbsorb = thunderElementAbsorb;
	}
	
	public boolean isDarkElementAbsorb() {
		return darkElementAbsorb;
	}
	public void setDarkElementAbsorb(boolean darkElementAbsorb) {
		this.darkElementAbsorb = darkElementAbsorb;
	}
	
	public boolean isDivineElementAbsorb() {
		return divineElementAbsorb;
	}
	public void setDivineElementAbsorb(boolean divineElementAbsorb) {
		this.divineElementAbsorb = divineElementAbsorb;
	}
		
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		String[] attributes = formula.split(",");
		
		for (String attr : attributes) {
			try {
				formulaParser(attr);
				if (formula.equalsIgnoreCase("")) {
					formula = attr;
				}
				else {
					formula += ", " + attr;
				}
			}
			catch (FormulaModifierException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
