package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Konstruuje i implementuje mechaniki zwiazane z przyciskiem
 */
public class Button {
    public int buttonstatus;
    private int x;
    private int y;
    private int width;
    private int height;
    private Texture texture_1;
    private Texture texture_2;
    private Texture texture_3;
    private SpriteBatch batch;
    private Sprite sprite;
    private Vector3 mousePos = new Vector3();

    Button(int x, int y, int width, int height, Texture texture_1,Texture texture_2,Texture texture_3, SpriteBatch batch){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.texture_1 =texture_1;
        this.texture_2 =texture_2;
        this.texture_3 =texture_3;
        this.batch =batch;
        sprite = new Sprite(this.texture_1, 0,0,this.width,this.height);
        sprite.setPosition(this.x, this.y);
        buttonstatus =0;
        //
    }

    /**
     * @return
     * Konstruuje i implementuje mechaniki zwiazane z przyciskiem
     */
    public boolean Input(){
        //System.out.println("Y"+this.y);
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        //System.out.println(mousePos);
        if((this.mousePos.x>this.x)&&(960-this.mousePos.y>this.y)&&(this.mousePos.x<this.x+width)&&(960-this.mousePos.y<this.y+height)) {
            buttonstatus =1;
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                return  true;
            }else{
                return  false;
            }
        }else{
            return  false;
        }
    }
    public boolean Update(){
        boolean check=false;
        boolean temp =Input();
        if(buttonstatus==1){
            sprite.setTexture(texture_3);
        }else if(buttonstatus ==0){
            sprite.setTexture(texture_2);
        }
        if(temp==true) {
            if (buttonstatus == 1) {
                buttonstatus = 2;
                check = true;
            }
        }else {
            buttonstatus = 0;
        }
        if(buttonstatus==2){
            sprite.setTexture(texture_1);
        }
        return  check;
    }
    public void Draw(){

        batch.begin();
        sprite.draw(batch);
        batch.end();

    }
}
