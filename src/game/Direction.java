package game;

public enum Direction {
	NONE(-1), UP(0), RIGHT(1), DOWN(2), LEFT(3);

	private final int id, xd, yd;

	private Direction(final int id) {
		this.id = id;

		switch (id) {
		case 0: xd =  0; yd = -1; break; // 上
		case 1: xd =  1; yd =  0; break; // 右
		case 2: xd =  0; yd =  1; break; // 下
		case 3: xd = -1; yd =  0; break; // 左
		default: xd = Integer.MAX_VALUE; yd = Integer.MAX_VALUE; // 動かないやつ用(蕾,花)
		}
	}

	public int getId() {
		return id;
	}

	// 逆方向を返す
	public Direction getReverseDirection() {
		switch (this) {
		case UP:	return DOWN;
		case RIGHT:	return LEFT;
		case DOWN:	return UP;
		default:	return RIGHT;
		}
	}

	public int getXDirection() {
		return xd;
	}

	public int getYDirection() {
		return yd;
	}
}
