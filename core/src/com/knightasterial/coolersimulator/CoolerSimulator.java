package com.knightasterial.coolersimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CoolerSimulator extends ApplicationAdapter {
	
	SpriteBatch batch;
	ShapeRenderer sRender;
	
	Texture gameBoard;
	OrthographicCamera camera;
	
	int boardDimension;
	double scaleRatio;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sRender = new ShapeRenderer();
		
		gameBoard = new Texture("8x8Board.png");
		
		//creates the camera and sets it to look at a world in the size of the window
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//finds the dimensions of the board
		boardDimension = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//sets scale ratio of new / old
		scaleRatio = (double) boardDimension / (double) 512;
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//good practice to update camera every frame
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(gameBoard, 0, 0, boardDimension, boardDimension);
		batch.end();
		
		sRender.begin(ShapeType.Filled);
		sRender.setColor(0, 255, 0, 0.1f);
		sRender.rect( 0, 0, (int) (64*scaleRatio), (int) (64*scaleRatio) );
		sRender.end();
		

	}
	
	@Override
	public void dispose () {

	}
}
