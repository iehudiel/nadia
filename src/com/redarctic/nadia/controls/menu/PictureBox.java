/*
 * PictureBox.java
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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

public class PictureBox extends Control {

	Bitmap image;
	Rect sourceRect;
	Rect destRect;
	
	public PictureBox(Bitmap image, Rect destination) {
		this.setImage(image);
		this.setDestRect(destination);
		Rect srcRect = new Rect(0, 0, this.image.getWidth(), this.image.getHeight());
		this.setSourceRect(srcRect);
		this.setColor(Color.WHITE);
	}
	
	public PictureBox(Bitmap image, Rect destination, Rect source) {
		this.setImage(image);
		this.setDestRect(destination);
		this.setSourceRect(source);
		this.setColor(Color.WHITE);
	}
	
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Rect getSourceRect() {
		return sourceRect;
	}

	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}

	public Rect getDestRect() {
		return destRect;
	}

	public void setDestRect(Rect destRect) {
		this.destRect = destRect;
	}

	@Override
	public void initialize() {		
	}

	@Override
	public void update(Canvas canvas) {		
	}

	@Override
	public void drawMe(Canvas canvas) {
		canvas.drawBitmap(image, sourceRect, destRect, null);
	}

	@Override
	public void handleInput() {
	}
	
	public synchronized void setPosition(Point newPosition) {
		// TODO: Check the calculations
		this.destRect = new Rect(newPosition.x, newPosition.y,
				newPosition.x + sourceRect.width(), 
				newPosition.y + sourceRect.height());
	}

}
