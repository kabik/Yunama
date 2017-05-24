package monster;

import static game.MyTool.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

import cell.YMContainer;
import game.Direction;
import game.Game;

public abstract class Monster extends YMContainer {
	private int HP;
	private int maxHP;
	private int v; // 速さ (継承したクラスで定義が必要)
	private int beforeXIndex, beforeYIndex;
	private int[][] priority = { // 養分,魔分をばらまくときの周囲のマスの優先度
			{ 0, 1},
			{-1, 1},
			{ 1, 1},
			{-1, 0},
			{ 1, 0},
			{ 0,-1},
			{-1,-1},
			{ 1,-1}, // 1周
			{ 0, 2},
			{-1, 2},
			{ 1, 2},
			{-2, 2},
			{ 2, 2},
			{-2, 1},
			{ 2, 1},
			{-2, 0},
			{ 2, 0},
			{-2,-1},
			{ 2,-1},
			{ 0,-2},
			{-1,-2},
			{ 1,-2},
			{-2,-2},
			{ 2,-2}, // 2周
			{ 0, 3},
			{-1, 3},
			{ 1, 3},
			{-2, 3},
			{ 2, 3},
			{-3, 3},
			{ 3, 3},
			{-3, 2},
			{ 3, 2},
			{-3, 1},
			{ 3, 1},
			{-3, 0},
			{ 3, 0},
			{-3,-1},
			{ 3,-1},
			{-3,-2},
			{ 3,-2},
			{ 0,-3},
			{-1,-3},
			{ 1,-3},
			{-2,-3},
			{ 2,-3},
			{-3,-3},
			{ 3,-3}, // 3周
	};

	private boolean isDeath;
	private Direction direction; // 向き (動いている向き）
	protected Image img;

	public abstract void update();
	protected abstract boolean isHungry();	// 自分がお腹空いてるかどうか
	protected abstract void changeImage();
	protected abstract void growth();

	protected Monster(int xIndex, int yIndex, int yobun, int mabun, Game game) {
		super(xIndex, yIndex, game.getCellSize(), game);
		setBeforeXIndex(xIndex);
		setBeforeYIndex(yIndex);
		setYobun(yobun);
		setMabun(mabun);
	}

	public void updateIndex() {
		getGame().getCell(getXIndex(), getYIndex()).getMonsterList().remove(this);

		int x = getX();
		int y = getY();
		int size = getSize();
		setXIndex( (x + size / 2) / size);
		setYIndex( (y + size / 2) / size);

		getGame().getCell(getXIndex(), getYIndex()).getMonsterList().add(this);
	}

	public void checkDeath() {
		if (getHP() <= 0 && !isDeath()) {
			scatterYobun();
			scatterMabun();
			delete();
		}
	}

	protected void delete() {
		getGame().deleteMonster(this);
		getGame().getCell(getXIndex(), getYIndex()).deleteMonster(this);
		getGame().getDetail().deleteYM(this);
		death();
	}

	public void death() {
		isDeath = true;
	}

	// 養分をばらまく
	protected void scatterYobun() {
		int n = Math.min(getYobun(), 48);
		int xIndex = getXIndex();
		int yIndex = getYIndex();
		for (int i = 0; i < n; i++) {
			scatterYobun(xIndex + priority[i][0], yIndex + priority[i][1]);
		}
	}

	// cell[xIndex][yIndex]に養分を1渡す
	protected void scatterYobun(int xIndex, int yIndex) {
		if (getGame().inside(xIndex, yIndex)) {
			getGame().getCell(xIndex, yIndex).plusYobun(1);
		}
	}

	// 魔分をばらまく
	protected void scatterMabun() {
		int n = getMabun();
		int xIndex = getXIndex();
		int yIndex = getYIndex();
		for (int i = 0; i < n; i++) {
			scatterMabun(xIndex + priority[i][0], yIndex + priority[i][1]);
		}
	}

	// cell[xIndex][yIndex]に魔分を1渡す
	protected void scatterMabun(int xIndex, int yIndex) {
		if (getGame().inside(xIndex, yIndex)) {
			getGame().getCell(xIndex, yIndex).plusMabun(1);
		}
	}

	protected void move() {
		if (isMoving() || canMoveStraight()) {
			decideDirection();
		} else {
			// 移動する
			setX( getX() + getDirection().getXDirection() * getV() );
			setY( getY() + getDirection().getYDirection() * getV() );
			updateIndex();
		}
	}

	// 上に行けるかどうか
	protected boolean canMoveUp() {
		return getGame().getCell( getXIndex(), getYIndex()-1).isBroken();
	}

	// 下に行けるかどうか
	protected boolean canMoveDown() {
		return getGame().getCell(getXIndex(), getYIndex()+1).isBroken();
	}

	// 左に行けるかどうか
	protected boolean canMoveLeft() {
		return getGame().getCell(getXIndex()-1, getYIndex()).isBroken();
	}

	// 右に行けるかどうか
	protected boolean canMoveRight() {
		return getGame().getCell(getXIndex()+1, getYIndex()).isBroken();
	}

	// 直進可能かどうか
	protected boolean canMoveStraight() {
		if (getDirection() == Direction.UP) {
			return canMoveUp();
		} else if (getDirection() == Direction.RIGHT) {
			return canMoveRight();
		} else if (getDirection() == Direction.DOWN) {
			return canMoveDown();
		} else if (getDirection() == Direction.LEFT) {
			return canMoveLeft();
		} else {
			return false;
		}
	}

	// 移動中かどうか
	protected boolean isMoving() {
		int size = getSize();
		return (getX() % size != 0) || (getY() % size != 0);
	}

	// セルを移動したかどうか
	protected boolean isMoved() {
		return (getXIndex() != getBeforeXIndex()) || (getYIndex() != getBeforeYIndex());
	}

	// ランダムに方向を決める
	protected void decideDirection() {
		List<Direction> list = moveList();
		if (!list.isEmpty()) {
			int m = getRnd(0, list.size());
			setDirection(list.get(m));
		}
	}

	// 移動可能なリストを返す
	public List<Direction> moveList() {
		List<Direction> moveList = new LinkedList<Direction>();

		if ( canMoveUp() )    { moveList.add(Direction.UP); }
		if ( canMoveRight() ) { moveList.add(Direction.RIGHT); }
		if ( canMoveDown() )  { moveList.add(Direction.DOWN); }
		if ( canMoveLeft() )  { moveList.add(Direction.LEFT); }

		return moveList;
	}

	public void plusHP(int d) {
		setHP(Math.min(getHP() + d, getMaxHP()));
	}

	public void minusHP(int d) {
		setHP(Math.max(getHP() - d, 0));
	}

	public void paint(Graphics2D g) {
		int size = getSize();
		g.drawImage(getImage(), getX(), getY(), size, size, getGame().getCanvas());
	}

	/*===ゲッター===*/
	public int getHP() {
		return HP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getBeforeXIndex() {
		return beforeXIndex;
	}

	public int getBeforeYIndex() {
		return beforeYIndex;
	}

	public int getV() {
		return v;
	}

	public Direction getDirection() {
		return direction;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public Image getImage() {
		return img;
	}

	/*===セッター===*/
	public void setHP(int HP) {
		this.HP = HP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public void setBeforeXIndex(int beforeXIndex) {
		this.beforeXIndex = beforeXIndex;
	}

	public void setBeforeYIndex(int beforeYIndex) {
		this.beforeYIndex = beforeYIndex;
	}

	public void setV(int v) {
		this.v = v;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setImage(Image img) {
		this.img = img;
	}
}
