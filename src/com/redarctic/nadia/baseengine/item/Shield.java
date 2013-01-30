/*
 * Shield.java
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

public class Shield extends BaseItem implements Serializable {
	private static final long serialVersionUID = 1817582993820569453L;

	final WeaponsType weaponType = WeaponsType.SHIELD;
	
	BaseModifier modifier;
	
	public Shield(String shieldName, ItemType type, int price, BaseModifier modifier, Classes ... allowableClasses) {
		super(shieldName, type, price, allowableClasses);
		this.modifier = modifier;
	}

	public WeaponsType getWeaponType() {
		return weaponType;
	}

	public BaseModifier getModifier() {
		return modifier;
	}

	protected void setModifier(BaseModifier modifier) {
		this.modifier = modifier;
	}

	@Override
	public String toString() {
		String shieldString = super.toString() + ", ";
		shieldString += getWeaponType().toString() + ", ";
		shieldString += getModifier().toString();
		
		for (Classes c : this.allowableClasses) {
			shieldString += ", " + c.toString();
		}
		
		return shieldString;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Classes[] allowedClasses = new Classes[this.allowableClasses.size()];
		
		for (int i = 0; i < this.allowableClasses.size(); i++) {
			allowedClasses[i] = this.allowableClasses.get(i);
		}
		
		Shield shield = new Shield(
				getName(), 
				getType(), 
				getPrice(), 
				getModifier(), 
				allowedClasses
				);
		
		return shield;
	}
	
	
}
