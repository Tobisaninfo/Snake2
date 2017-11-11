package de.tobias.snake2.game;

import de.tobias.snake2.Content;
import de.tobias.snake2.Snake;
import de.tobias.snake2.game.entities.SnakeHead;
import de.tobias.snake2.scene.GameScene;
import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.Font;

/**
 * Wrapper für den Spieler.
 *
 * @author tobias
 */
public class Player extends Content {

	/**
	 * Schlange (Schlangenkopf)
	 */
	private SnakeHead head;

	/**
	 * Spielstand
	 */
	private int score;

	// Schriftart
	private Font font;
	private TrueTypeFont scoreFont;

	public Player(GameScene gameScene) throws SlickException {
		score = 0;

		font = new Font("Sans serif", Font.BOLD, 20);
		scoreFont = new TrueTypeFont(font, true);

		// Erstellt eine Schlange
		head = new SnakeHead(gameScene.getWorld().getBarriers(), this, Snake.TILECOUNTX / 2, Snake.TILECOUNTY / 2);

		// Fügt der Schlange Körperteile hinzu
		for (int i = 0; i < Snake.getSettings().s_defaultSnakeLength; i++) {
			head.addPart();
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public SnakeHead getSnake() {
		return head;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		head.render(gc, g);

		// Highscore
		g.setColor(Color.white);
		g.setFont(scoreFont);
		g.drawString(score + "S + " + head.getLifeCount() + "H", 5, 0);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		head.update(gc, delta);
	}

	public void setEasy(boolean easy) {
		this.head.setEasy(easy);
	}
}
