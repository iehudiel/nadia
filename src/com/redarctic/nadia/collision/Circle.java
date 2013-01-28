/*
 * Circle.java
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

package com.redarctic.nadia.collision;

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;

import android.graphics.Point;

public class Circle implements ISignalProvider {
	private volatile Point center;
	private volatile long radius;	
	
	public static final String SIGNAL_INTERSECTS_POINT = "INTERSECTS_POINT";
	public static final String SIGNAL_INTERSECTS_CIRCLE = "INTERSECTS_CIRCLE";
	public static final String SIGNAL_INTERSECTS_RECT = "INTERSECTS_RECT";
	public static final String SIGNAL_INTERSECTS_POLY = "INTERSECTS_POLY";
	
	/**
	 * Circle
	 */
	public Circle(Point center, long radius) throws NullPointerException {
		if (center != null) {
			this.center = center;
			this.radius = Math.abs(radius);
		}
		else {
			throw new NullPointerException("The parameter center is null");
		}
	}
	
	/**
	 * Circle
	 */
	public Circle(int x, int y, int radius) {
		this.center = new Point(x, y);
		this.radius = Math.abs(radius);
	}
	
	/**
	 * getCenter
	 */	
	public Point getCenter() {
		return center;
	}
	
	/**
	 * setCenter
	 */
	public void setCenter(Point center) {
		this.center = center;
	}
	
	/**
	 * setCenter
	 */
	public void setCenter(int x, int y) {
		if ( this.center == null ) {
			this.center = new Point(x, y);
		}
		else {
			this.center.x = x;
			this.center.y = y;
		}
	}
	
	/**
	 * getX
	 */
	public int getX() {
		return ((this.center != null) ? this.center.x : 0);
	}
	
	/**
	 * setX
	 */
	public void setX(int x) {
		if ( this.center == null ) {
			this.center = new Point(x, 0);
		}
		else {
			this.center.x = x;
		}
	}
	
	/**
	 * getY
	 */
	public int getY() {
		return ((this.center != null) ? this.center.y : 0);
	}
	
	/**
	 * setY
	 */
	public void setY(int y) {
		if ( this.center == null ) {
			this.center = new Point(0, y);
		}
		else {
			this.center.y = y;
		}
	}
	
	/**
	 * getRadius
	 */
	public long getRadius() {
		return radius;
	}
	
	/**
	 * setRadius
	 */
	public void setRadius(long radius) {
		this.radius = Math.abs(radius);
	}
	
	/**
	 * intersects
	 */
	public boolean intersects(Point point) {
		boolean fIntersects = false;
		
		/*long lDistance = Math.round( 
				Math.sqrt( 
						Math.pow((this.center.x - point.x), 2) + 
						Math.pow((this.center.y - point.y), 2)
						) );
		
		if (this.radius > lDistance) {
			fIntersects = true;
		}*/
		// Let's better work with squared, instead of doing square roots,
		// that way, it will cost less processor
		long lDistance = Math.round(
						Math.pow((this.center.x - point.x), 2) + 
						Math.pow((this.center.y - point.y), 2)
						);
		
		if ((long)Math.pow(this.radius, 2) > lDistance) {
			fIntersects = true;
			SignalSlotMap.fastEmit(this, Circle.SIGNAL_INTERSECTS_POINT);
		}
		
		return fIntersects;
	}
	
	/**
	 * intersects
	 */
	public boolean intersects(Circle other) {
		boolean fIntersects = false;
		
		long lSumRadius = this.radius + other.getRadius();
		/*long lDistance = Math.round(
				Math.sqrt(
						Math.pow(( this.center.x - other.getX() ), 2) +
						Math.pow(( this.center.y - other.getY() ), 2)
						)
				);
		
		if (lSumRadius > lDistance) {
			fIntersects = true;
		}*/
		long lDistance = Math.round(
						Math.pow(( this.center.x - other.getX() ), 2) +
						Math.pow(( this.center.y - other.getY() ), 2) 
						);
		
		if ((long)Math.pow(lSumRadius, 2) > lDistance) {
			fIntersects = true;
			SignalSlotMap.fastEmit(this, Circle.SIGNAL_INTERSECTS_CIRCLE);
		}
		
		return fIntersects;
	}
	
	public boolean intersects(CollisionPolygon other) {
		boolean fIntersects = false;
		
		for(int i = 0; i < other.getNumberVertex(); i++) {
			if (this.intersects(other.getVertex(i))) {
				fIntersects = true;
				SignalSlotMap.fastEmit(this, Circle.SIGNAL_INTERSECTS_POLY);
				break;
			}
		}
		
		return fIntersects;
	}
	
	public boolean intersects(Rectangle other) {
		boolean fIntersects = false;
		
		Point p1 = new Point( Math.round(other.left), Math.round(other.top) );
		Point p2 = new Point( Math.round(other.right), Math.round(other.top) );
		Point p3 = new Point( Math.round(other.left), Math.round(other.bottom) );
		Point p4 = new Point( Math.round(other.right), Math.round(other.bottom) );
		
		fIntersects = this.intersects(p1) || this.intersects(p2) || 
			this.intersects(p3) || this.intersects(p4);
		
		if (fIntersects)
			SignalSlotMap.fastEmit(this, Circle.SIGNAL_INTERSECTS_RECT);
		
		return fIntersects;
	}
}
