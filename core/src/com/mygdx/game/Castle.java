package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

/**
 * Konstruuje obiekt typu zamek i wszelkich mechanik z nim związanych
 */
public class Castle extends StaticBody{
    private Batch batch;
    private Texture castle;
    private  Polygon castle_poly;
    private ShapeRenderer render_shape_local;
    Castle(int x, int y, int width, int height, Batch batch){
        this.x =x;
        this.y =y;
        this.width =width;
        this.height = height;
        this.batch = batch;
        this.castle = new Texture(Gdx.files.internal("zamek.png"));
        this.sprite = new Sprite(this.castle,0,0,this.width,this.height);
        this.sprite.setPosition(this.x,this.y);
        castle_poly = new Polygon(new float[] { 0, 0, sprite.getWidth(), 0, sprite.getWidth(),
                sprite.getHeight(), 0, sprite.getHeight()});
        castle_poly.setPosition(this.x,this.y);
        render_shape_local = new ShapeRenderer();
    }
    @Override
    public void Update(){

    }
    @Override
    public void Draw(){
        batch.begin();
        sprite.draw(batch);
        batch.end();
        render_shape_local.begin(ShapeRenderer.ShapeType.Line);
        render_shape_local.polygon(castle_poly.getTransformedVertices());
        render_shape_local.setColor(Color.RED);
        render_shape_local.end();
    }
    public Polygon GetShape(){
        return castle_poly;
    }
}
