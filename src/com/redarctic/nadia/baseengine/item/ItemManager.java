/*
 * ItemManager.java
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

package com.redarctic.nadia.baseengine.item;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ItemManager {
	ConcurrentHashMap<String, Weapon> weapons = new ConcurrentHashMap<String, Weapon>();
	ConcurrentHashMap<String, Integer> numberWeapons = new ConcurrentHashMap<String, Integer>();
	ConcurrentHashMap<String, Armor> armors = new ConcurrentHashMap<String, Armor>();
	ConcurrentHashMap<String, Integer> numberArmors = new ConcurrentHashMap<String, Integer>();
	ConcurrentHashMap<String, Shield> shields = new ConcurrentHashMap<String, Shield>();
	ConcurrentHashMap<String, Integer> numberShields = new ConcurrentHashMap<String, Integer>();
	
	public Enumeration<String> getWeaponKeys() {
		return this.weapons.keys();
	}
	
	public Enumeration<String> getArmorKeys() {
		return this.armors.keys();
	}
	
	public Enumeration<String> getShieldKeys() {
		return this.shields.keys();
	}
	
	public ItemManager() {}
	
	public void addWeapon(Weapon weapon) {
		if (!this.weapons.containsKey(weapon.getName())) {
			this.weapons.put(weapon.getName(), weapon);
			this.numberWeapons.put(weapon.getName(), 1);
		}
		else {
			this.numberWeapons.put(
					weapon.getName(), 
					Integer.valueOf(this.numberWeapons.get(weapon.getName()).intValue() + 1)
					);
		}
	}
	
	///////////////////////////////
	/// WEAPON REGION
	///////////////////////////////
	
	public Weapon getWeapon(String name) {
		if (this.weapons.containsKey(name)) {
			try {
				return (Weapon)this.weapons.get(name).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	
	public int getNumberWeapons(String name) {
		if (this.numberWeapons.containsKey(name)) {			
			return (int)this.numberWeapons.get(name);			
		}
		else return 0;
	}
	
	public boolean containsWeapon(String name) {
		return this.weapons.containsKey(name) && this.numberWeapons.containsKey(name);
	}
	
	public Weapon removeWeapon(String name) {
		if (containsWeapon(name)) {
			if (getNumberWeapons(name) > 1) {
				this.numberWeapons.put(
						name, 
						Integer.valueOf(this.numberWeapons.get(name).intValue() - 1)
						);
				return this.weapons.get(name);
			}
			else {
				this.numberWeapons.remove(name);
				return this.weapons.remove(name);
			}
		}
		else {
			return null;
		}
	}
	
	///////////////////////////////
	/// ARMOR REGION
	///////////////////////////////
	
	public void addArmor(Armor armor) {
		if (!this.armors.containsKey(armor.getName())) {
			this.armors.put(armor.getName(), armor);
			this.numberArmors.put(armor.getName(), 1);
		}
		else {
			this.numberArmors.put(
					armor.getName(), 
					Integer.valueOf(this.numberArmors.get(armor.getName()).intValue() + 1)
					);
		}
	}
	
	public Armor getArmor(String name) {
		if (this.armors.containsKey(name)) {
			try {
				return (Armor)this.armors.get(name).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	
	public int getNumberArmors(String name) {
		if (this.numberArmors.containsKey(name)) {			
			return (int)this.numberArmors.get(name);			
		}
		else return 0;
	}
	
	public boolean containsArmor(String name) {
		return this.armors.containsKey(name) && this.numberArmors.containsKey(name);
	}
	
	public Armor removeArmor(String name) {
		if (containsArmor(name)) {
			if (getNumberArmors(name) > 1) {
				this.numberArmors.put(
						name, 
						Integer.valueOf(this.numberArmors.get(name).intValue() - 1)
						);
				return this.armors.get(name);
			}
			else {
				this.numberArmors.remove(name);
				return this.armors.remove(name);
			}
		}
		else {
			return null;
		}
	}
	
	///////////////////////////////
	/// SHIELD REGION
	///////////////////////////////
	
	public void addShield(Shield shield) {
		if (!this.shields.containsKey(shield.getName())) {
			this.shields.put(shield.getName(), shield);
			this.numberShields.put(shield.getName(), 1);
		}
		else {
			this.numberShields.put(
					shield.getName(), 
					Integer.valueOf(this.numberShields.get(shield.getName()).intValue() + 1)
					);
		}
	}
	
	public Shield getShield(String name) {
		if (this.shields.containsKey(name)) {
			try {
				return (Shield)this.shields.get(name).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}
	
	public int getNumberShields(String name) {
		if (this.numberShields.containsKey(name)) {			
			return (int)this.numberShields.get(name);			
		}
		else return 0;
	}
	
	public boolean containsShield(String name) {
		return this.shields.containsKey(name) && this.numberShields.containsKey(name);
	}
	
	public Shield removeShield(String name) {
		if (containsShield(name)) {
			if (getNumberShields(name) > 1) {
				this.numberShields.put(
						name, 
						Integer.valueOf(this.numberShields.get(name).intValue() - 1)
						);
				return this.shields.get(name);
			}
			else {
				this.numberShields.remove(name);
				return this.shields.remove(name);
			}
		}
		else {
			return null;
		}
	}
}
