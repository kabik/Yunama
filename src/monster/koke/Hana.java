package monster.koke;

import static game.MyTool.*;

import java.util.List;

import cell.Cell;
import game.Game;
import monster.Species;

public class Hana extends Tsubomi {
	public Hana(int xIndex, int yIndex, int yobun, int mabun, Game game) {
		super(xIndex, yIndex, yobun, mabun, game);

		setMaxYobun(11);
		setHP(21);
		setSpecies(Species.HANA);
	}

	@Override
	public void update() {
		absorbCount = (absorbCount + 1) % 10;
		if (absorbCount == 0) {
			if (isHungry()) {
				List<Cell> list = getEatbleCellList();
				if (!list.isEmpty()) {
					int n = getRnd(0, list.size());
					eat(list.get(n));
				}
			}
			minusHP(2);
		}
		changeImage();
		if (getHP() <= 0) {
			growth();
		}
	}

	@Override
	protected boolean isHungry() {
		return getYobun() < getMaxYobun();
	}

	@Override
	// 繁殖する
	protected void growth() {
		int xIndex = getXIndex(), yIndex = getYIndex();
		Game game = getGame();
		Cell cell = game.getCell(xIndex, yIndex);
		int yobun = getYobun(), mabun = getMabun();
		for (int i = 5; i > 0; i--) {
			Koke koke = new Koke(xIndex, yIndex, yobun/i, mabun/i, game);
			game.addMonster(koke);
			cell.addMonster(koke);
			yobun -= yobun/i; mabun -= mabun/i;
		}
		delete();
	}

	@Override
	protected void changeImage() {
		img = getGame().getCanvas().getImages().getHanaImage(0);
	}

}
