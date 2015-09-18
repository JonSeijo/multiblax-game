package com.jashlaviu.multiblax;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter{

    private MultiblaxScreen gameScreen;
    public static final int MOVE_RIGHT = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_UP = 2;
    public static final int MOVE_DOWN = 3;

    private boolean[] movementInput;


    public InputHandler(MultiblaxScreen gameScreen){
        this.gameScreen = gameScreen;
        movementInput = new boolean[4];
    }

    @Override
    public boolean keyDown(int keycode){
        if(keycode == Input.Keys.RIGHT){
            movementInput[MOVE_RIGHT] = true;
            return true;
        }
        if(keycode == Input.Keys.LEFT){
            movementInput[MOVE_LEFT] = true;
            return true;
        }
        if(keycode == Input.Keys.SPACE || keycode == Input.Keys.UP){
            movementInput[MOVE_UP] = true;
            return true;
        }
        if(keycode == Input.Keys.DOWN){
            movementInput[MOVE_DOWN] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode){
        if(keycode == Input.Keys.RIGHT){
            movementInput[MOVE_RIGHT] = false;
            return true;
        }
        if(keycode == Input.Keys.LEFT){
            movementInput[MOVE_LEFT] = false;
            return true;
        }
        if(keycode == Input.Keys.SPACE || keycode == Input.Keys.UP){
            movementInput[MOVE_UP] = false;
            return true;
        }
        if(keycode == Input.Keys.DOWN){
            movementInput[MOVE_DOWN] = false;
            return true;
        }
        return false;
    }

    public boolean[] getMovementInput(){
        return movementInput;
    }

}