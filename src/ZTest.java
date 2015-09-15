import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ZTest {
	private static TileSet tiles = new TileSet();
	
	private static ArrayList<ArrayList<Tile>> randIni() {
		ArrayList<ArrayList<Tile>> table = new ArrayList<ArrayList<Tile>>();
		ArrayList<Tile> outed = new ArrayList<Tile>();
		
		TileSet tiles = new TileSet();
		Collections.shuffle(tiles.allSet());
		
		for (int x = 0; x < 6; x++) {
			if (x == 5) {
				for (int i = 0; i < 7; i++) {
					outed.add(tiles.allSet().remove(0));
				}
			} else {
				table.add(new ArrayList<Tile>());
				for (int y = 0; y < 5; y++) {
					table.get(x).add(tiles.allSet().remove(0));
				}
			}
		}
		
		ArrayList<ArrayList<Tile>> ini = new ArrayList<ArrayList<Tile>>();
		for (int i = 0; i < 5; i++) {
			ini.add(new ArrayList<Tile>(table.get(i)));
		}
		ini.add(new ArrayList<Tile>(outed));
		
		return ini;
	}
	
	private static void testTile() {
		int n = 0;
		
		System.out.println("TYPESET:");
		for (Tile t: tiles.typeSet()) {
			System.out.println(t + "\t" + t.num());
			n++;
		}
		System.out.println(n + "\n");
		n = 0;
		
		System.out.println("ALLSET:");
		for (Tile t: tiles.allSet()) {
			System.out.println(t + "\t" + t.num());
			n++;
		}
		System.out.println(n + "\n");
	}
	private static void testArray() {
		Boolean[] arr = new Boolean[5];
		System.out.println(arr[0]);
		
		ArrayList<Integer> i = new ArrayList<Integer>();
		i.add(1);
		i.add(2);
		i.add(null);
		i.add(3);
		
		for (Integer x: i) {
			System.out.println(x);
		}
		
		for (int x = 0; x < 2; x++) {
			i.remove(0);
		}
		System.out.println(i.size());
		System.out.println();

		for (Integer x: i) {
			System.out.println(x);
		}
		System.out.println();
		
		i.add(i.remove(0));
		for (Integer x: i) {
			System.out.println(x);
		}
	}
	private static void testTri() {
		Triple tri = new Triple(1.3, 1.1, 1.6);
		System.out.println(tri + "\t");
		
		boolean[] oks = tri.ok();
		System.out.println("\t" + oks[0]);
		System.out.println("A:\t" + oks[1]);
		System.out.println("B:\t" + oks[2]);
		System.out.println("3:\t" + oks[3]);
		System.out.println("4:\t" + oks[4]);
		System.out.println("5:\t" + oks[5]);
		System.out.println("14:\t" + oks[6]);
		System.out.println("W:\t" + oks[7]);
	}
	private static void testGame() {
		Game game = new Game();
		
		game.printIni();
		System.out.println("\n" + game);
		game.printResult();
	}
	private static void doData() {
		int k = 50000;
		
		Game game;
		int wins = 0;
		int ties = 0;
		for (int n = 0; n < k; n++) {
			game = new Game();
			if (game.getResult() == null) {
				ties++;
			} else if (game.getResult() == true) {
				wins++;
			}
			System.out.println("GAME:\t" + n + "\t" + game.getResult());
		}
		System.out.println(wins + " / " + k + " = " + (double) wins / k);
		System.out.println(ties + " / " + k + " = " + (double) ties / k);
	}
	private static void writeGame() throws IOException {
		Game game = new Game(randIni());
	}
	private static void checkGame() throws IOException {
		ArrayList<ArrayList<Tile>> ini = new ArrayList<ArrayList<Tile>>();
		double[] temp = {
				1.1,	2.2,	1.3,	1.6,	1.5,	
				1.3,	1.4,	5.6,	2.2,	3.3,	
				4.4,	4.6,	1.6,	3.5,	5.5,	
				1.1,	5.5,	6.6,	1.5,	4.5,	
				3.3,	4.4,	4.6,	1.2,	5.6,	
				3.6,	6.6,	3.4,	2.5,	2.4,	2.6,	2.3,	
		};
		
		int r = 0;
		for (int i = 0; i < 6; i++) {
			ini.add(new ArrayList<Tile>());
			int k = 5;
			if (i == 5) {
				k = 7;
			}
			for (int j = 0; j < k; j++) {
				ini.get(i).add(new Tile(temp[r]));
				r++;
			}
		}
		
		Game game = new Game(ini);
	}
	
	public static void main(String[] args) throws IOException {
		//testTile();
		//testArray();
		//testTri();
		//testGame();
		doData();
		//writeGame();
		//checkGame();
	}

}
