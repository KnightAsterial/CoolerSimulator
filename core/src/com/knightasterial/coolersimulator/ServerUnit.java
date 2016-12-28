package com.knightasterial.coolersimulator;

import com.badlogic.gdx.graphics.Texture;

public class ServerUnit{
	Texture unitImage;
	
	/**
	 * Clockwise rotation:
	 * 0 = up,
	 * 1 = right,
	 * 2 = down,
	 * 3 = left,
	 */
	int inputDirection;

	
	public ServerUnit(int direction){
		inputDirection = direction;
		
		if (inputDirection == 0){														//if pointing up
			unitImage = new Texture("ServerUnitUp.png");
		} else if (inputDirection == 1){												//if pointing right
			unitImage = new Texture("ServerUnitRight.png");
		} else if (inputDirection == 2){												//if pointing down
			unitImage = new Texture("ServerUnitDown.png");
		} else if (inputDirection == 3){												//if pointing left
			unitImage = new Texture("ServerUnitLeft.png");
		}
	}
	
	public Texture getImage(){
		return unitImage;
	}
	
	public int getDirection(){
		return inputDirection;
	}
	
}
