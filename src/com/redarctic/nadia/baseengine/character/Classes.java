/*
 * Classes.java
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

import com.redarctic.nadia.R;
import com.redarctic.nadia.baseengine.GameSurface;

public enum Classes {
	KNIGHT (
			GameSurface.getStringResource(R.string.knight_class_name), 
			GameSurface.getStringResource(R.string.knight_class_description), 
			false
			),
	TEMPLAR (
			GameSurface.getStringResource(R.string.templar_class_name),
			GameSurface.getStringResource(R.string.templar_class_description),
			true
			),
	SPARTAN (
			GameSurface.getStringResource(R.string.spartan_class_name), 
			GameSurface.getStringResource(R.string.spartan_class_description), 
			true
			),
	ROGUE (
			GameSurface.getStringResource(R.string.rogue_class_name), 
			GameSurface.getStringResource(R.string.rogue_class_description), 
			false
			),
	HUNTER (
			GameSurface.getStringResource(R.string.hunter_class_name),
			GameSurface.getStringResource(R.string.hunter_class_description),
			false
			),
	ASSASIN (
			GameSurface.getStringResource(R.string.assasin_class_name),
			GameSurface.getStringResource(R.string.assasin_class_description),
			true
			),
	MARTIAL_ARTIST (
			GameSurface.getStringResource(R.string.martial_artist_class_name), 
			GameSurface.getStringResource(R.string.martial_artist_class_description), 
			false
			),
	HERO (
			"Hero", 
			"", 
			true
			),
	NEPHILIM (
			"Nephilim", 
			"", 
			true
			),
	DRAGON_WARRIOR (
			"Dragon Warrior", 
			"", 
			true
			),
	PRIEST (
			"Priest", 
			"", 
			false
			),
	WARLOCK (
			"Warlock", 
			"", 
			false),
	ORACLE (
			"Oracle", 
			"", 
			true),
	SAGE (
			"Sage", 
			"", 
			true
			),
	MIMIC (
			"Mimic", 
			"", 
			false
			);
	
	private final String name;
	private final String description;
	private final boolean requirePreviousClass;
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public boolean isRequirePreviousClass() {
		return requirePreviousClass;
	}
	
	Classes(String name, String description, boolean requirePreviousClass) {
		this.name = name;
		this.description = description;
		this.requirePreviousClass = requirePreviousClass;
	}
}
