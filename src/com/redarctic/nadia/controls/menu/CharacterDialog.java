/*
 * CharacterDialog.java
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
import com.redarctic.nadia.ext.StringHelper;
import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.baseengine.ColorPallete;
import com.redarctic.nadia.baseengine.SpriteFont;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class CharacterDialog 
extends Control implements ISignalProvider, ISlotProvider {

	public enum DockPlace {
		UP, DOWN, NO_DOCKABLE
	}

	public static final String SIGNAL_CHANGED_CHARACTER_NAME = "CHANGED_CHARACTER_NAME";
	public static final String SIGNAL_CHANGED_MESSAGE = "CHANGED_MESSAGE";
	public static final String SIGNAL_NEW_PAGE_REQUESTED = "NEW_PAGE_REQUESTED";

	private String characterName;
	private String message;

	private DockPlace dockPlace;

	private Bitmap characterFace;

	private int backgroundColor1;
	private int backgroundColor2;
	private int fontColor;

	private Paint spriteFont;
	private ControlBorder border;

	private int width;
	private int numCharsPerRow;
	private int numRows;

	public CharacterDialog() {
		super();
		setTabStop(true);
		setEnabled(true);
		setHasFocus(false);
		this.position = new Point(0, 0);
		this.characterName = "*";
		this.message = "";
		this.spriteFont = SpriteFont
				.getDefaultFont(ColorPallete.COLOR_MENU_TEXT_LIGHT);
		this.backgroundColor1 = ColorPallete.COLOR_MENU_BOX_DFLT_LIGHT;
		this.backgroundColor2 = ColorPallete.COLOR_MENU_BOX_DFLT_DARK;
		this.dockPlace = DockPlace.UP;
		this.width = 0;
		this.numCharsPerRow = 0;
	}

	public CharacterDialog(String message) {
		super();
		setTabStop(true);
		setEnabled(true);
		setHasFocus(false);
		this.position = new Point(0, 0);
		this.characterName = "*";
		this.setMessage(message);
		this.spriteFont = SpriteFont
				.getDefaultFont(ColorPallete.COLOR_MENU_TEXT_LIGHT);
		this.backgroundColor1 = ColorPallete.COLOR_MENU_BOX_DFLT_LIGHT;
		this.backgroundColor2 = ColorPallete.COLOR_MENU_BOX_DFLT_DARK;
		this.dockPlace = DockPlace.UP;
		this.width = 0;
		this.numCharsPerRow = 0;
	}

	public CharacterDialog(String characterName, String message) {
		super();
		setTabStop(true);
		setEnabled(true);
		setHasFocus(false);
		this.position = new Point(0, 0);
		setMessage(message);
		setCharacterName(characterName);
		this.spriteFont = SpriteFont
				.getDefaultFont(ColorPallete.COLOR_MENU_TEXT_LIGHT);
		this.backgroundColor1 = ColorPallete.COLOR_MENU_BOX_DFLT_LIGHT;
		this.backgroundColor2 = ColorPallete.COLOR_MENU_BOX_DFLT_DARK;
		this.dockPlace = DockPlace.UP;
		this.width = 0;
		this.numCharsPerRow = 0;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = ((!characterName.equalsIgnoreCase("")) ? characterName
				: "*");
		SignalSlotMap.fastEmit(this, SIGNAL_CHANGED_CHARACTER_NAME);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if (message != null) {
			this.message = message;
			SignalSlotMap.fastEmit(this, SIGNAL_CHANGED_MESSAGE);
		}
	}

	public DockPlace getDockPlace() {
		return dockPlace;
	}

	public void setDockPlace(DockPlace dockPlace) {
		this.dockPlace = dockPlace;
	}

	public Bitmap getCharacterFace() {
		return characterFace;
	}

	public void setCharacterFace(Bitmap characterFace) {
		this.characterFace = characterFace;
	}

	public int getBackgroundColor1() {
		return backgroundColor1;
	}

	public void setBackgroundColor1(int backgroundColor1) {
		this.backgroundColor1 = backgroundColor1;
	}

	public int getBackgroundColor2() {
		return backgroundColor2;
	}

	public void setBackgroundColor2(int backgroundColor2) {
		this.backgroundColor2 = backgroundColor2;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		if (numRows > 0) {
			this.numRows = numRows;
		}
		else {
			this.numRows = 1;
		}
	}

	/**
	 * initialize Initialize the position values, once all the information is
	 * set
	 */
	@Override
	public void initialize() {
		this.border = new ControlBorder(this.getPosition(), new Point(
				this.width, this.caculateHeight()), this.backgroundColor1,
				this.backgroundColor2);
		calculatePosition();
		this.border.setPosition(getPosition());
	}

	/**
	 * calculatePosition
	 */
	public void calculatePosition() {
		switch (this.dockPlace) {
		case UP:
			this.position.x = this.position.y = 0;
			break;
		case DOWN:
			this.position.x = 0;
			this.position.y -= this.caculateHeight();
			break;
		case NO_DOCKABLE:
			if (this.position.x < 0) {
				this.position.x = 0;
			}
			if (this.position.y < 0) {
				this.position.y = 0;
			}
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * calculatePosition
	 */
	public void calculatePosition(Point screenSize) {
		switch (this.dockPlace) {
		case UP:
			this.position.x = this.position.y = 0;
			break;
		case DOWN:
			this.position.x = 0;
			this.position.y = screenSize.y - this.caculateHeight();
			break;
		case NO_DOCKABLE:
			this.position.x = (int)MathHelper.clamp(this.position.x, 0, screenSize.x);
			this.position.y = (int)MathHelper.clamp(this.position.y, 0, screenSize.y);			
			break;
		default:
			break;
		}
	}

	private int caculateHeight() {
		int height = 0;

		if (numRows > 0) {
			height = (SpriteFont.getMeasureString("A").y * (numRows + 3));
		}

		return height;
	}

	@Override
	public void update(Canvas canvas) {
		if (this.width == 0) {
			this.width = canvas.getWidth();
		}
		
		if (this.numCharsPerRow == 0) {
			this.numCharsPerRow = this.width / SpriteFont.getMeasureString("A").x;
		}
	}

	@Override
	public void drawMe(Canvas canvas) {
		this.border.drawMe(canvas);
		int rowHeight = SpriteFont.getMeasureString(characterName).y;
		Point charNamePos = new Point(this.position.x + 10, this.position.y + rowHeight);
		SpriteFont.drawText(canvas, this.characterName + ":", charNamePos, this.spriteFont);
		
		if (numRows > 0) {			
			ArrayList<String> messageLines = StringHelper.wrapToLines(this.message, this.numCharsPerRow);
			Point pos = new Point(charNamePos);
			pos.y += rowHeight;
			int i = 0;
			for (String s : messageLines) {
				if (i < numRows) {
					SpriteFont.drawText(canvas, s, pos, this.spriteFont);
					pos.y += rowHeight;
					i++;
				}
			}
			
//			String strPress = "Press Action";
//			int nCharsPress = strPress.length();
//			for (i = 0; i < (this.numCharsPerRow - nCharsPress); i++) {
//				strPress = " " + strPress;
//			}
//			pos.y += rowHeight;
			//SpriteFont.drawText(canvas, strPress, pos, this.spriteFont);			
		}
	}

	@Override
	public void handleInput() {		
	}

}
