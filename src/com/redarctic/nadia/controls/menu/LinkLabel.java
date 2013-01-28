/*
 * LinkLabel.java
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

package com.redarctic.nadia.controls.menu;

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.baseengine.SpriteFont;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

public class LinkLabel 
extends Control implements ISignalProvider {
	private int selectedColor = Color.RED;
	
	public static final String SIGNAL_ACTIVATED = "ACTIVATED";
	
	public LinkLabel() {
		super();
		this.setTabStop(true);
		this.setHasFocus(false);
		this.position = new Point(0, 0);		
	}	
	
	public int getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(int selectedColor) {
		this.selectedColor = selectedColor;
	}
	
	@Override
	public void update(Canvas canvas) { }

	@Override
	public synchronized void drawMe(Canvas canvas) {
		if (this.isHasFocus()) {
			SpriteFont.drawText(canvas, text, position, SpriteFont.getDefaultFont(selectedColor));
		}
		else {
			SpriteFont.drawText(canvas, text, position, SpriteFont.getDefaultFont(color));
		}
	}

	@Override
	public void handleInput() {
		if (gamepad != null) {
			if (isHasFocus() && gamepad.isActionPressed()) {
				doEmit(SIGNAL_ACTIVATED);
			}
		}
	}
	
	public void doEmit(String signalName) {
		SignalSlotMap.fastEmit(this, signalName);
	}

	@Override
	public void initialize() {
		
	}
}
