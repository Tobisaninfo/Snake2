package de.tobias.snake2.game.entities;

import de.tobias.snake2.Content;
import de.tobias.snake2.Snake;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import static de.tobias.snake2.Snake.TILEHEIGHT;
import static de.tobias.snake2.Snake.TILEWIDTH;

/**
 * Klasse für die Hindernisse
 *
 * @author tobias
 */
public class Barrier extends Content implements Comparable<Barrier> {

	private final Circle shape;
	private int tileX;
	private int tileY;

	public Barrier(int tileX, int tileY) {
		// Kreis mit Mittelpunkt Berechnung
		this.shape = new Circle(tileX * TILEWIDTH + TILEWIDTH / 2, tileY * TILEHEIGHT + Snake.TILEHEIGHT / 2, TILEWIDTH / 2);
		this.tileX = tileX;
		this.tileY = tileY;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public float getX() {
		return shape.getX();
	}

	public float getY() {
		return shape.getY();
	}

	public Circle getRect() {
		return shape;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.blue);
		g.fill(this.shape);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
	}

	// Vergleicht zwei beliebige Hindernisse (auf ihre Koordinaten
	public int compareTo(Barrier o) {
		if (getTileX() == o.getTileX() && getTileY() == getTileY()) {
			return 0;
		}
		return -1;
	}

}
