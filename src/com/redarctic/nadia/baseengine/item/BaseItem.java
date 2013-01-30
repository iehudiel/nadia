/*
 * BaseItem.java
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

import java.util.ArrayList;

import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.baseengine.character.Classes;

public abstract class BaseItem implements Cloneable {
	protected ArrayList<Classes> allowableClasses = new ArrayList<Classes>();
	
	String name;
	ItemType type;
	int price;
	transient boolean equipped;
	
	public ArrayList<Classes> getAllowableClasses() {
		return allowableClasses;
	}
	
	protected void setAllowableClasses(ArrayList<Classes> allowableClasses) {
		this.allowableClasses = allowableClasses;
	}
	
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	public ItemType getType() {
		return type;
	}
	
	protected void setType(ItemType type) {
		this.type = type;
	}
	
	public int getPrice() {
		return price;
	}
	
	protected void setPrice(int price) {
		this.price = (int)MathHelper.clamp(price, 0, 99999999);
	}
	
	public boolean isEquipped() {
		return equipped;
	}
	
	protected void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}
	
	public BaseItem(String name, ItemType type, int price, Classes ... allowableClasses) {
		for (Classes c : allowableClasses) {
			this.allowableClasses.add(c);
		}
		
		this.setName(name);
		this.setType(type);
		this.setPrice(price);
		this.setEquipped(false);
	}
	
	public boolean canEquip(Classes characterType) {
		return allowableClasses.contains(characterType);
	}

	@Override
	public String toString() {
		String itemString = "";
		
		itemString += getName() + ", ";
		itemString += getType() + ", ";
		itemString += getPrice();
		
		return itemString;
	}
	
	
}
