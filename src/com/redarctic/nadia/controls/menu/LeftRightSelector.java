/*
 * LeftRightSelector.java
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

import java.util.ArrayList;

import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;
import com.redarctic.nadia.baseengine.SpriteFont;
import com.redarctic.nadia.controls.DirectionalPad;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

public class LeftRightSelector extends Control implements ISignalProvider, ISlotProvider {
	
	ArrayList<String> items = new ArrayList<String>();
	Bitmap leftTexture;
	Bitmap rightTexture;
	Bitmap stopTexture;
	
	int selectedColor = Color.RED;
	int maxItemWidth;
	int selectedItem;
	
	public static final String SIGNAL_SELECTION_CHANGED = "SELECTION_CHANGED";
	
	public LeftRightSelector(Bitmap leftArrow, Bitmap rightArrow, Bitmap stop) {
		this.leftTexture = leftArrow;
		this.rightTexture = rightArrow;
		this.stopTexture = stop;
		this.setTabStop(true);
		this.setColor(Color.WHITE);
	}
	
	public int getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(int selectedColor) {
		this.selectedColor = selectedColor;
	}

	public ArrayList<String> getItems() {
		return items;
	}
	
	public int getSelectedIndex() {
		return selectedItem;
	}
	
	public void setSelectedIndex(int value) {
		selectedItem = (int)MathHelper.clamp((float)value, 0.0f, (float)items.size());
	}
	
	public String getSelectedItem() {
		return getItems().get(selectedItem);
	}	
	
	public void setItems(String[] items, int maxWidth) {
		this.items.clear();
		
		for (String s : items) {
			this.items.add(s);
		}
		
		this.maxItemWidth = maxWidth;
	}
	
	protected void onSelectionChanged() {
		SignalSlotMap.fastEmit(this, SIGNAL_SELECTION_CHANGED, this);
	}
	
	@Override
	public synchronized void initialize() {
		SignalSlotMap.fastConnect(this.gamepad, DirectionalPad.SIGNAL_BUTTON_LEFT_PRESSED, 
				SlotProviderMethodPair.create(this, "handleInputLeft"));
		SignalSlotMap.fastConnect(this.gamepad, DirectionalPad.SIGNAL_BUTTON_RIGHT_PRESSED, 
				SlotProviderMethodPair.create(this, "handleInputRight"));
	}
	
	@Override
	public void update(Canvas canvas) {
	}

	@Override
	public synchronized void drawMe(Canvas canvas) {
		Point drawTo = this.position;
		
		if (this.selectedItem != 0) {
			canvas.drawBitmap(leftTexture, drawTo.x, drawTo.y, null);
		}
		else {
			canvas.drawBitmap(stopTexture, drawTo.x, drawTo.y, null);
		}
		
		drawTo.x += leftTexture.getWidth() + 5;
		
		int itemWidth = (int)SpriteFont.getDefaultFont(this.getColor()).measureText(items.get(selectedItem));
		int offset = (maxItemWidth - itemWidth) / 2;
		
		drawTo.x += offset;
		
		SpriteFont.drawText(canvas, this.items.get(this.selectedItem), 
				drawTo, SpriteFont.getDefaultFont(
						(isHasFocus()) ? this.selectedColor : this.getColor() ));
		
		drawTo.x += -1 * offset + this.maxItemWidth + 5;
		
		if (this.selectedItem != this.items.size() - 1) {
			canvas.drawBitmap(rightTexture, drawTo.x, drawTo.y, null);
		}
		else {
			canvas.drawBitmap(stopTexture, drawTo.x, drawTo.y, null);
		}
	}

	@Override
	public void handleInput() {		
	}
	
	public void handleInputLeft() {
		if (!items.isEmpty()) {
			selectedItem--;
			if (selectedItem < 0)
				selectedItem = 0;
			onSelectionChanged();
		}
			
	}
	
	public void handleInputRight() {
		if (!items.isEmpty()) {
			selectedItem++;
			if (selectedItem >= items.size())
				selectedItem = items.size() - 1;
			onSelectionChanged();
		}
			
	}

}
