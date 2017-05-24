package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.VolatileImage;
import java.util.List;

import javax.swing.JPanel;

import cell.Cell;
import game.Game;
import monster.Monster;

public class MyCanvas extends JPanel {
	MyFrame frame;
	//Game game;
	private MyImage images;
	private VolatileImage vi;
	private Graphics vg;
	private int w, h;   // ゲーム全体の幅
	private int drawX, drawY; // 描画開始位置

	public MyCanvas(MyFrame frame) {
		this.frame = frame;
		this.images = new MyImage();
		this.w = Game.W;
		this.h = Game.H;
		this.drawX = - Game.W / 4 + Game.CELLSIZE / 2;
		this.drawY = 0;
		setBackground(Color.white);
	}

	public void paint(Graphics g) {
		super.paint(g);

		if (vg == null) {
			vi = createVolatileImage(w, h);
			vg = vi.getGraphics();
		}

		vg.setColor(Color.black);
		vg.fillRect(0, 0, w, h);

		gamePaint((Graphics2D)vg);

		g.drawImage(vi, getDrawX(), getDrawY(), null);
	}

	private void gamePaint(Graphics2D g) {
		Game game = frame.getGame();
		// セル描画(モンスターも)
		for (int i = 0; i <= game.getCellXNum()+1; i++) {
			for (int j = 0; j <= game.getCellYNum()+1; j++) {
				game.getCell(i, j).paint(g);
			}
		}

		// モンスター描画
		List<Monster> list = game.getMonsterList();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).paint(g);
		}

		// ピッケル描画
		game.getPickel().paint(g);

		// 詳細描画
		game.getDetail().paint(g);

		yobunSumPaint(g);
	}

	private void yobunSumPaint(Graphics2D g) {
		Game game = frame.getGame();
		int w = game.getCellXNum();
		int h = game.getCellYNum();
		int sum = 0;
		for (int i = 1; i <= w; i++) {
			for (int j = 1; j <= h; j++) {
				Cell c = game.getCell(i,j);
				List<Monster> list = c.getMonsterList();
				for (int k = 0; k < list.size(); k++) {
					sum += list.get(k).getYobun();
				}
				sum += c.getYobun();
			}
		}
		g.setFont(game.getFont(true));
		g.setColor(Color.cyan);
		g.drawString("合計養分:"+sum, 10, 40);
	}

	/*
	 *  画像を描画する位置を移動させる
	 *  引数は移動するマスの数で受け取る
	 */
	public void imageMove(int dXIndex, int dYIndex) {
		Game game = frame.getGame();
		setDrawX(getDrawX() - dXIndex * game.getCellSize());
		setDrawY(getDrawY() - dYIndex * game.getCellSize());
	}

	/*===ゲッター===*/
	public MyImage getImages() {
		return images;
	}

	public Graphics getVg() {
		return vg;
	}

	public int getDrawX() {
		return drawX;
	}

	public int getDrawY() {
		return drawY;
	}

	/*===セッター===*/
	public void setVg(Graphics vg) {
		this.vg = vg;
	}

	public void setDrawX(int drawX) {
		this.drawX = drawX;
	}

	public void setDrawY(int drawY) {
		this.drawY = drawY;
	}
}
