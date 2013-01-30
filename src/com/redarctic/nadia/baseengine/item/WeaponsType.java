/*
 * WeaponsType.java
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

public enum WeaponsType {
	SWORD (1, "Sword", HandsUsed.BOTH_HANDS),
	STAFF (2, "Staff", HandsUsed.BOTH_HANDS),
	CLUB (1, "Club", HandsUsed.BOTH_HANDS),
	GREAT_SWORD (2, "Great Sword", HandsUsed.MAIN_HAND_ONLY),
	AXE (1, "Axe", HandsUsed.BOTH_HANDS),
	GREAT_AXE (2, "Great Axe", HandsUsed.MAIN_HAND_ONLY),
	CLAW (2, "Claws", HandsUsed.MAIN_HAND_ONLY),
	DAGGER (1, "Dagger", HandsUsed.BOTH_HANDS),
	SHIELD (1, "Shield", HandsUsed.SECONDARY_HAND_ONLY);
	
	public enum HandsUsed {
		MAIN_HAND_ONLY,
		SECONDARY_HAND_ONLY,
		BOTH_HANDS
	}
	
	private final int hands;
	private final String type;
	private final HandsUsed handsUsed;
	
	public int getHands() {
		return this.hands;
	}
	
	public String getType() {
		return this.type;
	}
	
	public HandsUsed getHandsUsed() {
		return this.handsUsed;
	}
	
	WeaponsType(int hands, String type, HandsUsed handsUsed) {
		this.hands = hands;
		this.type = type;
		this.handsUsed = handsUsed;
	}
}
