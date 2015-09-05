package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.math.MathUtils;
import com.jashlaviu.multiblax.TextureLoader;

public class Ball extends ActorGeneric{

    private float gravity, maxSpeedY;

    public Ball (float posX, float posY){
        super(posX, posY);

        setRegion(TextureLoader.ballBlueBigRegion);
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
}
