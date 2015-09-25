package com.jashlaviu.multiblax;


import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jashlaviu.multiblax.actors.Background;
import com.jashlaviu.multiblax.actors.Ball;
import com.jashlaviu.multiblax.actors.Player;
import com.jashlaviu.multiblax.actors.ShootLong;
import com.jashlaviu.multiblax.actors.Wall;

public class MultiblaxScreen extends ScreenAdapter{

    private SpriteBatch batch;
    private Stage stage;
    private Player player;
    private Ball ball, ball2, ball3, ball4;

    private InputHandler inputHandler;
    private OrthographicCamera camera;

    private Array<Ball> balls;
    private Array<ShootLong> shoots;

    private float shootTimer = 0;
    private int lives;

    public ShapeRenderer shaper = new ShapeRenderer();
    
	private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    
    private Array<Rectangle> wallRects;    
    
    public MultiblaxScreen(MultiblaxGame game){
        batch = game.getBatch();
        camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(800, 600, camera), batch);

        lives = 5;
        resetLevel();
        
        map = new TmxMapLoader().load("map_0.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        
        wallRects = new Array<Rectangle>();
        
        MapLayer wallLayer = map.getLayers().get("WallObjects");
        if(wallLayer != null){
        	MapObjects wallObjects = wallLayer.getObjects();
        	for (MapObject wallo : wallObjects){
        		Rectangle rec = ((RectangleMapObject)wallo).getRectangle();
        		wallRects.add(rec);        		
        	}
        }
        
        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateBalls(delta);
        updatePlayer(delta);
        
        renderer.setView((OrthographicCamera) stage.getCamera());
        renderer.render();
        //Now render objects in your stage on top.
        //stage.render();
        
        stage.draw();
        updateShoots(delta);
        stage.act(delta);
        
        // Draw hearts
        stage.getBatch().begin();
        for(int i = 0; i < lives; i++){
        	stage.getBatch().draw(TextureLoader.hearth, 50 + i*50, 550);
        }
        stage.getBatch().end();
        
        shaper.begin(ShapeType.Line);
        shaper.setColor(0, 0, 0, 0);        
        for(Rectangle rec : wallRects){
        	shaper.rect(rec.x, rec.y, rec.width, rec.height);
        }
        
        for(Ball ball : balls){
        	Rectangle rec = ball.getCollisionBounds();
        	shaper.rect(rec.x, rec.y, rec.width, rec.height);
        }        
        shaper.end();
        
        movePlayer();
    }
    
    
    public void resetLevel(){
    	stage = new Stage(new FitViewport(800, 600, camera), batch);
    	
        floor = new Wall(0, 0, Wall.FLOOR);
        roof = new Wall(0, 520, Wall.ROOF);
        wallLeft = new Wall(-40, 80, Wall.SIDE_LEFT);
        wallRight = new Wall(760, 80, Wall.SIDE_RIGHT);
        
        int startVel = 110;
        
        player = new Player(200f, 100);
        ball = new Ball(300, 400, Ball.SIZE_3);
        ball.setVelocityX(startVel);

        ball2 = new Ball(400, 300, Ball.SIZE_3);
        ball2.setVelocityX(-startVel);
        
        ball3 = new Ball(500, 200, Ball.SIZE_3);
        ball3.setVelocityX(startVel);
        
        ball4 = new Ball(200, 200, Ball.SIZE_3);
        ball4.setVelocityX(startVel);

        balls = new Array<Ball>();
        shoots = new Array<ShootLong>();

        balls.add(ball);
        balls.add(ball2);
        balls.add(ball3);
        //balls.add(ball4);
        
        addActorsToStage();
    }
    
