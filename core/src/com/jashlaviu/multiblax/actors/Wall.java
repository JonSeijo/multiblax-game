package com.jashlaviu.multiblax.actors;

import com.jashlaviu.multiblax.actors.ActorGeneric;
import com.jashlaviu.multiblax.TextureLoader;

public class Wall extends ActorGeneric {

    public static final int FLOOR = 0;
    public static final int SIDE_LEFT = -1;
    public static final int SIDE_RIGHT = 1;
    public static final int ROOF = 2;

    private int type;

    public Wall(float posX, float posY, int type){
        super(posX, posY);
        this.type = type;

        if(type == FLOOR || type == ROOF) {
            setRegion(TextureLoader.floorRegion);
        }
        if(type == SIDE_LEFT || type == SIDE_RIGHT) {
            setRegion(TextureLoader.wallRegion);
        }

        setCollisionBounds(posX, posY, getWidth(), getHeight());
    }

    public int getType() {
        return type;
    }
}