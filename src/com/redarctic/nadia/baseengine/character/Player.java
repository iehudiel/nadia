/*
 * Player.java
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

package com.redarctic.nadia.baseengine.character;

import android.graphics.Canvas;
import android.graphics.Point;

import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.baseengine.sprite.AnimatedSprite;
import com.redarctic.nadia.baseengine.sprite.AnimationKey;
import com.redarctic.nadia.baseengine.tileengine.Camera;
import com.redarctic.nadia.baseengine.tileengine.Camera.CameraMode;
import com.redarctic.nadia.collision.Rectangle;

/**
 * Player
 * This class is the character in the world map, this does not involves anyhow
 * character status, and battle related character 
 */
public class Player implements ISlotProvider {
	Camera camera;
	Point screenSize;
	AnimatedSprite sprite;
	
	/**
	 * getCamera
	 * Returns the camera attached to the player in the world
	 * @return Camera
	 * @see Camera 
	 */
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public AnimatedSprite getSprite() {
		return sprite;
	}
	
	public Player(Point screenSize, AnimatedSprite sprite) {
		this.screenSize = screenSize;
		Rectangle screenRectangle = new Rectangle(
				0, 
				0, 
				this.screenSize.x, 
				this.screenSize.y);
		this.camera = new Camera(screenRectangle);
		this.sprite = sprite;
	}
	
	public void zoomIn() {
		this.camera.zoomIn();
		if (this.camera.getCameraMode() == CameraMode.FOLLOW) {
			this.camera.lockToSprite(this.sprite);
		}
	}
	
	public void zoomOut() {
		this.camera.zoomOut();
		if (this.camera.getCameraMode() == CameraMode.FOLLOW) {
			this.camera.lockToSprite(this.sprite);
		}
	}
	
	public void moveUp() {
		Point motion = new Point();
		this.sprite.setCurrentAnimation(AnimationKey.UP);
		motion.y = -1;
		
		movementFixer(motion);
	}
	
	public void moveDown() {
		Point motion = new Point();
		this.sprite.setCurrentAnimation(AnimationKey.DOWN);
		motion.y = 1;
		
		movementFixer(motion);
	}
	
	public void moveLeft() {
		Point motion = new Point();
		this.sprite.setCurrentAnimation(AnimationKey.LEFT);
		motion.x = -1;
		
		movementFixer(motion);
	}
	
	public void moveRight() {
		Point motion = new Point();
		this.sprite.setCurrentAnimation(AnimationKey.RIGHT);
		motion.x = 1;
		
		movementFixer(motion);
	}
	
	public void stopAnimate() {
		this.sprite.setAnimating(false);
	}
	
	private void movementFixer(Point motion) {
		if (!motion.equals(0, 0)) {
			this.sprite.setAnimating(true);
			motion = MathHelper.normalize(motion);
			
			Point newPosition = new Point(this.sprite.getPosition());
			newPosition.x += motion.x * this.sprite.getSpeed();
			newPosition.y += motion.y * this.sprite.getSpeed();
			this.sprite.setPosition(newPosition);
			this.sprite.lockToMap();
			
			if (this.camera.getCameraMode() == CameraMode.FOLLOW)
				this.camera.lockToSprite(this.sprite);
		}
		else {
			sprite.setAnimating(false);
		}
	}
	
	public void update(Canvas canvas) {
		this.camera.update(canvas);
		this.sprite.update(canvas);
		
		Point motion = new Point(0, 0);
		movementFixer(motion);
	}
	
	public void drawMe(Canvas canvas) {
		this.sprite.drawMe(canvas, this.camera);
	}
}
