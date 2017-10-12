package de.tobias.snake2.scene;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.tobias.snake2.Snake;

/**
 * Klasse für das Menu
 * 
 * @author tobias
 *
 */
public class MenuScene extends BasicGameState {

	// Hindergrund
	private Image image;

	private Font font = new Font("Sans serif", Font.BOLD, 50);
	private TrueTypeFont f = new TrueTypeFont(font, true);

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		image = new Image("assets/title.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(image, 0, 0);
		
		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("Inken-Modus: Druecke I", (int) (container.getWidth() * 0.5 - g.getFont().getWidth("Inken-Modus: Druecke I") * 0.5),
				(int) (container.getHeight() * 0.75));

		g.resetFont();
		g.setColor(Color.white);
		g.drawString("Highscore: " + Snake.getSettings().s_highScore, 0, 0);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// Game Start
		if (container.getInput().isKeyDown(Input.KEY_ENTER)) {
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
			GameScene scene = (GameScene) game.getState(1);
			scene.setEasy(false);
		} else if (container.getInput().isKeyDown(Input.KEY_I)) {
			game.enterState(1, new FadeOutTransition(), new FadeInTransition());
			GameScene scene = (GameScene) game.getState(1);
			scene.setEasy(true);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
