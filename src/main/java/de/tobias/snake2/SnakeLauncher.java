package de.tobias.snake2;

import de.tobias.utils.application.ApplicationUtils;
import de.tobias.utils.application.Launcher;

/**
 * Öffnet das Game bei einer jar (exe, app), damit die natives kopiert werden können
 *
 * @author tobias
 */
public class SnakeLauncher {

	public static void main(String[] args) throws Exception {
		Launcher.launchApplication(ApplicationUtils.registerMainApplication(Snake.class), args);
	}
}
