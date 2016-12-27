package com.knightasterial.coolersimulator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.knightasterial.coolersimulator.CoolerSimulator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Cooler Simulator 2017";
		config.width = 1000;
		config.height = 800;
		
		new LwjglApplication(new CoolerSimulator(), config);
	}
}
