package de.tobias.snake2.game.entities;

import static de.tobias.snake2.Snake.HEIGHT;
import static de.tobias.snake2.Snake.TILECOUNTX;
import static de.tobias.snake2.Snake.TILECOUNTY;
import static de.tobias.snake2.Snake.TILEHEIGHT;
import static de.tobias.snake2.Snake.TILEWIDTH;
import static de.tobias.snake2.Snake.WIDTH;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.tobias.snake2.Content;

/**
 * Klasse für den Hintergrund von Snake
 * 
 * @author tobias
 *
 */
public class Background extends Content {

	private Image image;

	public Background() throws SlickException {
		image = new Image(WIDTH, HEIGHT);

		Random r = new Random();
		Graphics g = image.getGraphics();

		// Erstellt ein Zufälliges Bild aus verschiedenen Grüntönen
		for (int i = 0; i < TILECOUNTX; i++) {
			for (int j = 0; j < TILECOUNTY; j++) {
				Color color = new Color(r.nextInt(100) + 50, r.nextInt(100) + 155, r.nextInt(50) + 25);
				g.setColor(color);
				g.drawRect(i * TILEWIDTH, j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
			}
		}
		g.flush(); // Übernimmt die Zeichnungen
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawImage(image, 0, 0);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
	}

}
