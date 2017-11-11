package de.tobias.snake2;

import de.tobias.utils.settings.SettingsSerializable;
import de.tobias.utils.settings.Storable;

/**
 * Container für die Einstellungen (Speicherort Mac: ~/Library/Application Support/Java File Container/de.tobias.snake2/Config/settings.yml)
 *
 * @author tobias
 */
public class SnakeSettings implements SettingsSerializable {

	private static final long serialVersionUID = 1L;

	@Storable
	public int s_highScore = 0;
	@Storable
	public int s_defaultBarrieres = 20;
	@Storable
	public int s_defaultSnakeLength = 5;
	@Storable
	public int s_addLengthThenEat = 5;
	@Storable
	public int s_moveThreshold = 150;
}
