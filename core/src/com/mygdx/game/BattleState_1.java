package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Klasa bitwy.
 */
public class BattleState_1 implements State {
    /**
     * Przechowuje informacje odnoszące się do gridu mapy.
     */
    class Grid{
        int x;
        int y;
        int width;
        int height;
        Polygon grid_body;
        Grid(int x, int y, int width, int height){
            this.x =x;
            this.y =y;
            this.width = width;
            this.height =height;

            grid_body = new Polygon(new float[] { 0, 0, this.width, 0, this.width,
                    this.height, 0, this.height});
            grid_body.setPosition(this.x,this.y);
        }
    }

    private PlayerBattle unit_p_1;
    private PlayerBattle unit_p_2;
    private PlayerBattle unit_p_3;

    Vector<PlayerBattle> player_units = new Vector<PlayerBattle>(0);
    private Texture map_texture;

    private Texture player_1;
    private Texture player_2;
    private Texture player_3;

    private Texture enemy_1;
    private Texture enemy_2;
    private Texture enemy_3;

    private ShapeRenderer shapeRenderer;
    private Batch batch;
    Grid[][] grid_map;

    int row =-1;
    int col =-1;
    int counter =0;
    int second_counter =0;

    boolean player_clicked;
    private Vector3 mousePos = new Vector3();
    private  boolean clicked;



    private boolean player_unit_selected;
    private boolean quit;


    boolean player_turn;
    boolean enemy_turn;
    int num_of_moves;

    int index_temp;

    GameStateManager StateManager_Ref;
    Vector<PlayerBattle> enemy_units = new Vector<PlayerBattle>(0);

    boolean enemy_clicked;
    int p_empty=0;
    int e_empty=0;

    private  int battle_map_index;

