package de.tobias.snake2.scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Font;

/**
 * Klasse für die Game Over Szene.
 *
 * @author tobias
 */
public class GameOverScene extends BasicGameState {

	// Vordergrund Bild
	private Image image;
	// Hintergrung Bild (Letzter Frame des Games)
	private Image bg;

	private Font font = new Font("Sans serif", Font.BOLD, 50);
	private TrueTypeFont f = new TrueTypeFont(font, true);

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		image = new Image("assets/gameover.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if (bg != null) {
			g.drawImage(bg, 0, 0);
		}
		g.drawImage(image, 0, 0);

		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("Inken-Modus: Druecke I", (int) (container.getWidth() * 0.5 - g.getFont().getWidth("Inken-Modus: Druecke I") * 0.5),
				(int) (container.getHeight() * 0.75));
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// Neues Spiel
		if (container.getInput().isKeyDown(Input.KEY_N)) {
			GameScene scene = (GameScene) game.getState(1);
			scene.preInit();
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
			scene.setEasy(false);
		} else if (container.getInput().isKeyDown(Input.KEY_I)) {
			GameScene scene = (GameScene) game.getState(1);
			scene.preInit();
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
			scene.setEasy(true);
		}
	}

	@Override
	public int getID() {
		return 2;
	}

	public void setBg(Image bg) {
		this.bg = bg;
	}

}
