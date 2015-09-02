package com.jashlaviu.multiblax;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MultiblaxGame extends Game {
	private SpriteBatch batch;
	private TextureLoader textureLoader;

	@Override
	public void create () {
		textureLoader = new TextureLoader();
		batch = new SpriteBatch();
		this.setScreen(new MultiblaxScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose(){
		super.dispose();
		batch.dispose();
		textureLoader.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}
}
