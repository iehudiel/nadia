/*
 * Sprite.java
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

package com.redarctic.nadia.baseengine;

import java.util.ArrayList;

import com.redarctic.nadia.collision.Circle;
import com.redarctic.nadia.collision.Rectangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class Sprite {
	private Bitmap bmpImage;
	private Rectangle rectSlicedZone;
	private Point dstPosition;
	private ArrayList<Circle> listCollisions;
	
	public Sprite(Bitmap bmpImage, Rectangle rectSlicedZone, Point dstPosition) 
	throws NullPointerException {
		if (bmpImage != null) {
			this.bmpImage = bmpImage;
		}
		else {
			throw new NullPointerException();
		}
		
		if (rectSlicedZone != null) {
			this.rectSlicedZone = rectSlicedZone;
		}
		else {
			throw new NullPointerException();
		}
		
		if (dstPosition != null) {
			this.dstPosition = dstPosition;
		}
		else {
			throw new NullPointerException();
		}
	}
	
	public Point getDstPosition() {
		return this.dstPosition;
	}
	
	public void setDstPosition(Point dstPosition) {
		if (dstPosition != null) {
			this.dstPosition.x = dstPosition.x;
			this.dstPosition.y = dstPosition.y;
		}
	}
	
	public void setDstPosition(int x, int y) {
		this.dstPosition.x = x;
		this.dstPosition.y = y;
	}
	
	public void move(Point ptSpeed) {
		if (this.dstPosition != null) {
			this.dstPosition.x += ptSpeed.x;
			this.dstPosition.y += ptSpeed.y;
		}		
	}
		
	public void move(int xSpeed, int ySpeed) {
		this.dstPosition.x += xSpeed;
		this.dstPosition.y += ySpeed;
	}
	
	public void drawMe(Canvas canvas) {
		if (this.rectSlicedZone != null && 
				this.dstPosition != null && 
				this.bmpImage != null) {
			Rect rectSrc = new Rect(Math.round(this.rectSlicedZone.left),
					Math.round(this.rectSlicedZone.top),
					Math.round(this.rectSlicedZone.right),
					Math.round(this.rectSlicedZone.bottom));
			Rect rectDst = new Rect(
					this.dstPosition.x,
					this.dstPosition.y,
					Math.round(this.rectSlicedZone.width() + this.dstPosition.x),
					Math.round(this.rectSlicedZone.height()
							+ this.dstPosition.y));
			canvas.drawBitmap(this.bmpImage, rectSrc, rectDst, null);
		}
	}
}
