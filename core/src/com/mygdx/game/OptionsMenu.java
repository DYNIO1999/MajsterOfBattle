package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Timer;

/**
 * Klasa, ktora implementuje stan opcji gry
 */
public class OptionsMenu implements State{
    private SpriteBatch batch;
    private Texture reject_options_texture;
    private Texture approve_options_texture;
    private Texture bar_texture;
    private Texture slider_texture;
    private Texture exit_texture_0;
    private Texture exit_texture_1;
    private Texture exit_texture_2;

    private Texture options_background;
    private Button reject;
    private Button approve;
    private Button exit;

    private Sprite bar_sprite;
    private Sprite slider_sprite;

    private GameStateManager StateManager_Ref;

    private Vector3 mousePos = new Vector3();

    OptionsMenu(GameStateManager statemanager){
        this.StateManager_Ref =statemanager;
        batch = new SpriteBatch();
        options_background = new Texture(Gdx.files.internal("MenuBackground.png"));
        approve_options_texture = new Texture(Gdx.files.internal("approve.png"));
        reject_options_texture = new Texture(Gdx.files.internal("reject.png"));
        exit_texture_0 = new Texture(Gdx.files.internal("MenuExit_1.png"));
        exit_texture_1 = new Texture(Gdx.files.internal("MenuExit_0.png"));
        exit_texture_2 = new Texture(Gdx.files.internal("MenuExit_hover.png"));
        bar_texture = new Texture(Gdx.files.internal("options_bar.png"));
        slider_texture = new Texture(Gdx.files.internal("slider.png"));



        exit= new Button(((1280/2)-(exit_texture_0.getWidth()/2)),
                ((300)-(exit_texture_0.getHeight()/2)), exit_texture_0.getWidth(), exit_texture_0.getHeight(), exit_texture_0,exit_texture_1,exit_texture_2,batch);
        approve=new Button(((550)-(exit_texture_0.getWidth()/2)),
                ((500)-(approve_options_texture.getHeight()/2)), approve_options_texture.getWidth(), approve_options_texture.getHeight(), approve_options_texture,approve_options_texture,approve_options_texture,batch);
        reject = new Button(((750)-(reject_options_texture.getWidth()/2)),
                ((500)-(reject_options_texture.getHeight()/2)), reject_options_texture.getWidth(), reject_options_texture.getHeight(), reject_options_texture,reject_options_texture,reject_options_texture,batch);

        bar_sprite = new Sprite(bar_texture, 0,0,bar_texture.getWidth(),bar_texture.getHeight());
        slider_sprite = new Sprite(slider_texture,0,0,slider_texture.getWidth(),slider_texture.getHeight());

        bar_sprite.setPosition(((1280/2)-(bar_texture.getWidth()/2)),600);
        slider_sprite.setPosition(((1280/2)-(slider_texture.getWidth()/2)),600);


    }
    public void Input(){



        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

        if((this.mousePos.x>slider_sprite.getX())&&(960-this.mousePos.y>this.slider_sprite.getY())&&(this.mousePos.x<slider_sprite.getX()+
                slider_sprite.getWidth())&&(960-this.mousePos.y<slider_sprite.getY()+slider_sprite.getHeight())) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if(this.mousePos.x<slider_sprite.getX()+
                        (slider_sprite.getWidth()/2)){
                    if(slider_sprite.getX()>=bar_sprite.getX()) {
                        float test_x = mousePos.x - slider_sprite.getX();

                        slider_sprite.setPosition(slider_sprite.getX() - test_x, 600);
                    }
                }
                if(this.mousePos.x>slider_sprite.getX()+
                        (slider_sprite.getWidth()/2)) {
                    if(slider_sprite.getX()+slider_sprite.getWidth()<=bar_sprite.getX()+bar_sprite.getWidth()) {
                        float test_x = mousePos.x - slider_sprite.getX();
                        slider_sprite.setPosition(slider_sprite.getX() - (slider_sprite.getWidth() / 2) + test_x, 600);
                    }
                }
            }
        }
        approve.Input();
        reject.Input();
        exit.Input();
    }
    public void Update(){
        boolean button0_status= approve.Update();
        boolean button1_status=reject.Update();
        boolean button2_status=exit.Update();
        if(button0_status==true){
            float volume =(((slider_sprite.getX()+slider_sprite.getWidth()/2)-bar_sprite.getX())/441)*100;
            StateManager_Ref.sound_volume = volume;
            if(volume<=5){
                StateManager_Ref.sound_volume =0;
            }
            System.out.println(volume);
        }
        if(button1_status==true){
            slider_sprite.setPosition(((1280/2)-(slider_texture.getWidth()/2)),600);
        }
        if(button2_status==true){
            /*long endPauseTime = 0;
            endPauseTime = System.currentTimeMillis() + (1 * 1000);
            do{
            }while(endPauseTime>System.currentTimeMillis());

             */
            StateManager_Ref.PopState();
        }
    }
    public void Draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(options_background, 0, 0);
        bar_sprite.draw(batch);
        slider_sprite.draw(batch);
        batch.end();
        approve.Draw();
        reject.Draw();
        exit.Draw();
    }

}
