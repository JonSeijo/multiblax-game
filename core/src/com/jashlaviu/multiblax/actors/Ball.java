package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.math.MathUtils;
import com.jashlaviu.multiblax.TextureLoader;

public class Ball extends ActorGeneric{
	
	public static final int SIZE_1 = 1;
	public static final int SIZE_2 = 2;
	public static final int SIZE_3 = 3;
	public static final int SIZE_4 = 4;
	
    private float gravity, maxSpeedY;
    private int size;

    public Ball (float posX, float posY, int size){
        super(posX, posY);
        this.size = size;
        
        if(size == SIZE_3){
        	setRegion(TextureLoader.ballBlueBigRegion);        	
        }
        if(size == SIZE_2){
        	setRegion(TextureLoader.ballBlueMediumRegion);
        }
        if(size == SIZE_1){
        	setRegion(TextureLoader.ballBlueSmallRegion);
        }
        setCollisionBounds(posX, posY, getWidth(), getHeight());
        
        gravity = -650;
        maxSpeedY = 1000;

    }

    @Override
    public void updateY(float delta){
        if(Math.abs(velocity.y) < maxSpeedY) {
            velocity.y += gravity * delta;
        }
        this.moveBy(0, MathUtils.round(velocity.y * delta));
    }
    
    public void flipVelocityX(){
    	velocity.x = -velocity.x;
    }

    public void bounceX(){
        velocity.x = -velocity.x;
    }

    public void bounceY(){
        velocity.y = 600;
    }
    
    public int getSize(){
    	return size;
    }
}
