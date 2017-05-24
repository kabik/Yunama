package game;

import java.awt.Graphics2D;
import java.awt.Image;

import cell.Cell;

public class Pickel {
	private int x, y;
	private int xIndex, yIndex;
	private int size;
	private Image img;
	private Game game;

	Pickel(int xIndex, int yIndex, Game game) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.game = game;

		this.img = game.getCanvas().getImages().getPickelImage(0);
		this.size = game.getCellSize();

		updatePosition();
	}

	private void updatePosition() {
		x = xIndex * size;
		y = yIndex * size;
	}

	public void dig() {
		Cell cell = game.getCell(xIndex, yIndex);
		cell.dig();
		img = game.getCanvas().getImages().getPickelImage(1);
	}

	public void move(int dXIndex, int dYIndex) {
		if (game.inside(xIndex + dXIndex, yIndex + dYIndex)) {
			xIndex += dXIndex;
			yIndex += dYIndex;

			if (dXIndex != 0 && (game.isXIndexCenter(xIndex) || game.isXIndexCenter(xIndex-dXIndex))) {
				game.getCanvas().imageMove(dXIndex, 0);
			}
			if (dYIndex != 0 && (game.isYIndexCenter(yIndex) || game.isYIndexCenter(yIndex-dYIndex))) {
				game.getCanvas().imageMove(0, dYIndex);
			}
			
			updatePosition();
		}
	}

	public void moveUp() {
		move(0, -1);
	}

	public void moveDown() {
		move(0, 1);
	}

	public void moveLeft() {
		move(-1, 0);
	}

	public void moveRight() {
		move(1, 0);
	}

	public void paint(Graphics2D g) {
		g.drawImage(img, x, y, size, size, game.getCanvas());
		if (img == game.getCanvas().getImages().getPickelImage(1)) {
			img = game.getCanvas().getImages().getPickelImage(0);
		}
	}

	/*===ゲッター===*/
	public int getXIndex() {
		return xIndex;
	}

	public int getYIndex() {
		return yIndex;
	}
}
