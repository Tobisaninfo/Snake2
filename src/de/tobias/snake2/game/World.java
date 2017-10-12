package de.tobias.snake2.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import de.tobias.snake2.Content;
import de.tobias.snake2.Snake;
import de.tobias.snake2.game.entities.Background;
import de.tobias.snake2.game.entities.Barrier;
import de.tobias.snake2.game.entities.Foot;
import de.tobias.snake2.game.entities.Gift;
import de.tobias.snake2.game.entities.SnakeHead;
import de.tobias.snake2.scene.GameScene;

/**
 * Wrapper für alles Objekte der Spielwelt (ohne Spieler), also nur Umgebung.
 * 
 * @author tobias
 *
 */
public class World extends Content {

	private final Random random;

	// Spielweltobjekte
	/**
	 * Hintergrund der Spielwelt
	 */
	private Background background;
	/**
	 * Hindernisse
	 */
	private List<Barrier> barriers;
	/**
	 * Essen
	 */
	private Foot foot;
	private Gift gift;

	private boolean easy;

	private SnakeHead head;

	public World() throws SlickException {
		random = new Random();

		background = new Background();
		barriers = new ArrayList<>();
		foot = new Foot(barriers);
	}

	public void init(GameScene gameScene) {
		// Fügt zufällige Hindernisse hinzu
		for (int i = 0; i < Snake.getSettings().s_defaultBarrieres; i++) {
			addBarrierRandom();
		}
		this.head = gameScene.getPlayer().getSnake();
		foot.setSnakeHead(head);
	}

	public List<Barrier> getBarriers() {
		return barriers;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		background.render(gc, g);
		for (int i = 0; i < barriers.size(); i++)
			barriers.get(i).render(gc, g);
		foot.render(gc, g);
		if (gift != null) {
			gift.render(gc, g);
		}
	}

	private long lastGiftLeft = System.currentTimeMillis();

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		foot.update(gc, delta);
		if (easy) {
			if (gift == null) {
				if (lastGiftLeft + 1000 * 10 < System.currentTimeMillis()) {
					gift = new Gift(barriers);
					gift.setSnakeHead(head);
				}
			} else {
				gift.update(gc, delta);
				if (gift.isEaten()) {
					lastGiftLeft = System.currentTimeMillis();
					gift = null;
				}
			}
		}
	}

	private void addBarrierRandom() {
		boolean added = false;
		while (!added) {
			int x = random.nextInt(Snake.TILECOUNTX);
			int y = random.nextInt(Snake.TILECOUNTY);

			Barrier barrier = new Barrier(x, y);

			// Sucht nach gleichen Hindernissen und prüft ob ein Hinderniss in
			// den Mittelachsen liegt
			if (Collections.binarySearch(barriers, barrier) <= 0 && x != Snake.TILECOUNTX / 2 && y != Snake.TILECOUNTY / 2) {
				barriers.add(barrier);
				added = true;
			}
		}
	}

	public void setEasy(boolean easy) {
		this.easy = easy;
	}
}
