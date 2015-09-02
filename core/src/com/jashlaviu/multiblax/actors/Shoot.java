package com.jashlaviu.multiblax.actors;

import com.jashlaviu.multiblax.TextureLoader;

public class Shoot extends ActorGeneric{

    public Shoot (float posX, float posY){
        super(posX, posY);
        setRegion(TextureLoader.squarePinkRegion);
        setCollisionBounds(posX, posY, getWidth(), getHeight());

        velocity.y = 300;
    }


}
