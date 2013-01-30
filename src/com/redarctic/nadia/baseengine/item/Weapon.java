/*
 * Weapon.java
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

import java.io.Serializable;

import com.redarctic.nadia.baseengine.character.BaseModifier;
import com.redarctic.nadia.baseengine.character.Classes;
import com.redarctic.nadia.baseengine.item.WeaponsType.HandsUsed;

public class Weapon extends BaseItem implements Serializable {
	private static final long serialVersionUID = -6178151257009067604L;

	WeaponsType weaponType;
	
	BaseModifier modifier;

	public WeaponsType getWeaponType() {
		return weaponType;
	}

	protected void setWeaponType(WeaponsType weaponType) {
		this.weaponType = weaponType;
	}

	public BaseModifier getModifier() {
		return modifier;
	}

	protected void setModifier(BaseModifier modifier) {
		this.modifier = modifier;
	}
	
	public HandsUsed getHandsUsed() {
		return weaponType.getHandsUsed();
	} 
	
	public Weapon(String weaponName, WeaponsType type, int price, BaseModifier modifier, Classes ... classes) {
		super(weaponName, ItemType.ARMOR, price, classes);
		this.setModifier(modifier);
		this.setWeaponType(type);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Classes[] allowedClasses = new Classes[this.allowableClasses.size()];
		
		for (int i = 0; i < this.allowableClasses.size(); i++) {
			allowedClasses[i] = this.allowableClasses.get(i);
		}
		
		Weapon weapon = new Weapon(
				this.getName(), 
				this.getWeaponType(), 
				this.getPrice(), 
				this.getModifier(), 
				allowedClasses
				);
		return weapon;
	}

	@Override
	public String toString() {
		String weaponString = super.toString() + ", ";
		weaponString += this.weaponType.toString() + ", ";
		weaponString += this.modifier.toString();
		
		for (Classes t : this.allowableClasses) {
			weaponString += ", " + t.toString();
		}
		return weaponString;
	}	
}
