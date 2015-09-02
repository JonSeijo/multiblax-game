package com.jashlaviu.multiblax;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private Array<Shoot> shoots;

    private float shootTimer = 0;

    public ShapeRenderer shaper = new ShapeRenderer();

    public MultiblaxScreen(MultiblaxGame game){
        batch = game.getBatch();
        camera = new OrthographicCamera();
        stage = new Stage(new FitViewport(800, 600, camera), batch);

        player = new Player(100f, 390f);
        ball = new Ball(300, 400);
        ball.setVelocityX(200);

        ball2 = new Ball(400, 300);
        ball2.setVelocityX(-200);

        balls = new Array<Ball>();
        shoots = new Array<Shoot>();

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

        movePlayer();
    }

    public void shoot(){
        Shoot shoot = new Shoot(player.getX(), player.getY());
        shoots.add(shoot);
        stage.addActor(shoot);
    }

    private void updatePlayer(float delta){
        player.updateY(delta);
        if(player.getY() < floor.getTop()){
            player.setY(Math.round(floor.getTop() - player.getCollisionOffsetY()));
            player.setVelocityY(0);
        }

        player.updateX(delta);
        if(player.getX() < wallLeft.getRight()){
            player.setX(Math.round(wallLeft.getRight() + player.getCollisionOffsetX()));
            player.setVelocityX(0);
        }

        if(player.getCollisionBounds().x + player.getCollisionBounds().width > wallRight.getX()){
            player.setX(Math.round(wallRight.getX() - player.getCollisionBounds().width - player.getCollisionOffsetX()));
            player.setVelocityX(0);
        }
    }

    private void updateShoots(float delta){
        shootTimer += delta;

        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(0,0,0,1);
        //shaper.setAutoShapeType(ShapeRenderer.ShapeType.Line);

        Iterator<Shoot> shootIterator = shoots.iterator();
        while(shootIterator.hasNext()){

            Shoot shoot = shootIterator.next();
            shoot.updateY(delta);
            Rectangle shootBounds = shoot.getCollisionBounds();

            shaper.rect(shootBounds.x, shootBounds.y, shootBounds.width, shootBounds.height);

            if(shoot.getCollisionBounds().getY() + shoot.getCollisionBounds().getHeight() > roof.getY()){
                shoot.remove(); //Remove from stage
                shootIterator.remove();  //Remove from Array
            }
        }

        shaper.end();
    }

    private void updateBalls(float delta){
        for(Ball ball : balls){
            ball.updateY(delta);
            if(ball.getCollisionBounds().getY() < floor.getTop()){
                ball.setY(Math.round(floor.getTop()));
                ball.bounceY();
            }

            ball.updateX(delta);
            if(ball.getCollisionBounds().getX() < wallLeft.getRight()){
                ball.setX(Math.round(wallLeft.getX() + wallLeft.getWidth()
                        + ball.getCollisionOffsetX()));
                ball.bounceX();
            }
            if(ball.getCollisionBounds().getX()
                    + ball.getCollisionBounds().getWidth() > wallRight.getX()) {

                ball.setX(Math.round(wallRight.getX()
                        - ball.getCollisionBounds().width
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