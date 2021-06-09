package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public class GameEngine extends ApplicationAdapter {
	/**
	 * GameStateManager -cos
	 */
	public  GameStateManager GameStates;
	static public float sound_volume;
	static public int lost_battles;
	static public int won_battles;
	static public int max_won_battles;
	static public int max_lost_battles;

	public boolean sound;
	@Override
	public void create () {
		sound_volume = 0;
		lost_battles =0;
		won_battles=0;
		max_lost_battles=0;
		max_won_battles =0;
		sound = false;
		GameStates = new GameStateManager();
		MainMenu menu = new MainMenu(GameStates);
		GameStates.PushState(menu);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!GameStates.states.isEmpty())
		{
			GameStates.Run();
		}

	}

	@Override
	public void dispose () {

	}
}
