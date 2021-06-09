package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LostBattleState implements State{
    private Texture background_texture;
    private Texture title_texture;

    private Texture play_0;
    private Texture play_1;
    private Texture play_2;

    private Texture exit_0;
    private Texture exit_1;
    private Texture exit_2;

    private Button play;
    private Button exit;

    private GameStateManager StateManager_Ref;

    private SpriteBatch batch;

    LostBattleState(GameStateManager statemanager){
        this.batch = new SpriteBatch();
        this.StateManager_Ref = statemanager;

        background_texture = new Texture(Gdx.files.internal("MenuBackground.png"));
        play_0= new Texture(Gdx.files.internal("MenuPlay_1.png"));
        play_1 = new Texture(Gdx.files.internal("MenuPlay_0.png"));
        play_2= new Texture(Gdx.files.internal("MenuPlay_hover.png"));


        exit_0 = new Texture(Gdx.files.internal("MenuExit_1.png"));
        exit_1 = new Texture(Gdx.files.internal("MenuExit_0.png"));
        exit_2 = new Texture(Gdx.files.internal("MenuExit_hover.png"));

        title_texture = new Texture(Gdx.files.internal("defeat.png"));

        play = new Button(((1280/2)-(play_0.getWidth()/2)),
                ((500)-(play_0.getHeight()/2)), play_0.getWidth(), play_0.getHeight(), play_0,play_1,play_2,batch);
        exit= new Button(((1280/2)-(exit_0.getWidth()/2)),
                ((350)-(exit_0.getHeight()/2)), exit_0.getWidth(), exit_0.getHeight(), exit_0,exit_1,exit_2,batch);


    }
    @Override
    public void Input(){
        boolean button1_status = play.Update();
        boolean button3_status = exit.Update();

        if(button1_status==true){
            StateManager_Ref.PopState();
        }
        if(button3_status==true){
            StateManager_Ref.PopState();
            Gdx.app.exit();
        }
    }
    @Override
    public void Update(){
    }
    @Override
    public void Draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        batch.draw(background_texture, 0, 0);
        batch.draw(title_texture, ((1280/2)-(title_texture.getWidth()/2)), 850-title_texture.getHeight());
        batch.end();
        play.Draw();
        exit.Draw();
    }
}
