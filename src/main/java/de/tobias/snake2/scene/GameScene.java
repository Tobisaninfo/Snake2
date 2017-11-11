package de.tobias.snake2.scene;

import de.tobias.snake2.Snake;
import de.tobias.snake2.game.Player;
import de.tobias.snake2.game.World;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import static de.tobias.snake2.Snake.HEIGHT;
import static de.tobias.snake2.Snake.WIDTH;

public class GameScene extends BasicGameState {

	private boolean pause;
	private Image pauseImage;

	private Image buffer;

	private World world;
	private Player player;

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		return player;
	}

	// Erstellt die Gamekomponenten
	public GameScene() throws SlickException {
		preInit();
	}

	public void preInit() throws SlickException {
		pauseImage = new Image("assets/pause.png");
		buffer = new Image(Snake.WIDTH, Snake.HEIGHT);

		world = new World();
		player = new Player(this);

		world.init(this);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// Rendert das Spiel erst auf ein Zwischenbild
		buffer.getGraphics().clear();

		world.render(gc, buffer.getGraphics());
		player.render(gc, buffer.getGraphics());

		buffer.flushPixelData();

		g.drawImage(buffer, 0, 0);

		if (pause) {
			g.drawImage(pauseImage, WIDTH / 2 - pauseImage.getWidth() / 2, HEIGHT / 2 - pauseImage.getHeight() / 2);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		// Pause ändern
		if (!gc.hasFocus()) // Automatisch
			pause = true;
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
			pause = !pause;
		if (gc.getInput().isKeyPressed(Input.KEY_N))
			init(gc, game);
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
			pause = false;

		// Game updaten
		if (!pause) {
			world.update(gc, delta);
			player.update(gc, delta);
		}

		// Game Over
		if (!player.getSnake().isAlive()) {
			((GameOverScene) game.getState(2)).setBg(buffer);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
	}

	public void setEasy(boolean easy) {
		player.setEasy(easy);
		world.setEasy(easy);
	}

	@Override
	public int getID() {
		return 1;
	}
}
