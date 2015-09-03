package com.jashlaviu.multiblax.actors;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jashlaviu.multiblax.TextureLoader;

public class ActorGeneric extends Actor{

    protected TextureRegion region;
    protected Rectangle collisionBounds;

    protected Vector2 velocity;
    protected Vector2 collisionBoundsOffset;
    
    protected float oldX, oldY;


    public ActorGeneric(float posX, float posY){
        setRegion(TextureLoader.noTextureRegion);
        collisionBoundsOffset = new Vector2(0, 0);
        collisionBounds = new Rectangle(posX, posY, getWidth(), getHeight());

        setPosition(posX, posY);
        oldX = posX;
        oldY = posY;
        velocity = new Vector2(0, 0);
    }


    public void updateY(float delta){
        this.moveBy(0, MathUtils.round(velocity.y * delta));
    }

    public void updateX(float delta){
        this.moveBy(MathUtils.round(velocity.x * delta), 0);
    }


    @Override
    public void act(float delta){
        super.act(delta);
    }

    @Override
    public void positionChanged (){
        collisionBounds.x += getX() - oldX;
        collisionBounds.y += getY() - oldY;
        
        oldX = getX();
        oldY = getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getRegion(), getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    protected void setRegion(TextureRegion newRegion){
        region = newRegion;
        setSize(region.getRegionWidth(), region.getRegionHeight());
    }

    protected void setCollisionBounds(float x, float y, float width, float height){
        collisionBounds.x = x;
        collisionBounds.y = y;
        collisionBounds.width = width;
        collisionBounds.height = height;
    }

    public boolean collides(ActorGeneric actor2){
        return this.getCollisionBounds().overlaps(actor2.getCollisionBounds());
    }

    public Rectangle getCollisionBounds(){
        return collisionBounds;
    }

    public float getCollisionOffsetX(){
        return collisionBoundsOffset.x;
    }

    public float getCollisionOffsetY(){
        return collisionBoundsOffset.y;
    }

    public void setVelocityY(float y){
        velocity.y = y;
    }

    public void setVelocityX(float x){
        velocity.x = x;
    }

    public TextureRegion getRegion(){
        return region;
    }
}