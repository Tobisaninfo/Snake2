package de.tobias.snake2.game.entities;

import de.tobias.snake2.Content;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.List;
import java.util.Random;

import static de.tobias.snake2.Snake.*;

/**
 * Klasse für das Essen
 *
 * @author tobias
 */
public class Foot extends Content {

	private Random r;

	/**
	 * Bild es Essens
	 */
	private Image image;
	/**
	 * Refferenz zum Schlangenkopf
	 */
	private SnakeHead head;
	/**
	 * Liste der Barrieren
	 */
	private List<Barrier> barriers;

	private int x;
	private int y;

	public Foot(List<Barrier> barriers) throws SlickException {
		r = new Random();

		this.image = new Image("assets/foot.png");
		this.barriers = barriers;

		randomPosiotion();
	}

	public void setSnakeHead(SnakeHead head) {
		this.head = head;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(image, x, y);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// Schlangenkopf und Essen sind auf dem gleichen Tile
		if (x == head.getRectangle().getX() && y == head.getRectangle().getY()) {
			head.eat();
			randomPosiotion();
		}
	}

	private void randomPosiotion() {
		boolean collision = true;
		while (collision) {
			collision = false;
			int tileX = r.nextInt(TILECOUNTX);
			int tileY = r.nextInt(TILECOUNTY);
			x = tileX * TILEWIDTH;
			y = tileY * TILEHEIGHT;

			for (Barrier barrier : barriers) {
				// Prüft ob die Koordinaten mit einem Hinderniss übereinstimmeb
				if (barrier.getTileX() == tileX && barrier.getTileY() == tileY) {
					collision = true;
				}
			}
		}
	}
}
