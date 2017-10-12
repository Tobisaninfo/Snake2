package de.tobias.snake2;

import java.io.File;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.tobias.snake2.scene.GameOverScene;
import de.tobias.snake2.scene.GameScene;
import de.tobias.snake2.scene.MenuScene;
import de.tobias.update.main.Update;
import de.tobias.utils.application.App;
import de.tobias.utils.application.ApplicationUtils;
import de.tobias.utils.application.NativeLauncher;
import de.tobias.utils.application.container.PathType;
import de.tobias.utils.settings.YAMLSettings;
import de.tobias.utils.util.Worker;

/**
 * Main Class des Games
 * 
 * @author tobias
 *
 */
public class Snake extends StateBasedGame {

	/**
	 * Filename der Einstellungen
	 */
	private static final String SETTINGS_YML = "settings.yml";

	/**
	 * Window width
	 */
	public static final int WIDTH = 800;
	/**
	 * Window Height
	 */
	public static final int HEIGHT = 600;

	/**
	 * Kästchenbreite
	 */
	public static final int TILEWIDTH = 20;
	/**
	 * Kästchenhöhe
	 */
	public static final int TILEHEIGHT = 20;

	/**
	 * Kästchen Anzahl X Richtung
	 */
	public static final int TILECOUNTX = WIDTH / TILEWIDTH;
	/**
	 * Kästchen Anzahl Y Richtung
	 */
	public static final int TILECOUNTY = HEIGHT / TILEHEIGHT;

	private static App application;
	private static SnakeSettings settings;

	public Snake() throws SlickException {
		super(application.getInfo().getName());
	}

	public static void main(String[] args) throws SlickException {
		// Settings
		try {
			application = ApplicationUtils.registerMainApplication(Snake.class);
			application.start(args);

			Worker.runLater(() -> {
				try {
					Update update = Update.load(ApplicationUtils.getMainApplication().getInfo().getIdentifier());
					if (update != null) {
						int result = JOptionPane.showConfirmDialog(null, "Wollen Sie Snake aktualisieren",
								"Neue Version: " + update.getNewVersion(), JOptionPane.YES_NO_OPTION);

						if (result == JOptionPane.OK_OPTION) {
							Worker.runLater(() -> {
								try {
									File file = new File("UpdateManager.jar");
									if (file.exists()) {
										ProcessBuilder builder = new ProcessBuilder("java", "-jar", file.getAbsolutePath(), "--app="
												+ ApplicationUtils.getMainApplication().getInfo().getIdentifier() + "");
										builder.start();
										System.exit(0);
									} else {
										File file2 = new File("UpdateManager.exe");
										if (file2.exists()) {
											NativeLauncher.executeAsAdministrator(file2.getAbsolutePath(), "");
											System.exit(0);
										}
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			// Lädt die Einstellungen
			settings = YAMLSettings.load(SnakeSettings.class, ApplicationUtils.getApplication().getPath(PathType.CONFIGURATION, SETTINGS_YML));

			// Speichert die Einstellungen
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					YAMLSettings.save(settings, ApplicationUtils.getApplication().getPath(PathType.CONFIGURATION, SETTINGS_YML));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Slick2D Setup
		AppGameContainer container = new AppGameContainer(new Snake());
		container.setVerbose(false);
		container.setVSync(true);

		container.setDisplayMode(WIDTH, HEIGHT, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(false);

		// Startet das Spiel
		container.start();
	}

	public static SnakeSettings getSettings() {
		return settings;
	}

	/**
	 * Erstellt die einzelnen Szenen
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MenuScene());
		addState(new GameScene());
		addState(new GameOverScene());
	}
}
