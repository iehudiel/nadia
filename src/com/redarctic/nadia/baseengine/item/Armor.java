/*
 * Armor.java
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

public class Armor extends BaseItem implements Serializable {
	private static final long serialVersionUID = 4531161854992040741L;

	ArmorLocation location;
	
	BaseModifier modifier;

	public ArmorLocation getLocation() {
		return location;
	}

	protected void setLocation(ArmorLocation location) {
		this.location = location;
	}

	public BaseModifier getModifier() {
		return modifier;
	}

	protected void setModifier(BaseModifier modifier) {
		this.modifier = modifier;
	}
	
	public Armor(String name, int price, ArmorLocation location, BaseModifier modifier, Classes ... classes) {
		super(name, ItemType.ARMOR, price, classes);
		this.setLocation(location);
		this.setModifier(modifier);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Classes[] allowedClasses = new Classes[this.allowableClasses.size()];
		
		for (int i = 0; i < this.allowableClasses.size(); i++) {
			allowedClasses[i] = this.allowableClasses.get(i);
		}
		
		Armor armor = new Armor(
				this.getName(),
				this.getPrice(),
				this.getLocation(),
				this.getModifier(),
				allowedClasses
				);
		return armor;
	}

	@Override
	public String toString() {
		String armorString = super.toString() + ", ";
		armorString += location.toString() + ", ";
		armorString += modifier.toString();
		
		for (Classes t : allowableClasses) {
			armorString += ", " + t.toString();
		}
		
		return armorString;
	}		
}
