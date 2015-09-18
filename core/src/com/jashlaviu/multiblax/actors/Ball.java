package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.math.MathUtils;
import com.jashlaviu.multiblax.TextureLoader;

public class Ball extends ActorGeneric{
	
	public static final int SIZE_1 = 1;
	public static final int SIZE_2 = 2;
	public static final int SIZE_3 = 3;
	public static final int SIZE_4 = 4;
	
    private float gravity, maxSpeedY, bounceAmount;
    private int size;

    public Ball (float posX, float posY, int size){
        super(posX, posY);
        this.size = size;
        
        gravity = -1400;
        maxSpeedY = 1000;
        bounceAmount = 600;
        
        if(size == SIZE_3){
        	bounceAmount = 800;
        	setRegion(TextureLoader.ballBlueBig);        	
        }
        if(size == SIZE_2){
        	bounceAmount = 700;
        	setRegion(TextureLoader.ballBlueMedium);
        }
        if(size == SIZE_1){
        	bounceAmount = 600;
        	setRegion(TextureLoader.ballBlueSmall);
        }
        
        // Collision bounds changes with respect of the ball size
        setCollisionBounds(posX, posY, getWidth(), getHeight());
    }

    @Override
    public void updateY(float delta){
    	// There is a limit to the falling speed
        if(Math.abs(velocity.y) < maxSpeedY) {
            velocity.y += gravity * delta;
        }
        // Change the Y position using the velicity
        this.moveBy(0, MathUtils.round(velocity.y * delta));
    }
    
    public void flipVelocityX(){
    	velocity.x = -velocity.x;
    }

    public void bounceX(){
        velocity.x = -velocity.x;
    }
    
    public void bounceY(float factor){
    	velocity.y = bounceAmount * factor;
    }

    public void bounceY(){
        bounceY(1);
    }
    
    public int getSize(){
    	return size;
    }
}
