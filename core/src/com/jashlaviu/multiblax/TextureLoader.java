package com.jashlaviu.multiblax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureLoader {
    
    private TextureAtlas atlas;
    
    public static TextureRegion noTexture;
    public static TextureRegion shootLongNormal, shootLongFreeze, shootBullet;
    public static TextureRegion playerNormal;
    public static TextureRegion ballBlueBig, ballBlueMedium, ballBlueSmall;
    public static TextureRegion background1, floor, wall;
    
    public TextureLoader(){
    	atlas = new TextureAtlas(Gdx.files.internal("multiblax_atlas.atlas"));
    	
    	noTexture = atlas.findRegion("error_texture");
    	
    	shootLongNormal = atlas.findRegion("shoot_long", 5);
    	playerNormal = atlas.findRegion("player", 1);
    	
    	ballBlueBig = atlas.findRegion("ball_01", 3);
    	ballBlueMedium = atlas.findRegion("ball_01", 2);
    	ballBlueSmall = atlas.findRegion("ball_01", 1);
    	
    	background1 = atlas.findRegion("background", 1);
    	floor = atlas.findRegion("floor", 1);
    	wall = atlas.findRegion("wall", 1);
    }

    public void dispose(){
    	atlas.dispose();
    }

}