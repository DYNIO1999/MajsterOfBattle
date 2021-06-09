package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Klasa, która implementuje wygrany stan gry
 */
public class WonGameState implements State{
    private Texture background_texture;
    private Texture title_texture;

    private Texture exit_0;
    private Texture exit_1;
    private Texture exit_2;

    private Button exit;

    private GameStateManager StateManager_Ref;

    private SpriteBatch batch;

    Music music;
    WonGameState(GameStateManager statemanager){
        music = Gdx.audio.newMusic(Gdx.files.internal("win.wav"));
        this.batch = new SpriteBatch();
        this.StateManager_Ref = statemanager;

        background_texture = new Texture(Gdx.files.internal("MenuBackground.png"));

        exit_0 = new Texture(Gdx.files.internal("MenuExit_1.png"));
        exit_1 = new Texture(Gdx.files.internal("MenuExit_0.png"));
        exit_2 = new Texture(Gdx.files.internal("MenuExit_hover.png"));

        title_texture = new Texture(Gdx.files.internal("won_game.png"));
        exit= new Button(((1280/2)-(exit_0.getWidth()/2)),
                ((400)-(exit_0.getHeight()/2)), exit_0.getWidth(), exit_0.getHeight(), exit_0,exit_1,exit_2,batch);


    }
    @Override
    public void Input(){
        boolean button3_status = exit.Update();
        if(button3_status==true){
            music.stop();
            StateManager_Ref.PopState();
            Gdx.app.exit();
        }
    }
    @Override
    public void Update(){
        music.setVolume(StateManager_Ref.sound_volume/100);
        music.isLooping();
        music.play();
    }
    @Override
    public void Draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        batch.draw(background_texture, 0, 0);
        batch.draw(title_texture, ((1280/2)-(title_texture.getWidth()/2)), 800-title_texture.getHeight());
        batch.end();
        exit.Draw();
    }
}
