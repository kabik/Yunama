package cell;

import game.Game;
import monster.Species;

public abstract class YMContainer {
	private Species species; // 種族

	private int xIndex, yIndex;
	private int x, y;
	private int size;
	private int mabun, yobun; // 魔分, 養分
	private int maxYobun, maxMabun;
	private Game game;

	protected YMContainer(int xIndex, int yIndex, int size, Game game) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.size = size;
		this.game = game;

		this.x = xIndex * size;
		this.y = yIndex * size;
	}

	public void plusYobun(int d) {
		setYobun(getYobun() + d);
	}

	public void minusYobun(int d) {
		setYobun(getYobun() - d);
	}

	public void plusMabun(int d) {
		setMabun(getMabun() + d);
	}

	public void minusMabun(int d) {
		setMabun(getMabun() - d);
	}

	/*===ゲッター===*/
	public Species getSpecies() {
		return species;
	}

	public String getName() {
		return getSpecies().getName();
	}

	public int getXIndex() {
		return xIndex;
	}

	public int getYIndex() {
		return yIndex;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public int getYobun() {
		return yobun;
	}

	public int getMabun() {
		return mabun;
	}

	public int getMaxYobun() {
		return maxYobun;
	}

	public int getMaxMabun() {
		return maxMabun;
	}

	public Game getGame() {
		return game;
	}

	/*===セッター=== */
	public void setSpecies(Species species) {
		this.species = species;
	}

	public void setXIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public void setYIndex(int yIndex) {
		this.yIndex = yIndex;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setYobun(int yobun) {
		this.yobun = yobun;
	}

	public void setMabun(int mabun) {
		this.mabun = mabun;
	}

	public void setMaxYobun(int maxYobun) {
		this.maxYobun = maxYobun;
	}

	public void setMaxMabun(int maxMabun) {
		this.maxMabun = maxMabun;
	}
}
