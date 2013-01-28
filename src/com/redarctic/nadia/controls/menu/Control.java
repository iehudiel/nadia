/*
 * Control.java
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

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.redarctic.nadia.baseengine.DrawableObject;
import com.redarctic.nadia.controls.DirectionalPad;

public abstract class Control implements DrawableObject {
	protected String name;
    protected String text;
    protected Point size;
    protected Point position;
    protected Object value;
    protected boolean hasFocus;
    protected boolean enabled;
    protected boolean visible;
    protected boolean tabStop;
    protected Paint spriteFont;
    protected long color;
    protected String type;
    protected DirectionalPad gamepad;    
    
    public Control() {
		this.color = Color.WHITE;
		this.enabled = true;
		this.visible = true;
		this.spriteFont = ControlManager.getSpriteFont();
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public Point getSize() {
		return size;
	}
	public void setSize(Point size) {
		this.size = size;
	}
	
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
		this.position.y = (int)this.position.y;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean isHasFocus() {
		return hasFocus;
	}
	public void setHasFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isTabStop() {
		return tabStop;
	}
	public void setTabStop(boolean tabStop) {
		this.tabStop = tabStop;
	}
	
	public Paint getSpriteFont() {
		return spriteFont;
	}
	public void setSpriteFont(Paint spriteFont) {
		this.spriteFont = spriteFont;
	}
	
	public long getColor() {
		return color;
	}
	public void setColor(long color) {
		this.color = color;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
		
	public DirectionalPad getGamepad() {
		return gamepad;
	}

	public void setGamepad(DirectionalPad gamepad) {
		this.gamepad = gamepad;
	}

	public abstract void handleInput();
}
