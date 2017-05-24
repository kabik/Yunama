package cell;

import static game.MyTool.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import game.Game;
import monster.Monster;
import monster.Species;
import monster.koke.Koke;
import monster.mushi.Mushi;

public class Cell extends YMContainer {
	private boolean broken;
	private List<Monster> monsterList = new  LinkedList<Monster>();

	public Cell(int xIndex, int yIndex, int size, Game game) {
		super(xIndex, yIndex, size, game);

		setSpecies(Species.TSUCHI);
		setYobun(getRnd(0, 5));
		setMabun(getRnd(0, 5));
		setBroken(false);
	}

	public void dig() {
		if (!isBroken() && isBreakable()) {
			setBroken(true);
			getGame().getDetail().deleteYM(this);
			int yobun = getYobun();
			if (yobun > 0) {
				if (yobun < 10 ) {
					Koke koke = new Koke(getXIndex(), getYIndex(), yobun, getMabun(), getGame());
					getGame().addMonster(koke);
					addMonster(koke);
				} else if (yobun >= 10) {
					Mushi mushi = new Mushi(getXIndex(), getYIndex(), yobun, getMabun(), getGame());
					getGame().addMonster(mushi);
					addMonster(mushi);
				}
				setYobun(0);
				setMabun(0);
			}
		} else if (!getMonsterList().isEmpty()) {
			//ピッケルで突いたらHPが減る
			Monster monster = getMonsterList().get(0);
			monster.minusHP(monster.getMaxHP()/3);
			monster.checkDeath();
		}
	}

	public void addMonster(Monster monster) {
		monsterList.add(monster);
	}

	public void deleteMonster(Monster monster) {
		monsterList.remove(monster);
	}

	public void monsterUpdate() {
		for (int i = 0; i < getMonsterList().size(); i++) {
			getMonsterList().get(i).update();
		}
	}

	public void paint(Graphics2D g) {
		// セル描画
		if (!isBroken()) {
			int size = getSize();
			Game game = getGame();
			g.drawImage(game.getCanvas().getImages().getCellImage(0), getX(), getY(), size, size, game.getCanvas());
		}

		// 詳細描画
		///*
		if (getYobun() > 0) {
			g.setColor(Color.green);
		} else {
			g.setColor(Color.white);
		}
		g.setFont(new Font("Arial", Font.BOLD, getSize() * 2 / 5));
		g.drawString("Y"+getYobun(), getX()+5, getY()+35);
		//*/

		// モンスター描画
		/*for (int i = 0; i < getMonsterList().size(); i++) {
			getMonsterList().get(i).paint(g);
		}*/
	}

	public boolean isBreakable() {
		int xIndex = getXIndex();
		int yIndex = getYIndex();
		Game game = getGame();
		Cell upCell    = game.getCell(xIndex, yIndex-1);
		Cell downCell  = game.getCell(xIndex, yIndex+1);
		Cell leftCell  = game.getCell(xIndex-1, yIndex);
		Cell rightCell = game.getCell(xIndex+1, yIndex);
		return upCell.isBroken() || downCell.isBroken() || leftCell.isBroken() || rightCell.isBroken();
	}

	/*===ゲッター===*/
	public boolean isBroken() {
		return broken;
	}

	public List<Monster> getMonsterList() {
		return monsterList;
	}

	/*===セッター===*/
	public void setBroken(boolean broken) {
		this.broken = broken;
	}
}
