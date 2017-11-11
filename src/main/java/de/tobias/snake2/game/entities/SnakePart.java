package de.tobias.snake2.game.entities;

import de.tobias.snake2.Content;
import de.tobias.snake2.Snake;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Klasse für ein Schlangenteil.
 *
 * @author tobias
 */
public class SnakePart extends Content {

	/**
	 * Position des Schlangenteil
	 */
	private Rectangle rectangle;
	/**
	 * Position des letzten Schlangenteils
	 */
	private Rectangle previousPart;
	/**
	 * Refferenz zum nächsten Schlangenteil
	 */
	private SnakePart nextPart;

	/**
	 * Refferenz zum Schlangenkopf
	 */
	private SnakeHead head;

	private boolean removed;
	private boolean easy;

	private int index;

	public SnakePart(SnakeHead head, Rectangle previousPart, float lastX, float lastY, int index) {
		this.head = head;
		this.index = index;
		this.previousPart = previousPart;
		this.rectangle = new Rectangle(lastX, lastY, Snake.TILEWIDTH, Snake.TILEHEIGHT);
	}

	public void move(float x, float y) {
		if (nextPart != null)
			nextPart.move(rectangle.getX(), rectangle.getY());
		this.rectangle.setLocation(x, y);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void addPart() {
		if (nextPart != null)
			nextPart.addPart();
		else {
			nextPart = new SnakePart(head, rectangle, rectangle.getX(), rectangle.getY(), index + 1);
			nextPart.setEasy(easy);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (nextPart != null) {
			nextPart.render(gc, g);
		}

		if (head.isAlive())
			g.setColor(Color.red);
		else
			g.setColor(Color.white);

		// Rundung am Ende
		if (nextPart == null) {
			g.fillOval(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT);
			if (isAbove(previousPart)) {
				g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
			} else if (isBelow(previousPart)) {
				g.fillRect(rectangle.getX(), rectangle.getY() + Snake.TILEHEIGHT / 2, Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
			} else if (isLeft(previousPart)) {
				g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
			} else if (isRight(previousPart)) {
				g.fillRect(rectangle.getX() + Snake.TILEWIDTH / 2, rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
			}
			return;
		}

		Rectangle nextPart = this.nextPart.getRectangle();

		if (nextPart != null && previousPart != null) {
			// Durchgehendes Teil (Mittendrinn)
			if (isAbove(nextPart) && isBelow(previousPart) || isBelow(nextPart) && isAbove(previousPart)) {
				g.fill(rectangle);
			} else if (isLeft(nextPart) && isRight(previousPart) || isRight(nextPart) && isLeft(previousPart)) {
				g.fill(rectangle);

				// Von Oben nach Links oder Rechts abbiegen
			} else if (isAbove(nextPart)) {
				g.fillOval(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT);
				if (isLeft(previousPart)) {
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				} else if (isRight(previousPart)) {
					g.fillRect(rectangle.getX() + Snake.TILEWIDTH / 2, rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				}
				// Von Unten nach Links oder Rechts abbiegen
			} else if (isBelow(nextPart)) {
				g.fillOval(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT);
				if (isLeft(previousPart)) {
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY() + Snake.TILEHEIGHT / 2, Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				} else if (isRight(previousPart)) {
					g.fillRect(rectangle.getX() + Snake.TILEWIDTH / 2, rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY() + Snake.TILEHEIGHT / 2, Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				}
				// Von links nach oben oder unten abbiegen
			} else if (isLeft(nextPart)) {
				g.fillOval(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT);
				if (isAbove(previousPart)) {
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				} else if (isBelow(previousPart)) {
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY() + Snake.TILEHEIGHT / 2, Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				}
			} else if (isRight(nextPart)) {
				g.fillOval(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT);
				if (isAbove(previousPart)) {
					g.fillRect(rectangle.getX() + Snake.TILEWIDTH / 2, rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY(), Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				} else if (isBelow(previousPart)) {
					g.fillRect(rectangle.getX() + Snake.TILEWIDTH / 2, rectangle.getY(), Snake.TILEWIDTH / 2, Snake.TILEHEIGHT);
					g.fillRect(rectangle.getX(), rectangle.getY() + Snake.TILEHEIGHT / 2, Snake.TILEWIDTH, Snake.TILEHEIGHT / 2);
				}
			} else {
				g.fill(rectangle);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// Kollision mit dem Kopf
		if (head.getRectangle().getX() == getRectangle().getX() && head.getRectangle().getY() == getRectangle().getY()) {
			if (!easy)
				head.setAlive(false);
			else {
				removed = true;
			}
		}

		if (!removed) {
			if (nextPart != null) {
				nextPart.update(gc, delta);
			}
		} else {
			if (nextPart != null) {
				remove();
				head.getPlayer().setScore(head.getPlayer().getScore() / 2);
			} else {
				removed = false;
			}
		}
	}

	public void remove() {
		if (nextPart != null) {
			nextPart.remove();
			nextPart = null;
		}

		removed = false;
	}

	/**
	 * Part ist unter aktuellem Part (Nach Unten)
	 *
	 * @param part Test Objekt
	 * @return
	 */
	private boolean isAbove(Rectangle part) {
		return (rectangle.getY() > part.getY());
	}

	/**
	 * Part ist Über aktuellem Part (Nach Oben)
	 *
	 * @param part Test Objekt
	 * @return
	 */
	private boolean isBelow(Rectangle part) {
		return (rectangle.getY() < part.getY());
	}

	/**
	 * Part ist Links aktuellem Part (Nach Links)
	 *
	 * @param part Test Objekt
	 * @return
	 */
	private boolean isLeft(Rectangle part) {
		return (rectangle.getX() > part.getX());
	}

	private boolean isRight(Rectangle part) {
		return (rectangle.getX() < part.getX());
	}

	public void setNextPart(SnakePart nextPart) {
		this.nextPart = nextPart;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setEasy(boolean easy) {
		this.easy = easy;
		if (nextPart != null) {
			nextPart.setEasy(easy);
		}
	}
}
