package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Klasa implementująca postać gracza na mapie gry
 */
public class PlayerMap extends DynamicBody{

    private Vector3 mousePos = new Vector3();
    private  double startX, startY, endX, endY;
    private  double speed = 100;
    private  double elapsed = 0.02f;
    private  double directionY;
    private  double directionX;
    private  double distance;
    private boolean moving = false;

    private  Polygon player_poly;
    private  float[] player_vertices;


    private Texture player_text;
    private Batch batch;


    ShapeRenderer shape;


    private float stateTime;

    PlayerMap(){

    }
    PlayerMap(int x, int y, int width, int height, Batch batch){
        this.batch=batch;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        player_text = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(player_text,0,0,50,50);
        sprite.setSize(width,height);
        sprite.setPosition(0,0);
        shape = new ShapeRenderer();

        player_poly = new Polygon(new float[] { 0, 0, sprite.getWidth()-25, 0, sprite.getWidth()-25,
                sprite.getHeight()-25, 0, sprite.getHeight()-25});

    }
    @Override
    void Input(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            mousePos.set(Gdx.input.getX(), Math.abs(Gdx.input.getY()-960), 0);
            startX=sprite.getX();
            startY=sprite.getY();
            endX= mousePos.x-(sprite.getWidth()/2);
            endY= mousePos.y-(sprite.getHeight()/2);
            distance = Math.sqrt(Math.pow(endX-startX,2)+Math.pow(endY-startY,2));
            directionX = (endX-startX) / distance;
            directionY = (endY-startY) / distance;
            moving = true;
        }
    }
    @Override
    void Update(){
        if(moving == true)
        {
            //System.out.println("TEST");
            sprite.setX((float)(sprite.getX()+directionX * speed * elapsed));
            sprite.setY((float)(sprite.getY()+directionY * speed * elapsed));
            if(Math.sqrt(Math.pow(sprite.getX()-startX,2)+Math.pow(sprite.getY()-startY,2)) >= distance)
            {
                sprite.setX((float)endX);
                sprite.setY((float)endY);
                moving = false;
            }
        }
        player_poly.setPosition(sprite.getX() +25/2,sprite.getY()+25/2);
    }
    @Override
    void Draw(){
       //System.out.println(stateTime);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(sprite.getX()+25/2, sprite.getY()+25/2,sprite.getWidth()-25,sprite.getHeight()-25);
        shape.end();
    }
    public Polygon GetPlayerShape(){
        return player_poly;
    }
    public void ApplyCollision(float dX, float dY, float distance){
        sprite.setX((float)(sprite.getX()+dX * distance *speed * elapsed));
        sprite.setY((float)(sprite.getY()+dY * distance *speed * elapsed));
    }

}
