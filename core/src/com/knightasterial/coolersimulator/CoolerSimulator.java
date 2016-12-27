package com.knightasterial.coolersimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class CoolerSimulator extends ApplicationAdapter {
	
	SpriteBatch batch;
	ShapeRenderer sRender;
	
	Texture gameBoard;
	Texture selectorG;
	Texture selectorR;
	
	OrthographicCamera camera;
	
	int boardDimension;
	double scaleRatio;
	
	Vector3 mousePos;				//position of the mouse
	
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
		//sets the position of the mouse
		mousePos = new Vector3();
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos);
		
		selectorG = new Texture("SelectorGreen.png");
		selectorR = new Texture("SelectorRed.png");
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//good practice to update camera every frame
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		sRender.begin(ShapeType.Filled);
		sRender.setColor(0, 255, 0, 1);
		sRender.rect( 0, 0, (int) (64*scaleRatio), (int) (64*scaleRatio) );
		sRender.end();
		
		batch.begin();
		
		if ( !(mousePos.x > (512*scaleRatio)) &&  !(mousePos.y > (512*scaleRatio))){			//draws selector if on the board
			batch.draw(selectorG, 
						(int)( (int)(mousePos.x/(64*scaleRatio))*64*scaleRatio ), 
						(int)( (int)(mousePos.y/(64*scaleRatio))*64*scaleRatio ), 
						(int) (64*scaleRatio), 
						(int) (64*scaleRatio)
					  );
		}else{}
		
		
		batch.draw(gameBoard, 0, 0, boardDimension, boardDimension);
		batch.end();
		
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos);

		

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		sRender.dispose();
		gameBoard.dispose();
	}
	
	@Override

	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width, height);
		camera.update();

		//finds the dimensions of the board
		boardDimension = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//sets scale ratio of new / old
		scaleRatio = (double) boardDimension / (double) 512;
	}
}
