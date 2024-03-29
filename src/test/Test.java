package test;

public class Test {
	private static int[][] p = { // 養分,魔分をばらまくときの周囲のマスの優先度
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

	public static void main(String[] args) {
		int[][] a = new int[7][7];

		a[3][3] = 0;
		for (int i = 0; i < 48; i++) {
			a[ 3 + p[i][0] ][ 3 + p[i][1] ] = i+1;
		}

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.printf("%3d", a[j][i]);
			}
			System.out.println();
		}
	}
}
