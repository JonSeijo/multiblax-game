package com.jashlaviu.multiblax.actors;

import com.badlogic.gdx.math.MathUtils;
import com.jashlaviu.multiblax.actors.ActorGeneric;
import com.jashlaviu.multiblax.TextureLoader;

public class Player extends ActorGeneric {

    private int rightMovement, leftMovement;
    private float gravity, sideSpeed, maxSpeedY;

    private int maxShoots;
    private float shootTime;

    public Player(float posX, float posY){
        super(posX, posY);

        setRegion(TextureLoader.playerRegion);
        setCollisionBounds(getX(), getY(), getWidth(), getHeight());

        gravity = -650;
        maxSpeedY = 1000;
        sideSpeed = 250;
        velocity.x = sideSpeed;

        rightMovement = leftMovement = 0;
        maxShoots = 2;
        shootTime = .5f;

    }

    @Override
    public void updateY(float delta){
        if(Math.abs(velocity.y) < maxSpeedY) {
            velocity.y += gravity * delta;
        }
        this.moveBy(0, MathUtils.round(velocity.y * delta));
    }

    @Override
    public void updateX(float delta){
        velocity.x = (leftMovement + rightMovement) * sideSpeed;
        this.moveBy( MathUtils.round(velocity.x * delta), 0);
    }

    public void jump(){
        velocity.y = 300;
    }


    public void movingRight(boolean right) {
        rightMovement = right ? 1 : 0;
    }

    public void movingLeft(boolean left) {
        leftMovement = left ? -1 : 0;
    }


    public float getShootTime(){
        return shootTime;
    }


    public int getMaxShoots(){
        return maxShoots;
    }

}