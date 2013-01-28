/*
 * CollisionPolygon.java
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

import java.util.ArrayList;

import android.graphics.Point;

public class CollisionPolygon {
	private ArrayList<Point> lVertex;
	
	public enum PolygonType {
		NOT_HANDLED,
		CONVEX,
		CONCAVE
	}
	
	public CollisionPolygon() {
		this.lVertex = new ArrayList<Point>();
	}
	
	public CollisionPolygon(ArrayList<Point> lVertex) throws NullPointerException, ConvexPolygonException {
		if (lVertex != null) {
			if ( PolygonType.CONVEX == CollisionPolygon.isConvex(lVertex) ) {
				this.lVertex = lVertex;
			}
			else {
				throw new ConvexPolygonException("A concave vertex cannot be added in a collisionable polygon");
			}
		}
		else {
			this.lVertex = new ArrayList<Point>();
			
			throw new NullPointerException();
		}
	}
	
	public void append(Point oPt) throws ConvexPolygonException {
		if (this.lVertex == null) {
			this.lVertex = new ArrayList<Point>();
		}
		
		if (this.lVertex.size() < 3) {
			this.lVertex.add(oPt);
		}
		else {
			ArrayList<Point> lCopyVertex = new ArrayList<Point>();
			lCopyVertex.addAll(lVertex);
			lCopyVertex.add(oPt);
			
			if ( PolygonType.CONVEX == CollisionPolygon.isConvex(lCopyVertex) ) {
				lVertex.add(oPt);
				lCopyVertex.clear();
				lCopyVertex = null;
			}
			else {
				throw new ConvexPolygonException("A concave vertex cannot be added in a collisionable polygon");
			}
		}
	}
	
	public int getNumberVertex() {
		return this.lVertex.size();
	}
	
	public boolean isPolygon() {
		return this.lVertex.size() > 2;
	}
	
	public Point getVertex(int n) throws NullPointerException {
		Point p = new Point();
		
		if (n < this.lVertex.size()) {
			if (this.lVertex.get(n) != null) {
				p.x = this.lVertex.get(n).x;
				p.y = this.lVertex.get(n).y;
			}
			else {
				throw new NullPointerException();
			}
		}
		
		return p;
	}
	
	public static PolygonType isConvex(ArrayList<Point> lVertex) {
		int i, j, k;
		int flag = 0;
		double z;
		int n = lVertex.size();
		
		if (n < 3) {
			return PolygonType.NOT_HANDLED;
		}		
		
		for (i = 0; i < n; i++) {
			j = (i + 1) % n;
			k = (i + 2) % n;
			z = (lVertex.get(j).x - lVertex.get(i).x) * (lVertex.get(k).y - lVertex.get(j).y);
			z -= (lVertex.get(j).y - lVertex.get(i).y) * (lVertex.get(k).x -lVertex.get(j).x);
			
			if (z < 0)
				flag |= 1;
			else if (z > 0)
				flag |= 2;
			if (flag == 3)
				return PolygonType.CONCAVE;
		}
		
		if (flag != 0)
			return PolygonType.CONVEX;
		else
			return PolygonType.NOT_HANDLED;
	}
	
	public static int zCrossProduct(Point p1, Point p2, Point p3) 
	throws NullPointerException {
		int iZCrossProduct = 0;
		
		if (p1 != null && p2 != null && p3 != null) {
			int idx1 = p2.x - p1.x;
			int idy1 = p2.y - p2.y;
			int idx2 = p3.x - p2.x;
			int idy2 = p3.y - p2.y;
			
			iZCrossProduct = idx1 * idy2 - idy1 * idx2;
		}
		else {
			throw new NullPointerException("The 3 points must be set different of null");
		}
		
		return iZCrossProduct;
	} 
}
