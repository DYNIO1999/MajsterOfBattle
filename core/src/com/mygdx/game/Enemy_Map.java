package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.Random;

public class Enemy_Map extends DynamicBody{
    class Point{
        int x;
        int y;
        boolean visited;
        Point(int x,int y){
            this.x=x;
            this.y=y;
            this.visited = false;
        }
    }
    private  double startX, startY, endX, endY;

    private  double speed = 70;
    private  double elapsed = 0.02f;
    private  double directionY;
    private  double directionX;
    private  double distance;
    private  int it;
    boolean moving;

    private  int  counter;

    private Polygon enemy_poly;

    private int x_min;
    private int y_min;
    private int x_max;
    private int y_max;
    private  Texture texture;

    private Batch batch;

    ShapeRenderer shape;

    Point[] test_arr;

    int tescik[];
    Enemy_Map(){}

    Enemy_Map(Texture texture, int x, int y, int width, int height, Batch batch, int x_min, int y_min, int x_max, int y_max){
        this.batch=batch;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.texture = texture;
        this.x_min=x_min;
        this.x_max=x_max;
        this.y_min=y_min;
        this.y_max=y_max;

        shape = new ShapeRenderer();

        sprite= new Sprite(this.texture,0,0,this.width, this.height);

        enemy_poly=new Polygon(new float[] { 0, 0, sprite.getWidth(), 0, sprite.getWidth(),
                sprite.getHeight(), 0, sprite.getHeight()});
        test_arr = new Point[4];
        int i=0;
        Random test_random =new Random();
        for(i=0;i<4;i++){
            int x_Temp = (int) (Math.random() *(x_max-x_min)) + x_min;
            int y_Temp = (int) (Math.random() * (y_max-y_min))+y_min;
            if(x_Temp<=0){
                x_Temp=x_Temp*(-1);
            }else if(y_Temp<=0){
                y_Temp=y_Temp*(-1);
            }
            System.out.println(x_Temp +""+y_Temp);
            Point temp=new Point(x_Temp,y_Temp);
            test_arr[i]=temp;
        }
        tescik = n_neighbour(test_arr);

        System.out.println("Path");
        for(int j=0;j<5;j++){
            System.out.println(tescik[j]);
        }
        moving =true;
        it=0;
        sprite.setPosition(test_arr[0].x,test_arr[0].y);
        System.out.println(test_arr[0].x);
        System.out.println(test_arr[0].y);
        System.out.println(sprite.getX());
        System.out.println(sprite.getY());

        counter =0;
    }
    @Override
    public void Update(){
        if(it>=4){
            it=0;
        }
        System.out.println(counter);
        if(counter ==1 && it==0){
            System.out.println("CHECK!");
            Random test_random =new Random();
            int x_Temp = (int)sprite.getX();
            int y_Temp = (int)sprite.getY();
            System.out.println(x_Temp);
            Point temp=new Point(x_Temp,y_Temp);
            test_arr[0]=temp;
            for(int i=1;i<4;i++){
                x_Temp = (int) (Math.random() *(x_max-x_min)) + x_min;
                y_Temp =  (int) (Math.random() * (y_max-y_min))+y_min;
                if(x_Temp<=0){
                    x_Temp=x_Temp*(-1);
                }else if(y_Temp<=0){
                    y_Temp=y_Temp*(-1);
                }
                System.out.println(x_Temp);
                temp=new Point(x_Temp,y_Temp);
                test_arr[i]=temp;
            }
            tescik = n_neighbour(test_arr);
            counter =0;
        }
        System.out.println("IT "+it);
        System.out.println("X_SPRITE "+sprite.getX());
        System.out.println("X_NEW_NODE "+test_arr[tescik[it]].x);
        //System.out.println(test_arr[tescik[it]].x);
        if((((int)sprite.getX()) ==test_arr[tescik[it]].x) && (((int)sprite.getY())==test_arr[tescik[it]].y)) {
            System.out.println("CHECK2");
            startX = sprite.getX();
            startY = sprite.getY();
            endX = test_arr[tescik[it+1]].x;
            endY = test_arr[tescik[it+1]].y;
            distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
            directionX = (endX - startX) / distance;
            directionY = (endY - startY) / distance;
            if(it==0){
                counter++;
            }
            it++;
        }
        sprite.setX((float) (sprite.getX() + directionX * speed * elapsed));
        sprite.setY((float) (sprite.getY() + directionY * speed * elapsed));

        if(Math.sqrt(Math.pow(sprite.getX()-startX,2)+Math.pow(sprite.getY()-startY,2)) >= distance)
        {
            sprite.setX((float)endX);
            sprite.setY((float)endY);
            moving = false;
        }
        //System.out.println(it);
        //System.out.println(sprite.getX());
        //System.out.println(sprite.getY());
        enemy_poly.setPosition(sprite.getX()+5, sprite.getY());
    }
    @Override
    public void Draw(){
        batch.begin();
        sprite.draw(batch);
        batch.end();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(sprite.getX()+5, sprite.getY(),sprite.getWidth(),sprite.getHeight());
        shape.end();
    }
    public Polygon GetShape(){
        return enemy_poly;
    }
    public void ApplyCollision(float dX, float dY, float distance){
        sprite.setX((float)(sprite.getX()+dX * distance *speed * elapsed));
        sprite.setY((float)(sprite.getY()+dY * distance *speed * elapsed));
    }
    double calculate_distance(Point A,Point B){
        double result =-1;
        result = Math.sqrt(Math.pow((B.x-A.x),2.0)+Math.pow((B.y-A.y),2.0));
        return result;
    }
    int[] n_neighbour(Point[] arr){
        int start_index =-1;
        int end_index =-1;
        int current_index =-1;
        double dist =1000000000.0;
        int choosen_index =-1;
        int[] road_indexes =new int[5];
        start_index =0;
        end_index =0;


        int size = 4;
        int k=1;
        arr[start_index].visited=true;
        road_indexes[0]=start_index;
        current_index =start_index;
        for(int i=0;i<size-1;i++){
            for(int j=0;j<size;j++){
                if (j == current_index || j == end_index || arr[j].visited == true)
                {
                    continue;
                }
                else
                {
                    double temp = calculate_distance(arr[current_index], arr[j]);
                    if(temp<=dist){
                        dist =temp;
                        choosen_index =j;
                    }
                }
            }
            dist = 1000000000.0;
            arr[choosen_index].visited=true;
            road_indexes[k]=choosen_index;
            k++;
            current_index =choosen_index;
        }
        road_indexes[k]=0;
        return  road_indexes;
    }


}
