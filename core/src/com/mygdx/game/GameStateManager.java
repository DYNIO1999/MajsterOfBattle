package com.mygdx.game;

import java.util.Stack;

public class GameStateManager {
    public float sound_volume;
    public int screen_width=1280;
    public int screen_height=960;
    Stack<State> states = new Stack<State>();
    GameStateManager(){
    }
    public void PushState(State new_state){
        states.push(new_state);
    }
    public void PopState(){
        if(!states.isEmpty()) {
            states.pop();
        }
    }
    public void Run(){
        State temp = states.peek();
        temp.Input();
        temp.Update();
        temp.Draw();
    }
    /*public void SwitchState(State new_state){
        GameStates.
    }*/
}
