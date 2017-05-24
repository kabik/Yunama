package monster.koke;

import static game.MyTool.*;

import java.util.LinkedList;
import java.util.List;

import cell.Cell;
import game.Game;
import monster.Monster;
import monster.Species;

public class Koke extends Monster {
	private boolean ateOrDeposited;// 一瞬前に吸収or放出をしているか
	private int imgCount = 0;
	private int yobunDelayCount;

	public Koke(int xIndex, int yIndex, int yobun, int mabun, Game game) {
		super(xIndex, yIndex, yobun, mabun, game);

		setSpecies(Species.KOKE);
		setHP(16);
		setMaxHP(21);
		setAteOrDeposited(false);
		this.yobunDelayCount = 0;
		img = game.getCanvas().getImages().getKokeImage(0);
		setV(getSize() / 25);
		super.decideDirection();

		// これをしないと次のupdateで養分を吸収or放出してしまう。
		setAteOrDeposited(true);
	}

	@Override
	public void update() {
		List<Cell> eatList = getEatbleCellList();
		changeImage();
		growth();

		if (isDeath()) { return; }
		if (!eatList.isEmpty() && !isMoving() && !getAteOrDeposited()) {
			Cell cell = eatList.get(getRnd(0, eatList.size()));
			yobunDelay();
			if (getAteOrDeposited()) {
				if (isHungry()) {
					eat(cell);
				} else {
					deposit(cell);
				}
			}
		} else {
			move();
			setAteOrDeposited(false);
		}
	}

	@Override
	public void move() {
		decideDirection();

		// 移動する
		setX( getX() + getDirection().getXDirection() * getV() );
		setY( getY() + getDirection().getYDirection() * getV() );
		updateIndex();

		// 動いたらHPを1~2減らす
		if (isMoved()) {
			minusHP(getRnd(1,3));
			checkDeath();
		}

		setBeforeXIndex(getXIndex());
		setBeforeYIndex(getYIndex());
	}

	protected void yobunDelay() {
		yobunDelayCount = (yobunDelayCount + 1) % 10;
		setAteOrDeposited(yobunDelayCount == 0);
	}

	@Override
	protected void growth() {
		if (!isMoving() && getYobun() >= 2 && getHP() <= 2) {
			int xIndex = getXIndex(), yIndex = getYIndex();
			Game game = getGame();
			Tsubomi tsubomi = new Tsubomi(xIndex, yIndex, getYobun(), getMabun(), game);
			game.addMonster(tsubomi);
			game.getCell(xIndex, yIndex).addMonster(tsubomi);
			delete();
		}
	}

	// 養分吸収可能なセルのリストを返す
	protected List<Cell> getEatbleCellList() {
		int xIndex = getXIndex(), yIndex = getYIndex();
		List<Cell> list = new LinkedList<Cell>();
		Game game = getGame();
		Cell cell = null;
		if ( (cell = game.getCell(xIndex, yIndex+1)).getYobun() > 0 && !cell.isBroken()) { //下
			list.add(cell);
		}
		if ( (cell = game.getCell(xIndex-1, yIndex)).getYobun() > 0 && !cell.isBroken()) { //左
			list.add(cell);
		}
		if ( (cell = game.getCell(xIndex+1, yIndex)).getYobun() > 0 && !cell.isBroken()) { //右
			list.add(cell);
		}
		if ( (cell = game.getCell(xIndex, yIndex-1)).getYobun() > 0 && !cell.isBroken()) { //上
			list.add(cell);
		}
		return list;
	}

	public void deposit(Cell cell) {
		cell.plusYobun(this.getYobun()-1);
		this.setYobun(1);
	}

	public void eat(Cell cell) {
		int cellYobun = cell.getYobun();
		int d;
		if (cellYobun > 3) {
			d = 3;
		} else if (cellYobun > 1) {
			d = cellYobun - 1;
		} else {
			d = 1;
		}
		cell.minusYobun(d);
		plusYobun(d);
		plusHP(4);
	}

	@Override
	// 自分がお腹空いてるかどうか
	public boolean isHungry() {
		return getYobun() == 1;
	}

	@Override
	// 移動不可能だったら向きを変える
	protected void decideDirection() {
		if (!isMoving() && !canMoveStraight()) {
			super.decideDirection();
		}
	}

	@Override
	protected void changeImage() {
		imgCount = (imgCount + 1) % 20;
		img = getGame().getCanvas().getImages().getKokeImage(imgCount / 5);
	}

	public boolean getAteOrDeposited() {
		return ateOrDeposited;
	}

	public void setAteOrDeposited(boolean b) {
		ateOrDeposited = b;
	}
}
