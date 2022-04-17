package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Unit implements KeyListener {

	public enum Movement {
		UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1), UP_LEFT(1, 1), UP_RIGHT(1, 1), DOWN_LEFT(1, 1), DOWN_RIGHT(1,
				1), STATIONARY(0, 0);

		public final double x;
		public final double y;

		private Movement(double y, double x) {
			this.y = y;
			this.x = x;
		}

	}

	public Movement direction;

	public Player(double x, double y, double hp, double s, double dmg, double r, String n) {
		super(x, y, hp, s, dmg, r, n);
		direction = Movement.STATIONARY;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			switch (direction) {
			case STATIONARY:
			case UP:
			case DOWN:
				direction = Movement.UP;
				break;
			case LEFT:
			case DOWN_LEFT:
			case UP_LEFT:
				direction = Movement.UP_LEFT;
				break;
			case RIGHT:
			case DOWN_RIGHT:
			case UP_RIGHT:
				direction = Movement.UP_RIGHT;
				break;
			}
		} else if (e.getKeyChar() == 'a') {
			switch (direction) {
			case DOWN:
			case DOWN_LEFT:
			case DOWN_RIGHT:
				direction = Movement.DOWN_LEFT;
				break;
			case STATIONARY:
			case RIGHT:
			case LEFT:
				direction = Movement.LEFT;
				break;
			case UP:
			case UP_LEFT:
			case UP_RIGHT:
				direction = Movement.UP_LEFT;
				break;
			}
		} else if (e.getKeyChar() == 's') {
			switch (direction) {
			case DOWN:
			case STATIONARY:
			case UP:
				direction = Movement.DOWN;
				break;
			case DOWN_LEFT:
			case LEFT:
			case UP_LEFT:
				direction = Movement.DOWN_LEFT;
				break;
			case DOWN_RIGHT:
			case RIGHT:
			case UP_RIGHT:
				direction = Movement.DOWN_RIGHT;
				break;
			}
		} else if (e.getKeyChar() == 'd') {
			switch (direction) {
			case DOWN:
			case DOWN_LEFT:
			case DOWN_RIGHT:
				direction = Movement.DOWN_RIGHT;
				break;
			case LEFT:
			case RIGHT:
			case STATIONARY:
				direction = Movement.RIGHT;
				break;
			case UP:
			case UP_LEFT:
			case UP_RIGHT:
				direction = Movement.UP_RIGHT;
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'w') {
			switch (direction) {
			case UP:
				direction = Movement.STATIONARY;
				break;
			case UP_LEFT:
				direction = Movement.LEFT;
				break;
			case UP_RIGHT:
				direction = Movement.RIGHT;
				break;
			default:
				// no action
				break;
			}
		} else if (e.getKeyChar() == 'a') {
			switch (direction) {
			case DOWN_LEFT:
				direction = Movement.DOWN;
				break;
			case LEFT:
				direction = Movement.STATIONARY;
				break;
			case UP_LEFT:
				direction = Movement.UP;
				break;
			default:
				// no action
				break;
			}
		} else if (e.getKeyChar() == 's') {
			switch (direction) {
			case DOWN:
				direction = Movement.STATIONARY;
				break;
			case DOWN_LEFT:
				direction = Movement.LEFT;
				break;
			case DOWN_RIGHT:
				direction = Movement.RIGHT;
				break;
			default:
				// no action
				break;
			}
		} else if (e.getKeyChar() == 'd') {
			switch (direction) {
			case DOWN_RIGHT:
				direction = Movement.DOWN;
				break;
			case RIGHT:
				direction = Movement.STATIONARY;
				break;
			case UP_RIGHT:
				direction = Movement.UP;
				break;
			default:
				// no action
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
