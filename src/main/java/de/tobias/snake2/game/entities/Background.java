package de.tobias.snake2.game.entities;

import de.tobias.snake2.Content;
import org.newdawn.slick.*;

import java.util.Random;

import static de.tobias.snake2.Snake.*;

/**
 * Klasse für den Hintergrund von Snake
 *
 * @author tobias
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
