package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

public class PlayMapState implements State {
    private GameStateManager StateManager_Ref;

    private Texture terrain_texture;

    private Sprite terrain;
    private SpriteBatch batch;
    private  ShapeRenderer shapeRenderer;

    private  float[] vertices_1;
    private  float[] vertices_2;
    private  float[] vertices_3;
    private  Polygon shape_pol1;
    private  Polygon shape_pol2;
    private  Polygon shape_pol3;

    private  Texture quest_background;
    private  Texture quest_title;

    private  OrthographicCamera camera;
    private boolean pause_state =false;
    private Vector3 mousePos = new Vector3();
    private Intersector.MinimumTranslationVector mtv;

    private Enemy_Map enemy1;
    private Enemy_Map enemy2;
    private Texture enemy1_Texture;
    private Texture enemy2_Texture;


    private Texture points_1;
    private Texture points_2;
    private Texture points_3;

    int counter_victories;

    boolean quest_check =false;

    PlayerMap player;

    Castle castle;

    Music menu_music;

    PlayMapState(GameStateManager statemanager){
        batch = new SpriteBatch();
        //localinput = new InputAdapter();
        this.StateManager_Ref=statemanager;
        this.terrain_texture=  new Texture(Gdx.files.internal("Map.png"));
        player = new PlayerMap(0,0,50,50,batch);
        terrain = new Sprite(this.terrain_texture, 0,0, this.terrain_texture.getWidth(),this.terrain_texture.getHeight());
        //point1.set(300,300);
        //point2.set(500,500);
       // camera= new OrthographicCamera(300, 480); //FILL THE VALUES HERE
        shapeRenderer = new ShapeRenderer();
        //shape = new PolygonShape();
        //shape.set(vertices);
        vertices_1= new float[] {530,337,562,303,
        516,166,516,77,440,10,401,10,482,140,484,260};
        mtv = new Intersector.MinimumTranslationVector();
        shape_pol1 = new Polygon(vertices_1);
        shape_pol1.setOrigin(0,5);
        vertices_2= new float[]{
                580,371,
                609,340,
                743,371,
                802,473,
                923,615,
                900,640,
                782,515,
                726,400};
        vertices_3= new float[]{933,683,957,671,1004,791,975,810};
        shape_pol2 = new Polygon(vertices_2);
        shape_pol2.setOrigin(0,5);
        shape_pol3 = new Polygon(vertices_3);
        shape_pol3.setOrigin(0,5);

        castle = new Castle(336,491,160,160,batch);
        enemy1_Texture =new Texture(Gdx.files.internal("enemy1.png"));
        enemy2_Texture = new Texture(Gdx.files.internal("enemy2.png"));

        quest_background = new Texture(Gdx.files.internal("quest_background.png"));

        quest_title = new Texture(Gdx.files.internal("quest.png"));

        enemy1 = new Enemy_Map(enemy1_Texture,700,400,50,50,batch,800,0,1200,400);
        enemy2 = new Enemy_Map(enemy2_Texture,400,600,50,50,batch,100,600,400,900);

        points_1 = new Texture(Gdx.files.internal("02.png"));
        points_2 = new Texture(Gdx.files.internal("12.png"));
        points_3 = new Texture(Gdx.files.internal("22.png"));

        counter_victories =0;

        menu_music = Gdx.audio.newMusic(Gdx.files.internal("map.wav"));
    }
    @Override
    public void Input(){
        //localinput.touchDown();
        player.Input();
        mousePos.set(Gdx.input.getX(), Math.abs(Gdx.input.getY()-960), 0);
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause_state = true;
        }
        //System.out.println(mousePos);
        //System.out.println(pause_state);
    }
    @Override
    public void Update() {
        menu_music.setVolume(StateManager_Ref.sound_volume/100);
        menu_music.isLooping();
        menu_music.play();
        //menu_music.setVolume(StateManager_Ref.sound_volume/100);
        //System.out.println(mousePos);
        //camera.update();
        //float distance =0;
        //distance = Math.sqrt((Math.pow((point2.x-point1.x),2))
        castle.Update();
        if(enemy1!=null) {
            enemy1.Update();
        }
        if(enemy2!=null) {
            enemy2.Update();
        }
        player.Update();
        if (Intersector.overlapConvexPolygons(player.GetPlayerShape(),shape_pol1,mtv)){
            player.ApplyCollision(mtv.normal.x,mtv.normal.y, mtv.depth);
        }
        if (Intersector.overlapConvexPolygons(player.GetPlayerShape(),shape_pol2,mtv)) {
            player.ApplyCollision(mtv.normal.x,mtv.normal.y, mtv.depth);
        }
        if (Intersector.overlapConvexPolygons(player.GetPlayerShape(),shape_pol3,mtv)) {
            player.ApplyCollision(mtv.normal.x,mtv.normal.y, mtv.depth);
        }
        if (Intersector.overlapConvexPolygons(player.GetPlayerShape(),castle.GetShape(),mtv)) {
            if(quest_check==true&&counter_victories>=2){
                menu_music.stop();
                StateManager_Ref.PushState(new WonGameState(StateManager_Ref));
            }
            player.ApplyCollision(mtv.normal.x,mtv.normal.y, mtv.depth);
            quest_check=true;
        }else{
            quest_check=false;
        }
        if(enemy2!=null) {
            if (Intersector.overlapConvexPolygons(player.GetPlayerShape(), enemy2.GetShape(), mtv)) {
                menu_music.stop();
                System.out.println("ENEMY2");
                player.ApplyCollision(mtv.normal.x, mtv.normal.y, mtv.depth);
                StateManager_Ref.PushState(new BattleState_1(StateManager_Ref,2));
                enemy2 = null;
                counter_victories++;
            }
        }
        if(enemy1!=null) {
            if (Intersector.overlapConvexPolygons(player.GetPlayerShape(), enemy1.GetShape(), mtv)) {
                menu_music.stop();
                System.out.println("ENEMY1");
                player.ApplyCollision(mtv.normal.x, mtv.normal.y, mtv.depth);
                StateManager_Ref.PushState(new BattleState_1(StateManager_Ref,1));
                enemy1 = null;
                counter_victories++;
            }
        }
        if(pause_state==true){
            menu_music.stop();
            StateManager_Ref.PushState(new PlayPauseState(StateManager_Ref));

            //StateManager_Ref.PushState(new LostBattleState(this.StateManager_Ref));
            //StateManager_Ref.PushState(new WonBattleState(StateManager_Ref));
            //StateManager_Ref.PushState(new WonGameState(StateManager_Ref));
            //StateManager_Ref.PushState(new LostGameState(StateManager_Ref));
            pause_state=false;
        }
    }
    @Override
    public void Draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        //batch.setProjectionMatrix(camera.combined);
        batch.begin();
        terrain.draw(batch);
        batch.end();
        castle.Draw();
        if(enemy1!=null) {
            enemy1.Draw();
        }
        if(enemy2!=null) {
            enemy2.Draw();
        }
        player.Draw();
        batch.begin();
        if(quest_check==true) {
            batch.draw(quest_background, 1280/2-quest_background.getWidth()/2, 100);
            batch.draw(quest_title, 1280/2-quest_background.getWidth()/2+quest_title.getWidth()-40, quest_background.getHeight()-(quest_title.getHeight()/2));
            if(counter_victories==0){
                batch.draw(points_1, 1280/2-points_1.getWidth()/2, 175);
            }else if(counter_victories==1){
                batch.draw(points_2, 1280/2-points_2.getWidth()/2, 175);
            }else{
                batch.draw(points_3, 1280/2-points_3.getWidth()/2, 175);
            }
        }
        batch.end();
    }
}
