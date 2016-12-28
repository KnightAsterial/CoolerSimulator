package com.knightasterial.coolersimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	
	/**
	 * Clockwise rotation:
	 * 0 = up,
	 * 1 = right,
	 * 2 = down,
	 * 3 = left,
	 */
	int selectorDirection;
	Texture selectorUp;
	Texture selectorRight;
	Texture selectorDown;
	Texture selectorLeft;
	
	ServerUnit[][] unitArray;
	AirUnit[][] airArray;
	
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
		
		selectorDirection = 0;
		selectorUp = new Texture("UnitSelectorUp.png");
		selectorRight = new Texture("UnitSelectorRight.png");
		selectorDown = new Texture("UnitSelectorDown.png");
		selectorLeft = new Texture("UnitSelectorLeft.png");
		
		unitArray = new ServerUnit[8][8];
		airArray = new AirUnit[8][8];
		
		//temp
		unitArray[0][1] = new ServerUnit(1);
		
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if (unitArray[verticalNum][horizontalNum] == null){
					airArray[verticalNum][horizontalNum] = new AirUnit(0);
				}
			}
		}

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
		
		//-------------------------------Draw Selector-----------------------------
		if ( !(mousePos.x > (512*scaleRatio)) &&  !(mousePos.y > (512*scaleRatio))){			//draws selector if on the board
			
			if (selectorDirection == 0){														//if selector facing up
				batch.draw(selectorUp, 
							(int)( (int)(mousePos.x/(64*scaleRatio))*64*scaleRatio ), 
							(int)( (int)(mousePos.y/(64*scaleRatio))*64*scaleRatio ), 
							(int) (64*scaleRatio), 
							(int) (64*scaleRatio)
						  );
			} else if(selectorDirection == 1){													//if selector facing right
				batch.draw(selectorRight, 
						(int)( (int)(mousePos.x/(64*scaleRatio))*64*scaleRatio ), 
						(int)( (int)(mousePos.y/(64*scaleRatio))*64*scaleRatio ), 
						(int) (64*scaleRatio), 
						(int) (64*scaleRatio)
					  );
			} else if(selectorDirection == 2){													//if selector facing down
				batch.draw(selectorDown, 
						(int)( (int)(mousePos.x/(64*scaleRatio))*64*scaleRatio ), 
						(int)( (int)(mousePos.y/(64*scaleRatio))*64*scaleRatio ), 
						(int) (64*scaleRatio), 
						(int) (64*scaleRatio)
					  );
			} else if(selectorDirection == 3){													//if selector facing left
				batch.draw(selectorLeft, 
						(int)( (int)(mousePos.x/(64*scaleRatio))*64*scaleRatio ), 
						(int)( (int)(mousePos.y/(64*scaleRatio))*64*scaleRatio ), 
						(int) (64*scaleRatio), 
						(int) (64*scaleRatio)
					  );
			}
			
		}else{}
		
		//-------------------------------Draw Server Units-----------------------------
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if ( (unitArray[verticalNum][horizontalNum] != null) ){
					batch.draw(unitArray[verticalNum][horizontalNum].getImage(),
								(int) (horizontalNum*64*scaleRatio),
								(int) (verticalNum*64*scaleRatio),
								(int) (64*scaleRatio), 
								(int) (64*scaleRatio)
							  );
				}
			}
		}
		
		
		batch.draw(gameBoard, 0, 0, boardDimension, boardDimension);
		batch.end();
		
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);		//finds mouse position for selector
		camera.unproject(mousePos);

		if (Gdx.input.isKeyJustPressed(Input.Keys.E)){				//E rotates selector clockwise 1
			selectorDirection = (selectorDirection +1)%4;				
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
			selectorDirection = (selectorDirection -1);				//Q rotates selector counterclockwise 1
			if (selectorDirection < 0){
				selectorDirection += 4;
			}
		}

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
