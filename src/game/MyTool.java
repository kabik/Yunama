package game;

import java.util.Random;

public class MyTool {
	/*
	 * 便利なメソッドを格納するクラス
	 * このクラスを static import すると良い
	 */


	// [min, max-1] の範囲の整数をランダムに返す
	public static int getRnd(int min, int max) {
		//return min + (int)((max - min) * Math.random());
		Random rnd = new Random();
		return rnd.nextInt(max-min) + min;
	}
}
