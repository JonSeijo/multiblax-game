package com.jashlaviu.multiblax.actors;

import com.jashlaviu.multiblax.TextureLoader;

public class Background extends ActorGeneric{

    public Background (){
        super(0, 0);
        setRegion(TextureLoader.background1);
    }
}