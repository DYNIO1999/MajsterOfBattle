package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Klasa implementujaca stan glownej gry
 */
public class MainMenu implements State {
    public ShapeRenderer shape;
    public OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture title_menu;

    private Texture texture;
    private Texture play_0;
    private Texture play_1;
    private Texture play_2;
    private Texture options_0;
    private Texture options_1;
    private Texture options_2;
    private Texture exit_0;
    private Texture exit_1;
    private Texture exit_2;

    private Button play;
    private Button options;
    private Button exit;
    private GameStateManager StateManager_Ref;

    Music menu_music;

    MainMenu(GameStateManager statemanager){
        this.StateManager_Ref =statemanager;
        shape = new ShapeRenderer();
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("MenuBackground.png"));
        play_0= new Texture(Gdx.files.internal("MenuPlay_1.png"));
        play_1 = new Texture(Gdx.files.internal("MenuPlay_0.png"));
        play_2= new Texture(Gdx.files.internal("MenuPlay_hover.png"));
        options_0= new Texture(Gdx.files.internal("MenuOptions_1.png"));
        options_1= new Texture(Gdx.files.internal("MenuOptions_0.png"));
        options_2= new Texture(Gdx.files.internal("MenuOptions_hover.png"));
        exit_0 = new Texture(Gdx.files.internal("MenuExit_1.png"));
        exit_1 = new Texture(Gdx.files.internal("MenuExit_0.png"));
        exit_2 = new Texture(Gdx.files.internal("MenuExit_hover.png"));

        title_menu = new Texture(Gdx.files.internal("MenuTitle.png"));

        play = new Button(((1280/2)-(play_0.getWidth()/2)),
        ((550)-(play_0.getHeight()/2)), play_0.getWidth(), play_0.getHeight(), play_0,play_1,play_2,batch);
        options = new Button(((1280/2)-(options_0.getWidth()/2)),
                ((425)-(options_0.getHeight()/2)), options_0.getWidth(), options_0.getHeight(), options_0,options_1,options_2,batch);
        exit= new Button(((1280/2)-(exit_0.getWidth()/2)),
                ((300)-(exit_0.getHeight()/2)), exit_0.getWidth(), exit_0.getHeight(), exit_0,exit_1,exit_2,batch);

        menu_music = Gdx.audio.newMusic(Gdx.files.internal("menu.wav"));
        System.out.println(StateManager_Ref.sound_volume/100);
        menu_music.setVolume(StateManager_Ref.sound_volume/100);
    }
    @Override
    public void Input(){
        //System.out.println("Input");
        play.Input();


    }
    @Override
    public void Update() {
        System.out.println(StateManager_Ref.sound_volume/100);
        menu_music.setVolume(StateManager_Ref.sound_volume/100);
        menu_music.isLooping();
        menu_music.play();
        //System.out.println("I am updating!");
        boolean button1_status = play.Update();
        boolean button2_status = options.Update();
        boolean button3_status = exit.Update();
        if(button1_status==true){
            menu_music.stop();
         StateManager_Ref.PushState(new PlayMapState(StateManager_Ref));
        }
        if(button2_status==true){
            menu_music.stop();
            StateManager_Ref.PushState(new OptionsMenu(StateManager_Ref));
        }
        if(button3_status==true){
            menu_music.stop();
            StateManager_Ref.PopState();
            Gdx.app.exit();
        }

    }
    @Override
    public void Draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.draw(title_menu, ((1280/2)-(title_menu.getWidth()/2)), 850-title_menu.getHeight());
        batch.end();
        play.Draw();
        options.Draw();
        exit.Draw();
        /*shape.setProjectionMatrix(this.camera.combined);
        System.out.println("I am drawing!");
        Gdx.gl.glClearColor(255, 188, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        */
        /*this.shape.begin();
        this.shape.setColor(Color.RED);
        this.shape.rect(100, 100, 100, 100);
        this.shape.end();*/
    }
}
