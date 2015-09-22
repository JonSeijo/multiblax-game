package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jashlaviu.multiblax.TextureLoader;

public class ShootLong extends ActorGeneric{
	
    public ShootLong (float posX, float posY){
        super(posX, posY);
        setRegion(new TextureRegion(TextureLoader.shootLongNormal));
        
        // The height is zero, it will grow every frame
        collisionBounds = new Rectangle(posX, posY, 12, 0);
        setCollisionBounds(posX, posY, 12,0);

        velocity.y = 600;       
    }
    
    @Override
    public void updateY(float delta){
    	// Makes the shoot higher, its not a bullet    	
    	collisionBounds.height += Math.round(velocity.y * delta);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getRegion(), getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), collisionBounds.height, //collisionBounds.height,
                getScaleX(), getScaleY(), getRotation());
    }
   
    


}
