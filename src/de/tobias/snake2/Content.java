package de.tobias.snake2;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Abstrakte Klasse damit ein Object einer beliebigen Klasse in das Spiel
 * eingefügt werden kann.
 * 
 * @author tobias
 *
 */
public abstract class Content {

	/**
	 * Rendern des Objectes
	 * 
	 * @param gc
	 *            Spielcontainer
	 * @param g
	 *            Grafikkomponente des Spiels
	 * @throws SlickException
	 *             Beliebiger Fehler
	 */
	public abstract void render(GameContainer gc, Graphics g) throws SlickException;

	/**
	 * Aktualiesiert das Object
	 * 
	 * @param gc
	 *            Spielcontainer
	 * @param delta
	 *            Zeitabstand zum letzten Update
	 * @throws SlickException
	 *             Beliebiger Fehler
	 */
	public abstract void update(GameContainer gc, int delta) throws SlickException;
}
