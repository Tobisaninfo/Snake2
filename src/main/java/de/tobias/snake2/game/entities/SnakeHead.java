package de.tobias.snake2.game.entities;

import de.tobias.snake2.Content;
import de.tobias.snake2.Snake;
import de.tobias.snake2.game.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;

import static de.tobias.snake2.Snake.*;

/**
 * Klasse für den Schlangenkopf. Sie enthällt Informationen über das erste Körperteil.
 *
 * @author tobias
 */
public class SnakeHead extends Content {

	/**
	 * Richtung der Schlange
	 *
	 * @author tobias
	 */
	public enum Direction {
		UP,
		RIGHT,
		DOWN,
		LEFT;
	}

	// Assets
	private static Sound eatSound;
	private static Sound scoreSound;
	private static Sound highScoreSound;
	private static Sound deathSound;

	// Laden der Resourcen
	static {
		try {
			eatSound = new Sound("assets/eat1.ogg");
			scoreSound = new Sound("assets/orb.ogg");
			highScoreSound = new Sound("assets/levelup.ogg");
			deathSound = new Sound("assets/Fail2.wav");
		} catch (SlickException e) {
			System.err.println("Can't load resourcefiles: " + e.getLocalizedMessage());
		}
	}

	// Steuerung
	private static final int KEY_LEFT = Input.KEY_A;
	private static final int KEY_UP = Input.KEY_W;
	private static final int KEY_RIGHT = Input.KEY_D;
	private static final int KEY_DOWN = Input.KEY_S;

	// Refferenzren
	private final List<Barrier> barriers;
	private final Player player;

	// Schlange
	private SnakePart nextPart;
	private final Rectangle rect;

	// Spielzustand
	private boolean alive;
	private int lifeCount;

	private boolean easy;

	// Bewegung
	private int moveDelay;
	private Direction direction;

	private int startX;
	private int startY;

