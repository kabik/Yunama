package monster.mushi;

import static game.MyTool.*;

import java.util.LinkedList;
import java.util.List;

import cell.Cell;
import game.Direction;
import game.Game;
import monster.Monster;
import monster.Species;

public class Mushi extends Monster {
	//private boolean ateOrDeposited;// 一瞬前に吸収or放出をしているか
	private int imgCount = 0;
	private int moveCount = 0;	// 3マス進むごとに体力-1

	public Mushi(int xIndex, int yIndex, int yobun, int mabun, Game game) {
		super(xIndex, yIndex, yobun, mabun, game);

		setSpecies(Species.MUSHI);
		setHP(23);
		setMaxHP(64);
		img = game.getCanvas().getImages().getMushiImage(0);
		setV(getSize() / 25); // 後で変える
		super.decideDirection();
	}

	@Override
	public void update() {
		changeImage();
		if (false) {
			//食べてるときや攻撃しているときの処理を書く
		} else {
			move();
			growth();
		}
	}

	@Override
	public void move() {
		decideDirection();

		// 移動する
		setX( getX() + getDirection().getXDirection() * getV() );
		setY( getY() + getDirection().getYDirection() * getV() );
		updateIndex();

		if (isMoved()) {
			moveCount = (moveCount+1) % 3;
			if (moveCount == 0) {
				minusHP(1);
				checkDeath();
			}
		}
		setBeforeXIndex(getXIndex());
		setBeforeYIndex(getYIndex());
	}

	@Override
	protected void growth() {
		/*if (!isMoving() && getYobun() >= 2 && getHP() <= 2) {
			int xIndex = getXIndex(), yIndex = getYIndex();
			Game game = getGame();
			game.getCell(xIndex, yIndex).addMonster(new Tsubomi(xIndex, yIndex,
					getYobun(), getMabun(), game));
			delete();
		}*/
	}

	// 捕食可能なコケのリストを返す
	protected List<Monster> getEatbleKokeList() {
		int xIndex = getXIndex(), yIndex = getYIndex();
		List<Monster> list = new LinkedList<Monster>();
		Game game = getGame();
		Monster monster;
		Cell cell;
		cell = game.getCell(xIndex, yIndex+1);
		/*
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
		 */
		return list;
	}

	public void eat(Monster monster) {
		/*int cellYobun = cell.getYobun();
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
		plusHP(4);*/
	}

	@Override
	// 自分がお腹空いてるかどうか
	public boolean isHungry() {
		return getHP() < 45;
	}

	@Override
	/*
	 * 進む方向を決める。
	 * L字路では必ず曲がるが、T字路だとランダム。1本道で折り返すことはない。
	 */
	protected void decideDirection() {
		if (!isMoving()) {
			List<Direction> list = moveList();
			list.remove(getDirection().getReverseDirection());
			if (!list.isEmpty()) {
				int m = getRnd(0, list.size());
				setDirection(list.get(m));
			} else {
				setDirection(getDirection().getReverseDirection());
			}
		}
	}

	@Override
	protected void changeImage() {
		imgCount = (imgCount + 1) % 12;
		img = getGame().getCanvas().getImages().getMushiImage(getDirection().getId() * 4 + imgCount / 3);
	}
}
