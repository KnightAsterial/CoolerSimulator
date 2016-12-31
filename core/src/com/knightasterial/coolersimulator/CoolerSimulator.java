package com.knightasterial.coolersimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
	AirUnit currentAir;				//variable to hold current airUnit for drawing
	
	int upTemp;
	int rightTemp;
	int downTemp;
	int leftTemp;
	int currentTemp;
	
	ServerUnit currentServer;		//variable for holding current serverUnit for drawing
	int arrayVerticalIndex;			//variable for holding the vertical array index of the mouse
	int arrayHorizontalIndex;		//variable for holding the horizontal array index of the mouse
	
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
	
	Sound invalidPlacementSound;

	
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
		//unitArray[0][1] = new ServerUnit(1);
		
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if (unitArray[verticalNum][horizontalNum] == null){
					airArray[verticalNum][horizontalNum] = new AirUnit(0);
				}
			}
		}
		
		invalidPlacementSound = Gdx.audio.newSound(Gdx.files.internal("invalidPlacement.mp3"));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//good practice to update camera every frame
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		sRender.setProjectionMatrix(camera.combined);
		
		sRender.begin(ShapeType.Filled);
		
		//-------------------------------Draw Temperature AirUnits-----------------------------
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if ( (airArray[verticalNum][horizontalNum] != null) ){
					currentAir = airArray[verticalNum][horizontalNum];
					sRender.setColor(currentAir.getRed(), currentAir.getGreen(), currentAir.getBlue(), currentAir.getAlpha());
					sRender.rect((int) (horizontalNum*64*scaleRatio), (int) (verticalNum*64*scaleRatio), (int) (64*scaleRatio), (int) (64*scaleRatio));
				}
			}
		}
		
		sRender.end();
		
		batch.begin();
		
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
		
		batch.draw(gameBoard, 0, 0, boardDimension, boardDimension);
		batch.end();
		
		
		
		
		//-------------------------------Draws Selector Mouse Position & Direction-----------------------------
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
		//-------------------------------Create Units After Click-----------------------------
		if (Gdx.input.justTouched()){
			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);		//finds mouse position
			camera.unproject(mousePos);
			
			if (mousePos.x < 512*scaleRatio && mousePos.y < 512*scaleRatio){									//adds ServerUnit and deletes AirUnit
				
				arrayVerticalIndex = (int)( mousePos.y / (int) (64*scaleRatio) );
				arrayHorizontalIndex = (int)( mousePos.x / (int) (64*scaleRatio) );
				
				//if the selector faces up or down
				if( selectorDirection == 0 || selectorDirection ==2){
					//if the top or bottom doesn't face an edge
					if (arrayVerticalIndex+1 <= 7 && arrayVerticalIndex-1 >= 0){
						//if there is air next to the intake and output
						if ( airArray[arrayVerticalIndex+1][arrayHorizontalIndex] != null && airArray[arrayVerticalIndex-1][arrayHorizontalIndex] != null ){
							
							//error sound if there is a horizontal unit right next to it on the right
							if ( (arrayHorizontalIndex+1 <8 && unitArray[arrayVerticalIndex][arrayHorizontalIndex+1] != null) && ( (unitArray[arrayVerticalIndex][arrayHorizontalIndex+1].getDirection() == 1 || unitArray[arrayVerticalIndex][arrayHorizontalIndex+1].getDirection() ==3)) ){
								invalidPlacementSound.play();
							}
							//error sound if there is a horizontal unit right next to it on the left
							else if ( (arrayHorizontalIndex-1 > -1 && unitArray[arrayVerticalIndex][arrayHorizontalIndex-1] != null) && ( (unitArray[arrayVerticalIndex][arrayHorizontalIndex-1].getDirection() == 1 || unitArray[arrayVerticalIndex][arrayHorizontalIndex-1].getDirection() ==3)) ){
								invalidPlacementSound.play();
							}
							else{
								unitArray[arrayVerticalIndex][arrayHorizontalIndex] = new ServerUnit(selectorDirection);
								airArray[arrayVerticalIndex][arrayHorizontalIndex] = null;
							}
							
							
						}else{
							invalidPlacementSound.play();
						}
					}
					else{
						invalidPlacementSound.play();
					}
				//if the selector faces left or right
				} else if(selectorDirection == 1 || selectorDirection == 3){
					//if the left or right doesn't face an edge
					if (arrayHorizontalIndex+1 <= 7 && arrayHorizontalIndex-1 >= 0){
						//if there is air next to the input and output
						if ( airArray[arrayVerticalIndex][arrayHorizontalIndex+1] != null && airArray[arrayVerticalIndex][arrayHorizontalIndex-1] != null){
							
							//error sound if there is a vertical unit above it
							if ( (arrayVerticalIndex+1 < 8 && unitArray[arrayVerticalIndex+1][arrayHorizontalIndex] != null) && ( (unitArray[arrayVerticalIndex+1][arrayHorizontalIndex].getDirection() == 0 || unitArray[arrayVerticalIndex+1][arrayHorizontalIndex].getDirection() ==2)) ){
								invalidPlacementSound.play();
							}
							//error sound if there is a vertical unit below it
							else if ( (arrayVerticalIndex-1 > -1 && unitArray[arrayVerticalIndex-1][arrayHorizontalIndex] != null) && ( (unitArray[arrayVerticalIndex-1][arrayHorizontalIndex].getDirection() == 0 || unitArray[arrayVerticalIndex-1][arrayHorizontalIndex].getDirection() ==2)) ){
								invalidPlacementSound.play();
							}
							else{
								unitArray[arrayVerticalIndex][arrayHorizontalIndex] = new ServerUnit(selectorDirection);
								airArray[arrayVerticalIndex][arrayHorizontalIndex] = null;
							}
							
							
							
						}else{
							invalidPlacementSound.play();
						}
					}
					else{
						invalidPlacementSound.play();
					}
					
				}
				else{
					invalidPlacementSound.play();
				}
			}else{
				invalidPlacementSound.play();
			}
		}
		
		
		
		
		//-------------------------------Deletes Units After "ESCAPE-pressed"-----------------------------
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);		//finds mouse position
			camera.unproject(mousePos);
			
			if (mousePos.x < 512*scaleRatio && mousePos.y < 512*scaleRatio){									//deletes ServerUnit and creates AirUnit
				unitArray[(int)( mousePos.y / (int) (64*scaleRatio) )][(int)( mousePos.x / (int) (64*scaleRatio) )] = null;
				airArray[(int)( mousePos.y / (int) (64*scaleRatio) )][(int)( mousePos.x / (int) (64*scaleRatio) )] = new AirUnit(0);
			}
		}
		
		//TEMP TEMP TEMP TEMP TEMP
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)){
			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);		//finds mouse position
			camera.unproject(mousePos);
			
			if (mousePos.x < 512*scaleRatio && mousePos.y < 512*scaleRatio && airArray[(int)( mousePos.y / (int) (64*scaleRatio) )][(int)( mousePos.x / (int) (64*scaleRatio) )] != null){
				airArray[(int)( mousePos.y / (int) (64*scaleRatio) )][(int)( mousePos.x / (int) (64*scaleRatio) )].addTemperature(1);
			}
		}
		
		//add temperature to air
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if ( (unitArray[verticalNum][horizontalNum] != null) ){
					
					currentServer = unitArray[verticalNum][horizontalNum];
					
					if (currentServer.getDirection() == 0){
						airArray[verticalNum-1][horizontalNum].addTemperature( (airArray[verticalNum+1][horizontalNum].getTemperature()+10) );
					}
					if (currentServer.getDirection() == 1){
						airArray[verticalNum][horizontalNum-1].addTemperature( (airArray[verticalNum][horizontalNum+1].getTemperature()+10) );
					}
					if (currentServer.getDirection() == 2){
						airArray[verticalNum+1][horizontalNum].addTemperature( (airArray[verticalNum-1][horizontalNum].getTemperature()+10) );
					}
					if (currentServer.getDirection() == 3){
						airArray[verticalNum][horizontalNum+1].addTemperature( (airArray[verticalNum][horizontalNum-1].getTemperature()+10) );
					}
				}
			}
		}
		
		//makes temperature spread
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if (airArray[verticalNum][horizontalNum] != null){
					
					currentAir = airArray[verticalNum][horizontalNum];
					
					currentTemp = currentAir.getTemperature();
					
					//temp of air unit above
					if (verticalNum+1 > 7){
						upTemp = 0;
					}else if (airArray[verticalNum+1][horizontalNum] == null){
						upTemp = currentTemp;
					}
					else{
						upTemp = airArray[verticalNum+1][horizontalNum].getTemperature();
					}
					//temp of air unit right
					if (horizontalNum+1 > 7){
						rightTemp = 0;
					}else if (airArray[verticalNum][horizontalNum+1] == null){
						rightTemp = currentTemp;
					}
					else{
						rightTemp = airArray[verticalNum][horizontalNum+1].getTemperature();
					}
					//temp of air unit below
					if (verticalNum - 1 < 0){
						downTemp = 0;
					}else if (airArray[verticalNum-1][horizontalNum] == null){
						downTemp = currentTemp;
					}
					else{
						downTemp = airArray[verticalNum-1][horizontalNum].getTemperature();
					}
					//temp of air unit left
					if (horizontalNum-1 < 0){
						leftTemp = 0;
					}else if (airArray[verticalNum][horizontalNum-1] == null){
						leftTemp = currentTemp;
					}
					else{
						leftTemp = airArray[verticalNum][horizontalNum-1].getTemperature();
					}
					
					currentAir.setTemperature((upTemp+rightTemp+downTemp+leftTemp+currentTemp)/5);
					
				}
			}
		}
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		sRender.dispose();
		gameBoard.dispose();
		
		selectorG.dispose();
		selectorR.dispose();
		
		selectorUp.dispose();
		selectorRight.dispose();
		selectorDown.dispose();
		selectorLeft.dispose();
		
		//disposes of Textures within ServerUnits
		for (int verticalNum = 0; verticalNum < 8; verticalNum++){
			for (int horizontalNum = 0; horizontalNum < 8; horizontalNum++){
				if ( (unitArray[verticalNum][horizontalNum] != null) ){
					unitArray[verticalNum][horizontalNum].unitImage.dispose();
				}
			}
		}
		
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
