package com.jashlaviu.multiblax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureLoader {

    private Texture playerTexture, floorTexture, noTextureTexture;
    private Texture ballBlueBigTexture, ballBlueMediumTexture, ballBlueSmallTexture; 
    private Texture backgroundTexture, wallTexture;
    private Texture squarePinkTexture, shootLong0Texture;

    public static TextureRegion playerRegion, floorRegion, noTextureRegion;
    public static TextureRegion ballBlueBigRegion, ballBlueMediumRegion, ballBlueSmallRegion;
    public static TextureRegion backgroundRegion, wallRegion;
    public static TextureRegion squarePinkRegion, shootLong0Region;

    public TextureLoader(){
        shootLong0Texture = new Texture(Gdx.files.internal("shoot_long_0.png"));
        shootLong0Region = new TextureRegion(shootLong0Texture);

        squarePinkTexture = new Texture(Gdx.files.internal("square_pink.png"));
        squarePinkRegion = new TextureRegion(squarePinkTexture);

        wallTexture = new Texture(Gdx.files.internal("wall_test.png"));
        wallRegion = new TextureRegion(wallTexture);

        backgroundTexture = new Texture(Gdx.files.internal("background_0.png"));
        backgroundRegion = new TextureRegion(backgroundTexture);

        ballBlueBigTexture = new Texture(Gdx.files.internal("ball_blue_big.png"));
        ballBlueBigRegion = new TextureRegion(ballBlueBigTexture);
        
        ballBlueMediumTexture = new Texture(Gdx.files.internal("ball_blue_medium.png"));
        ballBlueMediumRegion = new TextureRegion(ballBlueMediumTexture);
        
        ballBlueSmallTexture = new Texture(Gdx.files.internal("ball_blue_small.png"));
        ballBlueSmallRegion = new TextureRegion(ballBlueSmallTexture);

        playerTexture = new Texture(Gdx.files.internal("player_test.png"));
        playerRegion = new TextureRegion(playerTexture);

        noTextureTexture = new Texture(Gdx.files.internal("error_texture.png"));
        noTextureRegion = new TextureRegion(noTextureTexture);

        floorTexture = new Texture(Gdx.files.internal("floor_test.png"));
        floorRegion = new TextureRegion(floorTexture);
    }

    public void dispose(){
        playerTexture.dispose();
        floorTexture.dispose();
        noTextureTexture.dispose();
        ballBlueBigTexture.dispose();
        ballBlueMediumTexture.dispose();
        ballBlueMediumTexture.dispose();
        backgroundTexture.dispose();
        wallTexture.dispose();
        squarePinkTexture.dispose();
        shootLong0Texture.dispose();
    }

}