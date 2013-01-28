/*
 * AnimatedSprite.java
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

package com.redarctic.nadia.baseengine.sprite;

import java.util.concurrent.ConcurrentHashMap;

//import com.redarctic.dragonsfate.activities.MainSurface;
import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.baseengine.tileengine.Camera;
import com.redarctic.nadia.baseengine.tileengine.TileMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * AnimatedSprite
 * <p>Contains all the set of needed methods to animate any kind of sprite, based on a texture and a set of tiles
 * that compounds the animation</p>
 * @author Emmanuel Arana Corzo <emmanuel.arana@gmail.com>
 */
public class AnimatedSprite {
	ConcurrentHashMap<AnimationKey, Animation> animations;
	AnimationKey currentAnimation = AnimationKey.DOWN;
	boolean animating = false;
	
	Bitmap texture;
	Point position = new Point(
			/*MainSurface.getGameRef().getScreenSize().x >> 1, 
			MainSurface.getGameRef().getScreenSize().y >> 1*/0, 0);
	Point velocity = new Point();
	float speed = 2.0f;
	
	
	public AnimationKey getCurrentAnimation() {
		return currentAnimation;
	}
	
	public void setCurrentAnimation(AnimationKey currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
	
	public boolean isAnimating() {
		return animating;
	}
	
	public void setAnimating(boolean animating) {
		this.animating = animating;
	}
	
	public int getWidth() {
		return this.animations.get(this.currentAnimation).getFrameWidth();
	}
	
	public int getHeight() {
		return this.animations.get(this.currentAnimation).getFrameHeight();
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = MathHelper.clamp(speed, 1.0f, 16.0f);
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getVelocity() {
		return velocity;
	}

	public void setVelocity(Point velocity) {
		this.velocity = velocity;
		
		if (velocity.x != 0 && velocity.y != 0)
			velocity = MathHelper.normalize(velocity);
	}
	
	public AnimatedSprite(Bitmap sprite, ConcurrentHashMap<AnimationKey, Animation> animation) {
		texture = sprite;
		
		animations = new ConcurrentHashMap<AnimationKey, Animation>();
		for (AnimationKey key : animation.keySet()) {
			try {
				animations.put(key, (Animation)animation.get(key).clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void update(Canvas canvas) {
		if (isAnimating())
			animations.get(this.currentAnimation).update(canvas);
	}
	
	public void drawMe(Canvas canvas, Camera camera) {
		Rect dst = new Rect(
				this.position.x, 
				this.position.y, 
				this.position.x + 
				this.animations.get(currentAnimation).getFrameWidth(),
				this.position.y + 
				this.animations.get(currentAnimation).getFrameHeight());
		Rect src = new Rect(
				(int)animations.get(currentAnimation).getCurrentFrameRect().left,
				(int)animations.get(currentAnimation).getCurrentFrameRect().top,
				(int)animations.get(currentAnimation).getCurrentFrameRect().right,
				(int)animations.get(currentAnimation).getCurrentFrameRect().bottom
				);
		canvas.drawBitmap(
				this.texture, 
				src, 
				dst, 
				null);
	}
	
	public void lockToMap() {
		position.x = (int) MathHelper.clamp(
				position.x, 0, TileMap.getWidthInPixels() - getWidth());
		position.y = (int) MathHelper.clamp(
				position.y, 0, TileMap.getHeightInPixels() - getHeight());
	}
}
