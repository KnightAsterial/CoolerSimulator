package com.knightasterial.coolersimulator;

public class AirUnit {
	int temperature;
	
	float red;
	float green;
	float blue;
	float alpha;
	
	/**
	 * @param temperature between 0-99
	 */
	public AirUnit(int temperature){
		this.temperature = temperature;
		if (temperature < 0){
			temperature = 0;
		}
		
		alpha = 1;
		
		if (temperature >= 0 && temperature <= 24){
			red = 0;
			green = (temperature*255/24.0f)/255.0f;
			blue = 255;
		} else if (temperature >= 25 && temperature <= 49){
			red = 0;
			green = 255;
			blue = (Math.abs(49-temperature)*255/24.0f)/255.0f;
			
		} else if (temperature >= 50 && temperature <=74){
			red = ((temperature-50)*255/24.0f)/255.0f;
			green = 255;
			blue = 0;
			
		} else if (temperature >= 75 && temperature <= 99){
			red = 255;
			green = (Math.abs(99-temperature)*255/24.0f)/255.0f;
			blue = 0;
		}
		else if (temperature > 99){
			red = 255;
			green = 255;
			blue = 255;
		}
		
	}
	
	public int getTemperature(){
		return temperature;
	}
	public void setTemperature(int temp){
		temperature = temp;
		if (temperature < 0){
			temperature = 0;
		}
		
		
		alpha = 1;
		
		if (temperature >= 0 && temperature <= 24){
			red = 0;
			green = (temperature*255/24.0f)/255.0f;
			blue = 255;
		} else if (temperature >= 25 && temperature <= 49){
			red = 0;
			green = 255;
			blue = (Math.abs(49-temperature)*255/24.0f)/255.0f;
			
		} else if (temperature >= 50 && temperature <=74){
			red = ((temperature-50)*255/24.0f)/255.0f;
			green = 255;
			blue = 0;
			
		} else if (temperature >= 75 && temperature <= 99){
			red = 255;
			green = (Math.abs(99-temperature)*255/24.0f)/255.0f;
			blue = 0;
		}
		else if (temperature > 99){
			red = 255;
			green = 255;
			blue = 255;
		}
	}
	
	public void addTemperature(int temp){
		temperature += temp;
		if (temperature < 0){
			temperature = 0;
		}
		
		
		alpha = 1;
		
		if (temperature >= 0 && temperature <= 24){
			red = 0;
			green = (temperature*255/24.0f)/255.0f;
			blue = 255;
		} else if (temperature >= 25 && temperature <= 49){
			red = 0;
			green = 255;
			blue = (Math.abs(49-temperature)*255/24.0f)/255.0f;
			
		} else if (temperature >= 50 && temperature <=74){
			red = ((temperature-50)*255/24.0f)/255.0f;
			green = 255;
			blue = 0;
			
		} else if (temperature >= 75 && temperature <= 99){
			red = 255;
			green = (Math.abs(99-temperature)*255/24.0f)/255.0f;
			blue = 0;
		}
		else if (temperature > 99){
			red = 255;
			green = 255;
			blue = 255;
		}
	}
	
	public float getRed(){
		return red;
	}
	public float getGreen(){
		return green;
	}
	public float getBlue(){
		return blue;
	}
	public float getAlpha(){
		return alpha;
	}
}
