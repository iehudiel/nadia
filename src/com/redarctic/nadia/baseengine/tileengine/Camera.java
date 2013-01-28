/*
 * Camera.java
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

package com.redarctic.nadia.baseengine.tileengine;

import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.baseengine.sprite.AnimatedSprite;
import com.redarctic.nadia.collision.Rectangle;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;

/**
 * Camera
 * Sets the logic to handle a point of view of the scene.
 * The camera could be set as a Free or Follow mode.
 * @see CameraMode
 */
public class Camera implements ISlotProvider {
	private static final float DEFAULT_ZOOM_INCREMENT = 0.25f;
	public static final float ZOOM_REAL_SIZE = 1.0f;
	private static final float DEFAULT_SPEED = 4.0f;
	public static final float MAX_SPEED = 16.0f;
	public static final float MIN_SPEED = 1.0f;

	/**
	 * CameraMode
	 * <p>{@link CameraMode#FREE}
	 * {@link CameraMode#FOLLOW}</p>
	 * @author Emmanuel Arana Corzo <{@link emmanuel.arana@gmail.com}>
	 */
	public enum CameraMode {
		/**
		 * You can control the camera freely with the game pad
		 */
		FREE, 
		/**
		 * The camera will follow the character
		 */
		FOLLOW
	}
	
	private Point position;
	private float speed;
	private float zoom;
	private Rectangle viewportRectangle;
	private CameraMode mode;
	
	/**
	 * getPosition
	 * Gets the position of the camera in the current level
	 * @return {@link Point}
	 */
	public Point getPosition() {
		return this.position;
	}
	/**
	 * setPosition
	 * Sets the position given a point
	 * @param position The new position
	 */
	private void setPosition(Point position) {
		this.position = position;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = MathHelper.clamp(speed, MIN_SPEED, MAX_SPEED);
	}
	
	public float getZoom() {
		return this.zoom;
	}
	
	public CameraMode getCameraMode() {
		return this.mode;
	}
	
	public Matrix getTransformation() {
		Matrix identity = new Matrix();
		identity.postScale(zoom, zoom);
		identity.postTranslate(position.x, position.y);
		return identity;
	}
	
	public Rectangle getViewportRectangle() {
		return new Rectangle(
				viewportRectangle.left,
				viewportRectangle.top, 
				viewportRectangle.right, 
				viewportRectangle.bottom);
	}
	
	public Camera(Rectangle viewportRect) {
		speed = DEFAULT_SPEED;
		zoom = ZOOM_REAL_SIZE;
		this.viewportRectangle = viewportRect;
		mode = CameraMode.FOLLOW;
		this.position = new Point(
				0, 0
				);
	}
	
	public Camera(Rectangle viewportRect, Point position) {
		speed = DEFAULT_SPEED;
		zoom = ZOOM_REAL_SIZE;
		this.viewportRectangle = viewportRect;
		mode = CameraMode.FOLLOW;
		this.setPosition(position);
	}
	
	public void update(Canvas canvas) {
	}
	
	public void moveLeft() {
		if (CameraMode.FOLLOW != this.mode) {
			Point motion = new Point(0, 0);
			
			motion.x = (int)-speed;
			
			if (!motion.equals(0, 0)) {
				motion = MathHelper.normalize(motion);
				position.x += motion.x * speed;
				position.y += motion.y * speed;
				lockCamera();
			}
		}
	}
	
	public void moveRight() {
		if (CameraMode.FOLLOW != this.mode) {
			Point motion = new Point(0, 0);
			
			motion.x = (int)speed;
			
			if (!motion.equals(0, 0)) {
				motion = MathHelper.normalize(motion);
				position.x += motion.x * speed;
				position.y += motion.y * speed;
				lockCamera();
			}
		}
	}
	
	public void moveUp() {
		if (CameraMode.FOLLOW != this.mode) {
			Point motion = new Point(0, 0);
			
			motion.y = (int)-speed;
			
			if (!motion.equals(0, 0)) {
				motion = MathHelper.normalize(motion);
				position.x += motion.x * speed;
				position.y += motion.y * speed;
				lockCamera();
			}
		}
	}
	
	public void moveDown() {
		if (CameraMode.FOLLOW != this.mode) {
			Point motion = new Point(0, 0);
			
			motion.y = (int)speed;
			
			if (!motion.equals(0, 0)) {
				motion = MathHelper.normalize(motion);
				position.x += motion.x * speed;
				position.y += motion.y * speed;
				lockCamera();
			}
		}
	}
	
	private void lockCamera() {
		if (this.position != null && this.viewportRectangle != null) {
			this.position.x = Math.round( MathHelper.clamp(this.position.x, 0, 
					TileMap.getWidthInPixels() * this.zoom - this.viewportRectangle.width()) );
			this.position.y = Math.round( MathHelper.clamp(this.position.y, 0,
					TileMap.getHeightInPixels() * this.zoom - this.viewportRectangle.height()) );
		}
	}
	
	public void lockToSprite(AnimatedSprite sprite) {
		if (this.position != null && this.viewportRectangle != null) {
			this.position.x = (int) ( (sprite.getPosition().x + sprite.getWidth() >> 1) * 
					this.zoom - 
					( (this.viewportRectangle.width()) / 2) );
			this.position.y = (int) ( (sprite.getPosition().y + sprite.getHeight() >> 1) * 
					this.zoom - 
					( (this.viewportRectangle.height()) / 2) );
		}
		this.lockCamera();
	}
	
	public void toggleCameraMode() {
		if (this.mode == CameraMode.FOLLOW) {
			this.mode = CameraMode.FREE;
		}
		else if (mode == CameraMode.FREE) {
			this.mode = CameraMode.FOLLOW;
		}
	}
	
	public void zoomIn() {
		this.zoom += DEFAULT_ZOOM_INCREMENT;
		
		if (this.zoom > 2.5f)
			this.zoom = 2.5f;
		
		Point newPosition = MathHelper.pointMulScalar(this.getPosition(), (int)this.zoom);
		snapToPosition(newPosition);
	}
	
	public void zoomOut() {
		this.zoom -= DEFAULT_ZOOM_INCREMENT;
		
		if (this.zoom < 0.5f)
			this.zoom = 0.5f;
		
		Point newPosition = MathHelper.pointMulScalar(this.getPosition(), (int)this.zoom);
		snapToPosition(newPosition);
	}
	
	private void snapToPosition(Point newPosition) {
		this.position.x = (int) (newPosition.x - this.viewportRectangle.width() / 2);
		this.position.y = (int) (newPosition.y - this.viewportRectangle.height() / 2);
		lockCamera();
	}
}
