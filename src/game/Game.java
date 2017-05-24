package game;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import cell.Cell;
import cell.Wall;
import main.MyCanvas;
import monster.Monster;

public class Game {
	public final static int W = 2100;		 // ゲーム横
	public final static int H = 1200;		 // ゲーム縦
	public final static int CELLSIZE = 50;	 // セルのサイズ
	private int cellSize = 50;				 // セルのサイズ
	private int cellXNum = 40;				 // 1行のセルの数(X)
	private int cellYNum = 20;				 // 1列のセルの数(Y)

	private MyCanvas canvas;
	private Font japFont = new Font("MS 明朝", Font.BOLD, getCellSize() * 2 / 5);
	private Font enFont = new Font("Arial", Font.BOLD, getCellSize() * 2 / 5);

	private Detail detail;
	private Pickel pickel;
	private Cell cell[][] = new Cell[getCellXNum()+2][getCellYNum()+2];
	private List<Monster> monsterList = new  LinkedList<Monster>();

	public Game(MyCanvas canvas) {
		/*
		for (int y = 0; y < cellYNum+2; y++) {
			for (int x = 0; x < cellXNum+2; x++) {
				if (this.isXIndexCenter(x) && this.isYIndexCenter(y) ){ 
					System.out.print(1);
				} else {
					System.out.print(0);
				}
			}
			System.out.println();
		}*/
		
		this.canvas = canvas;

		this.detail = new Detail(this);
		this.pickel = new Pickel(getCellXNum() / 2 + 1, 1, this);

		for (int i = 0; i <= getCellXNum()+1; i++) {
			for (int j = 0; j <= getCellYNum()+1; j++) {
				cell[i][j] = inside(i,j) ?
						new Cell(i, j, getCellSize(), this) : new Wall(i, j, getCellSize(), this);
			}
		}

		cell[getCellXNum()/2+1][1].setBroken(true);
	}

	public void update() {
		for (int i = 0; i < monsterList.size(); i++) {
			monsterList.get(i).update();
		}
		/*for (Cell[] cArray : cell) {
			for (Cell c : cArray) {
				c.monsterUpdate();
			}
		}*/
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			pickel.dig();
			break;
		case KeyEvent.VK_NONCONVERT:
			setDetail();
			break;
		case KeyEvent.VK_UP:
			pickel.moveUp();
			break;
		case KeyEvent.VK_RIGHT:
			pickel.moveRight();
			break;
		case KeyEvent.VK_DOWN:
			pickel.moveDown();
			break;
		case KeyEvent.VK_LEFT:
			pickel.moveLeft();
			break;
		default:
		}
	}

	public boolean inside(int xIndex, int yIndex) {
		return 0 < xIndex && xIndex < getCellXNum()+1 && 0 < yIndex && yIndex < getCellYNum()+1;
	}

	public boolean isXIndexCenter(int xIndex) {
		return getCellXNum() / 4 + 1 < xIndex && xIndex < getCellXNum() * 3 / 4 + 1;
	}

	public boolean isYIndexCenter(int yIndex) {
		return getCellYNum() / 4 + 1 < yIndex && yIndex < getCellYNum() * 3 / 4;
	}

	public void addMonster(Monster monster) {
		monsterList.add(monster);
	}

	public void deleteMonster(Monster monster) {
		monsterList.remove(monster);
	}

	public void setDetail() {
		Cell c = getCell(pickel.getXIndex(), pickel.getYIndex());
		List<Monster> list = c.getMonsterList();
		if (!list.isEmpty()) {
			getDetail().setYM(list.get(0));
		} else if (!c.isBroken()) {
			getDetail().setYM(c);
		} else {
			getDetail().setYM(null);
		}
	}

	/*===ゲッター===*/
	public MyCanvas getCanvas() {
		return canvas;
	}

	public Pickel getPickel() {
		return pickel;
	}

	public Detail getDetail() {
		return detail;
	}

	public Cell getCell(int xIndex, int yIndex) {
		return cell[xIndex][yIndex];
	}

	public Font getFont(boolean jap) {
		if (jap) {
			return japFont;
		} else {
			return enFont;
		}
	}

	public int getCellSize() {
		return cellSize;
	}

	public int getCellXNum() {
		return cellXNum;
	}

	public int getCellYNum() {
		return cellYNum;
	}

	public List<Monster> getMonsterList() {
		return monsterList;
	}

	/*===セッター===*/
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}
}
