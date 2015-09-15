package com.jashlaviu.multiblax;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jashlaviu.multiblax.actors.*;

import java.util.Iterator;

public class MultiblaxScreen extends ScreenAdapter{

    private SpriteBatch batch;
    private Stage stage;
    private Player player;
    private Wall floor, roof, wallRight, wallLeft;
    private Ball ball, ball2;

    private InputHandler inputHandler;
    private OrthographicCamera camera;

    private Array<Ball> balls;
    private Array<ShootLong> shoots;

    private float shootTimer = 0;

    public ShapeRenderer shaper = new ShapeRenderer();

    public MultiblaxScreen(MultiblaxGame game){
        batch = game.getBatch();
        camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(800, 600, camera), batch);

        player = new Player(100f, 390f);
        ball = new Ball(300, 400, Ball.SIZE_3);
        ball.setVelocityX(200);

        ball2 = new Ball(400, 300, Ball.SIZE_3);
        ball2.setVelocityX(-200);

        balls = new Array<Ball>();
        shoots = new Array<ShootLong>();

        balls.add(ball);
        balls.add(ball2);

        floor = new Wall(0, 0, Wall.FLOOR);
        roof = new Wall(0, 520, Wall.ROOF);
        wallLeft = new Wall(-40, 80, Wall.SIDE_LEFT);
        wallRight = new Wall(760, 80, Wall.SIDE_RIGHT);

        addActorsToStage();

        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBalls(delta);
        updatePlayer(delta);


        stage.draw();
        updateShoots(delta);
        stage.act(delta);
        
        Rectangle pBounds = player.getCollisionBounds();
        Rectangle b1Bounds = ball.getCollisionBounds();
        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(0,0,0,1);
       // shaper.rect(pBounds.x, pBounds.y, pBounds.width, pBounds.height);
      //  shaper.rect(b1Bounds.x, b1Bounds.y, b1Bounds.width, b1Bounds.height);
        shaper.end();
        
        // Estoy re-dibujando el piso para tapar el disparo
        stage.getBatch().begin();
        floor.draw(stage.getBatch(), 0);
        stage.getBatch().end();

        movePlayer();
    }
    
    public void divideBall(Ball ball, Rectangle shootBounds){
    	
    	ball.remove();
    	if(ball.getSize() > 1){
    		float newVelX = 150f;
    		float bounceFactor = 0.75f;
    		
	    	Ball newBall1 = new Ball(shootBounds.x - ball.getWidth() - 1,
	    			ball.getY(), ball.getSize()-1);
	    	newBall1.setVelocityX(-newVelX);
	    	newBall1.bounceY(bounceFactor);
	    	
	    	Ball newBall2 = new Ball(shootBounds.x + shootBounds.getWidth() + 1,
	    			ball.getY(), ball.getSize()-1);
	    	newBall2.setVelocityX(newVelX);
	    	newBall2.bounceY(bounceFactor);
	    	
	    	balls.add(newBall1);
	    	balls.add(newBall2);
	    	
	    	stage.addActor(newBall1);
	    	stage.addActor(newBall2);
    	}
    }

    public void shoot(){
        ShootLong shoot = new ShootLong(player.getX(), player.getY());
        shoots.add(shoot);
        stage.addActor(shoot);
    }

    private void updatePlayer(float delta){
        player.updateY(delta);
        Rectangle pBounds = player.getCollisionBounds();
        if(pBounds.y < floor.getTop()){
            player.setY(Math.round(floor.getTop() - player.getCollisionOffsetY()));
            player.setVelocityY(0);
        }

        player.updateX(delta);
        pBounds = player.getCollisionBounds();
        if(pBounds.x < wallLeft.getRight()){
            player.setX(Math.round(wallLeft.getRight() + player.getCollisionOffsetX()));
            player.setVelocityX(0);
        }

        if(pBounds.x + pBounds.width > wallRight.getX()){
            player.setX(Math.round(wallRight.getX() - pBounds.width - player.getCollisionOffsetX()));
            player.setVelocityX(0);
        }
       
    }

    private void updateShoots(float delta){
        shootTimer += delta;

        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(0,0,0,1);

        Iterator<ShootLong> shootIterator = shoots.iterator();
        while(shootIterator.hasNext()){

            ShootLong shoot = shootIterator.next();
            shoot.updateY(delta);
            Rectangle shootBounds = shoot.getCollisionBounds();

//            shaper.rect(shootBounds.x, shootBounds.y, shootBounds.width, shootBounds.height);

            if(shootBounds.getY() + shootBounds.getHeight() > roof.getY()){
                shoot.remove(); //Remove from stage
                shootIterator.remove();  //Remove from Array
                continue;
            }
            
            boolean ballDestroyed = false;
            
            Iterator<Ball> ballIterator = balls.iterator();
            while(ballIterator.hasNext()){
            	Ball ball = ballIterator.next();
            	if(shoot.collides(ball)){
            		ballIterator.remove();
            		divideBall(ball, shootBounds);
            		ballDestroyed = true;
            		break;
            	}
            }
            
            if(ballDestroyed){
        		shoot.remove();
        		shootIterator.remove();
            }
        }

        shaper.end();
    }

    private void updateBalls(float delta){
        for(Ball ball : balls){
            ball.updateY(delta);
            Rectangle bBounds = ball.getCollisionBounds();
            if(bBounds.getY() < floor.getTop()){
                ball.setY(Math.round(floor.getTop()));
                ball.bounceY();
            }

            ball.updateX(delta);
            bBounds = ball.getCollisionBounds();
            
            if(bBounds.x < wallLeft.getRight()){
                ball.setX(Math.round(wallLeft.getX() + wallLeft.getWidth()
                        + ball.getCollisionOffsetX()));
                ball.bounceX();
            }
            if(bBounds.x + bBounds.width > wallRight.getX()) {

                ball.setX(Math.round(wallRight.getX()
                        - bBounds.width
                        - ball.getCollisionOffsetX()));
                ball.bounceX();
            }
        }
    }

    private void addActorsToStage(){
        stage.addActor(new Background());
        stage.addActor(floor);
        stage.addActor(roof);
        stage.addActor(wallLeft);
        stage.addActor(wallRight);

        for(Ball ball : balls){
            stage.addActor(ball);
        }
        stage.addActor(player);
    }

    @Override
    public void dispose () {
        stage.dispose();
        shaper.dispose();
    }

    public void movePlayer(){
        boolean[] moveInput = inputHandler.getMovementInput();

        player.movingRight(moveInput[InputHandler.MOVE_RIGHT]);
        player.movingLeft(moveInput[InputHandler.MOVE_LEFT]);

        if(moveInput[InputHandler.MOVE_UP]){
            if(shootTimer > player.getShootTime() && shoots.size < player.getMaxShoots()){
                shoot();
                shootTimer = 0;
            }

        }
    }


}