    Music menu_music;
    BattleState_1(GameStateManager statemanager, int map){

        this.StateManager_Ref =statemanager;
        this.battle_map_index = map;
        menu_music =Gdx.audio.newMusic(Gdx.files.internal("battle1.wav"));
        if(battle_map_index==1) {
            map_texture = new Texture(Gdx.files.internal("battle_map_1.png"));
        }
        if(battle_map_index ==2) {
            map_texture = new Texture(Gdx.files.internal("battle_map_2.png"));
        }
        player_1 = new Texture(Gdx.files.internal("w4-100.png"));
        player_2 = new Texture(Gdx.files.internal("w3-100.png"));
        player_3 = new Texture(Gdx.files.internal("w2-100.png"));


        enemy_1 =new Texture(Gdx.files.internal("p1-100.png"));
        enemy_2 =new Texture(Gdx.files.internal("p2-100.png"));;
        enemy_3 =new Texture(Gdx.files.internal("p3-100.png"));;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        grid_map = new Grid[5][9];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++) {
                Grid temp = new Grid(200+j*100,250+i*100,100,100);
                grid_map[i][j]=temp;
            }
        }
        clicked =false;
        player_clicked =false;

        unit_p_1 = new PlayerBattle(0,0,100,100,2,player_2,this.batch,false);
        unit_p_1.SetCurrentRowColumn(4,0);
        unit_p_1.SetPosition(grid_map[4][0].x,grid_map[4][0].y);

        unit_p_2 = new PlayerBattle(0,0,100,100,1,player_1,this.batch,false);
        unit_p_2.SetCurrentRowColumn(2,0);
        unit_p_2.SetPosition(grid_map[2][0].x,grid_map[2][0].y);

        unit_p_3 = new PlayerBattle(0,0,100,100,3,player_3,this.batch,false);
        unit_p_3.SetCurrentRowColumn(0,0);
        unit_p_3.SetPosition(grid_map[0][0].x,grid_map[0][0].y);

        player_units.add(unit_p_1);
        player_units.add(unit_p_2);
        player_units.add(unit_p_3);
        player_unit_selected =false;


        PlayerBattle EnemyPlayer_Temp = new PlayerBattle(0,0,100,100,1,enemy_1,this.batch,false);
        EnemyPlayer_Temp.SetCurrentRowColumn(4,8);
        EnemyPlayer_Temp.SetPosition(grid_map[4][8].x,grid_map[4][8].y);
        enemy_units.add(EnemyPlayer_Temp);

        EnemyPlayer_Temp = new PlayerBattle(0,0,100,100,2,enemy_2,this.batch,false);
        EnemyPlayer_Temp.SetCurrentRowColumn(2,8);
        EnemyPlayer_Temp.SetPosition(grid_map[2][8].x,grid_map[2][8].y);
        enemy_units.add(EnemyPlayer_Temp);

        EnemyPlayer_Temp = new PlayerBattle(0,0,100,100,3,enemy_3,this.batch,false);
        EnemyPlayer_Temp.SetCurrentRowColumn(0,8);
        EnemyPlayer_Temp.SetPosition(grid_map[0][8].x,grid_map[0][8].y);
        enemy_units.add(EnemyPlayer_Temp);


        index_temp =-1;
        quit = false;

        enemy_clicked = false;
        player_turn = true;
        enemy_turn = false;
        num_of_moves = 0;
        p_empty =0;
        e_empty =0;
        menu_music.setVolume(StateManager_Ref.sound_volume/100);
    }

    /**
     * Metoda zbierająca wejścia od gracza
     */
    public void Input(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            quit = true;
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            mousePos.set(Gdx.input.getX(), Math.abs(Gdx.input.getY()-960), 0);
            player_clicked=false;
            enemy_clicked =false;
            clicked =true;
            counter =0;
        }
    }

    /**
     * Aktualizuje wejscia od gracza
     */
    public void Update() {
        menu_music.setVolume(StateManager_Ref.sound_volume/100);
        menu_music.isLooping();
        menu_music.play();
        if(enemy_units.isEmpty()){
            menu_music.stop();
            StateManager_Ref.PushState(new WonBattleState(StateManager_Ref));
        }else if(player_units.isEmpty()){
            menu_music.stop();
            StateManager_Ref.PushState(new LostGameState(StateManager_Ref));
        }
        if (clicked == true) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    if (grid_map[i][j].grid_body.contains(mousePos.x, mousePos.y)) {
                        if (row == i && col == j) {
                            counter = 2;
                        }

                        row = i;
                        col = j;

                        clicked = false;
                        if (counter == 2) {
                            player_clicked = false;
                            enemy_clicked = false;
                            row = -1;
                            col = -1;
                            counter = 0;
                            second_counter = 0;

                        } else {
                            if(player_turn==true) {
                                for (int k = 0; k < player_units.size(); k++) {
                                    if (player_units.elementAt(k).moved == false) {
                                        if (player_units.elementAt(k).current_row == row && player_units.elementAt(k).current_col == col) {
                                            second_counter = 1;
                                            index_temp = k;
                                            player_clicked = true;
                                            player_units.elementAt(k).moved = true;
                                            num_of_moves++;
                                        }
                                    }
                                }
                            }

                            if(enemy_turn==true) {
                                for (int k = 0; k < enemy_units.size(); k++) {
                                    if (enemy_units.elementAt(k).moved == false) {
                                        if (enemy_units.elementAt(k).current_row == row && enemy_units.elementAt(k).current_col == col) {
                                            second_counter = 1;
                                            index_temp = k;
                                            enemy_clicked = true;
                                            enemy_units.elementAt(k).moved = true;
                                            num_of_moves++;
                                        }
                                    }
                                }
                            }
                            counter = 1;
                        }

                    }
                }
                }
        }
            if (counter == 1 && second_counter == 1&&player_turn ==true) {
                int v1 = row - player_units.elementAt(index_temp).current_row;
                int v2 = col - player_units.elementAt(index_temp).current_col;
                if (v1 != 0 || v2 != 0) {
                    second_counter = 0;
                    counter = 0;
                    if(num_of_moves>=player_units.size() && player_turn == true){
                        player_turn =false;
                        enemy_turn =true;
                        num_of_moves =0;
                        for (int k = 0; k < enemy_units.size(); k++) {
                            enemy_units.elementAt(k).moved =false;
                        }
                    }
                }
                if (Math.abs(v1) <= 1 && Math.abs(v2) <= 1) {
                    System.out.println("PLAYER MOVE");
                    player_units.elementAt(index_temp).SetCurrentRowColumn(row, col);
                    player_units.elementAt(index_temp).SetPosition(grid_map[row][col].x, grid_map[row][col].y);
                }
                for (int k = 0; k < enemy_units.size(); k++) {
                    if (enemy_units.elementAt(k).current_row == row && enemy_units.elementAt(k).current_col == col) {
                        enemy_units.remove(k);
                    }
                }
            }
            if (counter == 1 && second_counter == 1&&enemy_turn ==true) {
                int v1 = row - enemy_units.elementAt(index_temp).current_row;
                int v2 = col - enemy_units.elementAt(index_temp).current_col;
                if (v1 != 0 || v2 != 0) {
                    second_counter = 0;
                    counter = 0;
                    if (num_of_moves >= enemy_units.size() && enemy_turn == true) {
                        enemy_turn = false;
                        player_turn = true;
                        num_of_moves = 0;
                        for (int k = 0; k < player_units.size(); k++) {
                            player_units.elementAt(k).moved = false;
                        }
                    }
            }
            if (Math.abs(v1) <= 1 && Math.abs(v2) <= 1) {
                enemy_units.elementAt(index_temp).SetCurrentRowColumn(row, col);
                enemy_units.elementAt(index_temp).SetPosition(grid_map[row][col].x, grid_map[row][col].y);
            }
             for (int k = 0; k < player_units.size(); k++) {
                 if(player_units.elementAt(k).current_row==row && player_units.elementAt(k).current_col==col){
                     player_units.remove(k);
                 }
             }

        }


        for(int i=0;i<player_units.size();i++){
            player_units.elementAt(i).Update();
        }
        for(int i=0;i<enemy_units.size();i++){
            enemy_units.elementAt(i).Update();
        }
    }

    /**
     * Rysuje gre
     */
    public void Draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(map_texture,0,0);
        batch.end();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.polygon(grid_map[i][j].grid_body.getTransformedVertices());
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.end();
            }
        }
        if(player_clicked  ==true) {
                ShowWays(player_units.elementAt(index_temp).current_row, player_units.elementAt(index_temp).current_col);
        }
        if(enemy_clicked == true){
            ShowWays(enemy_units.elementAt(index_temp).current_row, enemy_units.elementAt(index_temp).current_col);
        }
        for(int i=0;i<player_units.size();i++){
            player_units.elementAt(i).Draw();
        }
        for(int i=0;i<enemy_units.size();i++){
            enemy_units.elementAt(i).Draw();
        }
    }


    /**
     * @param current_row
     * @param current_col
     * Pokazuje możliwość poruszania się
     */
    void ShowWays(int current_row, int current_col){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(current_row +1 <5) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row + 1][current_col].x, grid_map[current_row+ 1][current_col].y, grid_map[current_row+ 1][current_col].width, grid_map[current_row+ 1][current_col].height);
        }
        if(current_row-1>=0) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row - 1][current_col].x, grid_map[current_row- 1][current_col].y, grid_map[current_row- 1][current_col].width, grid_map[current_row- 1][current_col].height);
        }
        if(current_col +1 < 9) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row][current_col + 1].x, grid_map[current_row][current_col+ 1].y, grid_map[current_row][current_col+ 1].width, grid_map[current_row][current_col+ 1].height);
        }
        if(current_col -1 >=0) {
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row][current_col - 1].x, grid_map[current_row][current_col-1].y, grid_map[current_row][current_col-1].width, grid_map[current_row][current_col-1].height);
        }
        if( current_col -1 >=0 && current_row-1>=0 ){
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row-1][current_col - 1].x, grid_map[current_row-1][current_col-1].y, grid_map[current_row-1][current_col-1].width, grid_map[current_row-1][current_col-1].height);
        }
        if( current_col +1 <9 && current_row+1 <5 ){
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row+1][current_col +1].x, grid_map[current_row+1][current_col+1].y, grid_map[current_row+1][current_col+1].width, grid_map[current_row+1][current_col+1].height);
        }
        if( current_col +1 <9 && current_row-1 >=0 ){
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row-1][current_col +1].x, grid_map[current_row-1][current_col+1].y, grid_map[current_row-1][current_col+1].width, grid_map[current_row-1][current_col+1].height);
        }
        if( current_col -1 >=0 && current_row+1 <5 ){
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(grid_map[current_row+1][current_col -1].x, grid_map[current_row+1][current_col-1].y, grid_map[current_row+1][current_col-1].width, grid_map[current_row+1][current_col-1].height);
        }
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(grid_map[current_row][current_col].x, grid_map[current_row][current_col].y, grid_map[current_row][current_col].width, grid_map[current_row][current_col].height);
        shapeRenderer.end();
    }
}
