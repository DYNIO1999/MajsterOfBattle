package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Klasa, ktora implementuje jednostke gracza na mapie bitwy
 */
public class PlayerBattle {

    public int current_row;
    public int current_col;

    public int x;
    public int y;
    public int width;
    public int height;
    public int index;


    public Sprite sprite;

    private Batch batch;

    Texture player_texture;
    boolean moved;
    PlayerBattle(int x, int y, int width, int height, int index, Texture texture, Batch batch, boolean moved){
        this.batch= batch;
        this.x=x;
        this.y=y;
        this.width =width;
        this.height =height;
        this.index = index;
        this.player_texture =texture;
        sprite = new Sprite(this.player_texture,0,0,width,height);
        sprite.setPosition(x,y);
        this.moved = moved;
    }
    public void Update(){
        sprite.setPosition(x,y);
    }
    public void Draw() {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    public void SetCurrentRowColumn(int current_row, int current_col){
        this.current_row =current_row;
        this.current_col=current_col;
    }
    public void SetPosition(int x, int y){
        this.x =x;
        this.y =y;
        sprite.setPosition(this.x,this.y);

    }
}
