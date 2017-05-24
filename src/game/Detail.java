package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import cell.YMContainer;
import monster.Monster;
import monster.Species;

public class Detail {
	Game game;
	YMContainer ym = null;

	Detail(Game game) {
		this.game = game;
	}

	public void paint(Graphics2D g) {
		if (canPaint()) {
			int x = ym.getX(),
				y = ym.getY(),
				size = ym.getSize();
			String label = ym.getSpecies() == Species.TSUCHI ?
				ym.getName() :
				ym.getName() + " " + ((Monster)ym).getHP();
			int xPoints[] = {x+size/4, x+size*3/4, x+size/2};
			int yPoints[] = {y-size/4, y-size/4,   y};
			// 枠
			g.setColor(new Color(75, 0, 130, 200));
			g.fillRoundRect(x - size/2, y - size * 5 / 4, size * 2, size, size / 10, size / 10);
			g.fillPolygon(new Polygon(xPoints, yPoints, xPoints.length));
			// 文字
			g.setColor(Color.white);
			g.setFont(game.getFont(true));
			g.drawString(label,
					x - size * 2 / 5,
					y - size * 4 / 5);
			g.drawString("養" + ym.getYobun() + " 魔" + ym.getMabun(),
					x - size * 2 / 5,
					y - size * 2 / 5);
		}
	}

	// ymがnullだったら描画しない
	public boolean canPaint() {
		return getYM() != null;
	}

	public void deleteYM(YMContainer ym) {
		if (getYM() == ym) {
			setYM(null);
		}
	}

	/*===ゲッター===*/
	public YMContainer getYM() {
		return ym;
	}

	/*===セッター===*/
	public void setYM(YMContainer ym) {
		this.ym = ym;
	}
}
