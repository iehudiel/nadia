/*
 * ControlBorder.java
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

import com.redarctic.nadia.baseengine.ColorPallete;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;

public class ControlBorder extends Control {
	private Point size;
	private Point position;
	private Paint fill;
	private int gradientColor1;
	private int gradientColor2;	
	
	public ControlBorder(Point position, Point size, int gradientColor1, int gradientColor2) {
		this.setPosition(position);
		this.setSize(size);
		
		this.fill = new Paint();
		this.fill.setStyle(Style.FILL);
		this.fill.setColor(Color.DKGRAY);
		this.fill.setAlpha(200);
		
		this.gradientColor1 = gradientColor1;
		this.gradientColor2 = gradientColor2;
		
		this.changeGradient();
	}
	
	public ControlBorder(Point position, Point size) {
		super();
		this.setPosition(position);
		this.setSize(size);
		
		this.fill = new Paint();
		this.fill.setStyle(Style.FILL);
		this.fill.setColor(Color.DKGRAY);
		this.fill.setAlpha(200);
		
		this.gradientColor1 = ColorPallete.COLOR_MENU_BOX_DFLT_DARK;
		this.gradientColor2 = ColorPallete.COLOR_MENU_BOX_DFLT_LIGHT;
		
		this.changeGradient();
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
	}

	public Paint getFill() {
		return fill;
	}

	public int getGradientColor1() {
		return gradientColor1;
	}

	public void setGradientColor1(int gradientColor1) {
		this.gradientColor1 = gradientColor1;		
		changeGradient();		
	}

	public int getGradientColor2() {
		return gradientColor2;
	}

	public void setGradientColor2(int gradientColor2) {
		this.gradientColor2 = gradientColor2;
		changeGradient();
	}
	
	public void setGradientColors(int color1, int color2) {
		this.gradientColor1 = color1;
		this.gradientColor2 = color2;
		changeGradient();
	}
	
	private void changeGradient() {
		LinearGradient gradient = new LinearGradient(0, 0, size.x, size.y, 
				this.gradientColor1, this.gradientColor2, 
				Shader.TileMode.MIRROR);
		
		this.fill.setShader(gradient);
	}

	@Override
	public void initialize() {
	}
	
	@Override
	public void update(Canvas canvas) {
	}
	@Override
	public void drawMe(Canvas canvas) {
		RectF rect = new RectF((float)position.x, (float)position.y,
				(float)(position.x + size.x), (float)(position.y + size.y));
		canvas.drawRoundRect(rect, 5, 5, fill);
	}
	@Override
	public void handleInput() {	
	}
}
