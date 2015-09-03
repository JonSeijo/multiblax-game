package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.math.Rectangle;
import com.jashlaviu.multiblax.TextureLoader;

public class Shoot extends ActorGeneric{

    public Shoot (float posX, float posY){
        super(posX, posY);
        setRegion(TextureLoader.shootLong0Region);
        
        collisionBounds = new Rectangle(posX, posY, 32, 32);
        setCollisionBounds(posX, posY, 32, 32);
        System.out.println(posX);

        velocity.y = 300;
    }
    
    @Override
    public void updateY(float delta){
    	collisionBounds.height += Math.round(velocity.y * delta);
    	
    }
   
    


}
