package monster.koke;

import static game.MyTool.*;

import java.util.LinkedList;
import java.util.List;

import cell.Cell;
import game.Game;
import monster.Monster;
import monster.Species;

public class Tsubomi extends Monster {
	protected int absorbCount = 0;

	public Tsubomi(int xIndex, int yIndex, int yobun, int mabun, Game game) {
		super(xIndex, yIndex, yobun, mabun, game);

		setSpecies(Species.TSUBOMI);
		setHP(21);
		setMaxHP(21);
	}

	@Override
	public void update() {
		absorbCount = (absorbCount + 1) % 30;
		if (absorbCount % 15 == 0) {
			List<Cell> list = getEatbleCellList();
			if (!list.isEmpty()) {
				int n = getRnd(0, list.size());
				eat(list.get(n));
			}
		}
		if (absorbCount % 10 == 0) {
			minusHP(2);
		}
		checkDeath();
		changeImage();
		growth();
	}

	// これの後にcheckDeathをやると養分が増えてしまうので注意
	@Override
	protected void growth() {
		if (getYobun() >= 8 && !isDeath()) {
			int xIndex = getXIndex(), yIndex = getYIndex();
			Game game = getGame();
			Hana hana = new Hana(xIndex, yIndex, getYobun(), getMabun(), game);
			game.addMonster(hana);
			game.getCell(xIndex, yIndex).addMonster(hana);
			delete();
		}
	}

	protected void eat(Cell cell) {
		cell.minusYobun(1);
		plusYobun(1);
	}

	@Override
	// 自分がお腹空いてるかどうか
	protected boolean isHungry() {
		return true;
	}

	// 養分吸収可能なセルのリストを返す
	protected List<Cell> getEatbleCellList() {
		int xIndex = getXIndex(), yIndex = getYIndex();
		List<Cell> list = new LinkedList<Cell>();
		Game game = getGame();
		Cell cell = null;
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				if ( (i != 0 || j != 0) &&
						game.inside(xIndex + i, yIndex + j) &&
						(cell = game.getCell(xIndex + i, yIndex + j)).getYobun() > 0 &&
						cell.getYobun() < 10) {// && !cell.isBroken()) { // 壊れている土の処理をどうするか
					list.add(cell);
				}
			}
		}
		return list;
	}

	@Override
	protected void changeImage() {
		img = getGame().getCanvas().getImages().getTsubomiImage(0);
	}
}