    public void divideBall(Ball ball, Rectangle shootBounds){
    	
    	ball.remove();
    	// Only divide if not the smallest one
    	if(ball.getSize() > 1){
    		float newVelX = 110f; //150
    		float bounceFactor = 0.75f;
    		
    		// Create smaller ball bouncing left
	    	Ball newBall1 = new Ball(shootBounds.x - ball.getWidth() - 1,
	    			ball.getY(), ball.getSize()-1);
	    	newBall1.setVelocityX(-newVelX);
	    	newBall1.bounceY(bounceFactor);
	    	
	    	// Create smaller ball bouncing right
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
    	// Create a new shoot in the player position
        ShootLong shoot = new ShootLong(player.getX() + player.getWidth()/2
        		-TextureLoader.shootLongNormal.getRegionWidth()/2
        		, player.getY());
        shoots.add(shoot);
        stage.addActor(shoot);
    }

    private void updatePlayer(float delta){
        player.updateY(delta);
        Rectangle pBounds = player.getCollisionBounds();
        
        // Handle floor collision
        for(Rectangle wallBound : wallRects){
        	if(pBounds.overlaps(wallBound)){
        		if(player.getVelocityY() < 0){
        			player.setY(Math.round(wallBound.getY() + wallBound.getHeight() - player.getCollisionOffsetY()));
                    player.setVelocityY(0);
        		}
        	}
        }
        
        player.updateX(delta);
        pBounds = player.getCollisionBounds();
        
        for(Rectangle wallBound : wallRects){
        	if(pBounds.overlaps(wallBound)){
        		// Player moving left
        		if(player.getVelocityX() < 0){
        			player.setX(Math.round(wallBound.getX() + wallBound.getWidth() + player.getCollisionOffsetX()));
                    player.setVelocityX(0);
        		}
        		
        		// Player moving right
        		if(player.getVelocityX() > 0){
        			player.setX(Math.round(wallBound.getX() - player.getWidth() + player.getCollisionOffsetX()));
                    player.setVelocityX(0);
        		}
        	}
        }

    }

    private void updateShoots(float delta){
        shootTimer += delta;

        Iterator<ShootLong> shootIterator = shoots.iterator();
        while(shootIterator.hasNext()){

            ShootLong shoot = shootIterator.next();
            shoot.updateY(delta);
            Rectangle shootBounds = shoot.getCollisionBounds();
            
            // Destroy shoot if collides with a wall
            for(Rectangle wallBound : wallRects){
            	if(shootBounds.overlaps(wallBound)){
                    shoot.remove(); //Remove from stage
                    shootIterator.remove();  //Remove from Array
                    continue;
            	}
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
            
            // Destroy shoot if ball was hit
            if(ballDestroyed){
        		shoot.remove();
        		shootIterator.remove();
            }
        }

    }

    private void updateBalls(float delta){
        for(Ball ball : balls){

            ball.updateX(delta);
            Rectangle bBounds = ball.getCollisionBounds();
            
            for(Rectangle wallBound : wallRects){
            	if(bBounds.overlaps(wallBound)){
            		// (collides left)
            		if(ball.getVelocityX() < 0){
                        ball.setX(Math.round(wallBound.getX() + wallBound.getWidth()));
                        ball.bounceX();
            		}
            		// (collides right)
            		else if(ball.getVelocityX() > 0){
            			ball.setX(Math.round(wallBound.getX() - bBounds.getWidth()));
                        ball.bounceX();
            		}
            	}
            }

            ball.updateY(delta);
            bBounds = ball.getCollisionBounds();
            
            
            for(Rectangle wallBound : wallRects){
            	if(bBounds.overlaps(wallBound)){
            		// Is falling (collides floor)
            		if(ball.getVelocityY() < 0){
                        ball.setY(Math.round(wallBound.getY() + wallBound.getHeight()));
                        ball.bounceY();
            		}
            		
            		// Is going up (collides roof)
            		else if(ball.getVelocityY() > 0){
                        
            		}
            	}
            }
            
            if(ball.collides(player)){
            	lives--;
            	resetLevel();
            	break;
            }
        }
    }

    private void addActorsToStage(){
        for(Ball ball : balls){
            stage.addActor(ball);
        }
        stage.addActor(player);
    }

    @Override
    public void dispose () {
        stage.dispose();
        shaper.dispose();
        map.dispose();
        renderer.dispose();
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