	public SnakeHead(List<Barrier> barriers, Player player, int tileX, int tileY) throws SlickException {
		this.barriers = barriers;
		this.player = player;

		this.startX = tileX;
		this.startY = tileY;

		if (easy) {
			lifeCount = 2;
		} else {
			lifeCount = 1;
		}

		alive = true;
		direction = Direction.DOWN;
		this.rect = new Rectangle(tileX * TILEWIDTH, tileY * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
	}

	public Rectangle getRectangle() {
		return rect;
	}

	public void addPart() {
		// Es gibt bereits einen Part
		if (nextPart != null) {
			nextPart.addPart();
		} else {
			// Der erste Part wird anhefügt
			if (direction == Direction.DOWN) {
				nextPart = new SnakePart(this, rect, rect.getX(), rect.getY() - TILEHEIGHT, 1);
			} else if (direction == Direction.UP) {
				nextPart = new SnakePart(this, rect, rect.getX(), rect.getY() + TILEHEIGHT, 1);
			} else if (direction == Direction.LEFT) {
				nextPart = new SnakePart(this, rect, rect.getX() + TILEWIDTH, rect.getY(), 1);
			} else if (direction == Direction.RIGHT) {
				nextPart = new SnakePart(this, rect, rect.getX() - TILEWIDTH, rect.getY(), 1);
			}
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		lifeCount--;

		System.out.println(alive);
		if (lifeCount == 0) {
			this.alive = alive;

			// Tot
			if (!alive) {
				deathSound.play();
				// Highscore aktualiesieren
				if (Snake.getSettings().s_highScore < player.getScore()) {
					Snake.getSettings().s_highScore = player.getScore();
				}
			}
		} else {
			respawn();
		}
	}

	public void respawn() {
		alive = true;
		direction = Direction.DOWN;
		rect.setBounds(startX * TILEWIDTH, startY * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (nextPart != null) {
			nextPart.render(gc, g);
		}

		if (alive)
			g.setColor(Color.white);
		else
			g.setColor(Color.black);

		g.fillOval(rect.getX(), rect.getY(), TILEWIDTH, TILEHEIGHT);
		if (direction == Direction.LEFT) {
			g.fillRect(rect.getX() + Snake.TILEWIDTH / 2, rect.getY(), TILEWIDTH / 2, TILEHEIGHT);
		} else if (direction == Direction.RIGHT) {
			g.fillRect(rect.getX(), rect.getY(), TILEWIDTH / 2, TILEHEIGHT);
		} else if (direction == Direction.UP) {
			g.fillRect(rect.getX(), rect.getY() + TILEHEIGHT / 2, TILEWIDTH, TILEHEIGHT / 2);
		} else if (direction == Direction.DOWN) {
			g.fillRect(rect.getX(), rect.getY(), TILEWIDTH, TILEHEIGHT / 2);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (alive) {
			// Schlangenkörper updaten
			nextPart.update(gc, delta);

			Input input = gc.getInput();
			// Richtung ändern
			if (input.isKeyDown(KEY_LEFT) && direction != Direction.RIGHT)
				direction = Direction.LEFT;
			else if (input.isKeyDown(KEY_UP) && direction != Direction.DOWN)
				direction = Direction.UP;
			else if (input.isKeyDown(KEY_DOWN) && direction != Direction.UP)
				direction = Direction.DOWN;
			else if (input.isKeyDown(KEY_RIGHT) && direction != Direction.LEFT)
				direction = Direction.RIGHT;

			// Wandkollision
			if (!easy) {
				if (getRectangle().getX() / TILEWIDTH >= TILECOUNTX)
					setAlive(false);
				else if (getRectangle().getX() / TILEWIDTH < 0)
					setAlive(false);
				else if (getRectangle().getY() / TILEWIDTH >= TILECOUNTY)
					setAlive(false);
				else if (getRectangle().getY() / TILEWIDTH < 0)
					setAlive(false);
			} else {
				if (getRectangle().getX() / TILEWIDTH >= TILECOUNTX) {
					rect.setBounds(0, rect.getY(), TILEWIDTH, TILEHEIGHT);
				} else if (getRectangle().getX() / TILEWIDTH < 0) {
					rect.setBounds((TILECOUNTX - 1) * TILEWIDTH, rect.getY(), TILEWIDTH, TILEHEIGHT);
				} else if (getRectangle().getY() / TILEWIDTH >= TILECOUNTY) {
					rect.setBounds(rect.getX(), 0, TILEWIDTH, TILEHEIGHT);
				} else if (getRectangle().getY() / TILEWIDTH < 0) {
					rect.setBounds(rect.getX(), (TILECOUNTY - 1) * TILEHEIGHT, TILEWIDTH, TILEHEIGHT);
				}
			}

			// Hindernisse Kollision
			for (int i = 0; i < barriers.size(); i++)
				if (barriers.get(i).getRect().intersects(rect))
					setAlive(false);

			// Movethreashold
			moveDelay += delta;

			if (moveDelay >= Snake.getSettings().s_moveThreshold) {
				move();
				moveDelay -= Snake.getSettings().s_moveThreshold;
			}
		}
	}

	public void eat() {
		player.setScore(player.getScore() + 1);

		// Neuer Highscore
		if (Snake.getSettings().s_highScore == player.getScore() - 1 && Snake.getSettings().s_highScore != 0)
			highScoreSound.play();
		if (player.getScore() % 5 == 0) // Highscore (5x)
			scoreSound.play();
		else
			eatSound.play(); // Normaler Eat Sound

		// Neue Parts Anhängen
		for (int i = 0; i < Snake.getSettings().s_addLengthThenEat; i++)
			addPart();
	}

	private void move() {
		if (nextPart != null)
			// Nächster Teil bekommt die Position des Aktuellen Teils
			nextPart.move(rect.getX(), rect.getY());

		// Bewegen
		if (direction == Direction.DOWN)
			rect.setY(rect.getY() + Snake.TILEHEIGHT);
		else if (direction == Direction.UP)
			rect.setY(rect.getY() - Snake.TILEHEIGHT);
		else if (direction == Direction.LEFT)
			rect.setX(rect.getX() - Snake.TILEWIDTH);
		else if (direction == Direction.RIGHT)
			rect.setX(rect.getX() + Snake.TILEWIDTH);
	}

	public void addLife() {
		this.lifeCount++;
	}

	public int getLifeCount() {
		return lifeCount;
	}

	public void setEasy(boolean easy) {
		this.easy = easy;
		if (easy) {
			lifeCount = 2;
		} else {
			lifeCount = 1;
		}
		if (nextPart != null)
			nextPart.setEasy(easy);
	}

	public Player getPlayer() {
		return player;
	}
}