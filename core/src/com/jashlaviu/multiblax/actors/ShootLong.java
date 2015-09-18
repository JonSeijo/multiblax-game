package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jashlaviu.multiblax.TextureLoader;

public class ShootLong extends ActorGeneric{
	
	private float flipTime, flipTimeMax;
	
    public ShootLong (float posX, float posY){
        super(posX, posY);
        setRegion(new TextureRegion(TextureLoader.shootLongNormal));
        
        collisionBounds = new Rectangle(posX, posY, 12, 0);
        setCollisionBounds(posX, posY, 12,0);
       // System.out.println(posX);

        velocity.y = 600; //previous one 370
        flipTimeMax = 0.085f;
    }
    
    @Override
    public void updateY(float delta){
    	collisionBounds.height += Math.round(velocity.y * delta);
    	
    	flipTime += delta;
    	if(flipTime > flipTimeMax){
    		flipTime = 0;
    		region.flip(true, false);
    	} 
    	
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getRegion(), getX(), (collisionBounds.y + collisionBounds.height)-getHeight(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }
   
    


}
