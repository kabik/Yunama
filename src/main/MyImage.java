package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MyImage {
	private Image pickelImage[] = new Image[2];
	private Image wallImage;
	private Image cellImage[] = new Image[9];
	private Image kokeImage[] = new Image[4];
	private Image tsubomiImage[] = new Image[4];
	private Image hanaImage[] = new Image[4];
	private Image mushiImage[] = new Image[17];

	MyImage() {
		BufferedImage readImage = null;
		try {
			// ピッケル
			readImage = ImageIO.read(new File("image/pickel/pickel1.gif"));
			pickelImage[0] = readImage;
			readImage = ImageIO.read(new File("image/pickel/pickel2.gif"));
			pickelImage[1] = readImage;

			// 壁
			readImage = ImageIO.read(new File("image/cell/wall.gif"));
			wallImage = readImage;

			// セル
			readImage = ImageIO.read(new File("image/cell/cell1.gif"));
			cellImage[0] = readImage;

			// コケ
			readImage = ImageIO.read(new File("image/monster/koke/koke_up1.gif"));
			kokeImage[0] = readImage;
			readImage = ImageIO.read(new File("image/monster/koke/koke_up2.gif"));
			kokeImage[1] = readImage;
			kokeImage[3] = readImage;
			readImage = ImageIO.read(new File("image/monster/koke/koke_up3.gif"));
			kokeImage[2] = readImage;

			// ツボミ
			readImage = ImageIO.read(new File("image/monster/koke/tsubomi1.gif"));
			tsubomiImage[0] = readImage;

			// ハナ
			readImage = ImageIO.read(new File("image/monster/koke/hana1.gif"));
			hanaImage[0] = readImage;

			// ムシ
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_up1.gif"));
			mushiImage[0] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_up2.gif"));
			mushiImage[1] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_up3.gif"));
			mushiImage[2] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_up4.gif"));
			mushiImage[3] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_right1.gif"));
			mushiImage[4] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_right2.gif"));
			mushiImage[5] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_right3.gif"));
			mushiImage[6] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_right4.gif"));
			mushiImage[7] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_down1.gif"));
			mushiImage[8] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_down2.gif"));
			mushiImage[9] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_down3.gif"));
			mushiImage[10] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_down4.gif"));
			mushiImage[11] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_left1.gif"));
			mushiImage[12] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_left2.gif"));
			mushiImage[13] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_left3.gif"));
			mushiImage[14] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/mushi_left4.gif"));
			mushiImage[15] = readImage;
			readImage = ImageIO.read(new File("image/monster/mushi/taberu.gif"));
			mushiImage[16] = readImage;
		} catch (Exception e) {
			e.printStackTrace();
			readImage = null;
		}
	}

	/*===ゲッター===*/
	public Image getPickelImage(int n) {
		return pickelImage[n];
	}

	public Image getWallImage() {
		return wallImage;
	}

	public Image getCellImage(int n) {
		return cellImage[n];
	}

	public Image getKokeImage(int n) {
		return kokeImage[n];
	}

	public Image getTsubomiImage(int n) {
		return tsubomiImage[n];
	}

	public Image getHanaImage(int n) {
		return hanaImage[n];
	}

	public Image getMushiImage(int n) {
		return mushiImage[n];
	}
}
