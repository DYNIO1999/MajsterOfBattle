package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayPauseState  implements State{

    private SpriteBatch batch;
    private Texture cont_texture_0;
    private Texture cont_texture_1;
    private Texture cont_texture_2;
    private Texture exit_texture_0;
    private Texture exit_texture_1;
    private Texture exit_texture_2;
    private Texture title;
    private Texture pause_background;
    private Texture pause;
    private Button cont;
    private Button exit;

    private GameStateManager StateManager_Ref;

    PlayPauseState(GameStateManager statemanager){
        this.StateManager_Ref =statemanager;
        batch = new SpriteBatch();
        pause_background = new Texture(Gdx.files.internal("MenuBackground.png"));
        cont_texture_0= new Texture(Gdx.files.internal("PausePlay_1.png"));
        cont_texture_1= new Texture(Gdx.files.internal("PausePlay_0.png"));
        cont_texture_2= new Texture(Gdx.files.internal("PausePlay_hover.png"));

        exit_texture_0 = new Texture(Gdx.files.internal("PauseExit_1.png"));
        exit_texture_1 = new Texture(Gdx.files.internal("PauseExit_0.png"));
        exit_texture_2 = new Texture(Gdx.files.internal("PauseExit_hover.png"));

        title = new Texture(Gdx.files.internal("pause_title.png"));

        pause = new Texture(Gdx.files.internal("pause.png"));
        cont = new Button(((1280/2)-(cont_texture_0.getWidth()/2)),
                ((400)-(cont_texture_0.getHeight()/2)), cont_texture_0.getWidth(), cont_texture_0.getHeight(),cont_texture_0,cont_texture_1,cont_texture_2,batch);
        exit= new Button(((1280/2)-(exit_texture_0.getWidth()/2)),
                ((950/4)-(exit_texture_0.getHeight()/2)), exit_texture_0.getWidth(), exit_texture_0.getHeight(), exit_texture_0,exit_texture_1,exit_texture_2,batch);

    }
    public void Input(){
        cont.Input();
        exit.Input();
    }
    public void Update(){
        boolean button1_status = cont.Update();
        boolean button2_status = exit.Update();
        if(button1_status==true){
            StateManager_Ref.PopState();
        }
        if(button2_status==true){
            StateManager_Ref.PushState(new MainMenu(StateManager_Ref));
        }
    }
    public void Draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(pause_background, 0, 0);
        batch.draw(title, ((1280/2)-250), ((400)-(cont_texture_0.getHeight()/2)));
        batch.end();
        cont.Draw();
        exit.Draw();
    }

}
