package cell;

import java.awt.Graphics2D;

import game.Game;

public class Wall extends Cell {
	public Wall(int xIndex, int yIndex, int size, Game game) {
		super(xIndex, yIndex, size, game);

		setYobun(0);
		setMabun(0);
		setBroken(false);
	}

	@Override
	public void dig() {
		// 壊せないようにした
	}

	public void paint(Graphics2D g) {
		int x = getX();
		int y = getY();
		int size = getSize();
		Game game = getGame();
		g.drawImage(game.getCanvas().getImages().getWallImage(), x, y, size, size, game.getCanvas());
	}
}
