import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Game {
	private List<ArrayList<Tile>> initial;
	private List<ArrayList<Tile>> table;
	private ArrayList<Tile> outed;
	
	private Boolean result;
	private int fullCalc;
	private int rowCalc;
	private int triCalc;
	
	public Game() {
		initialize();
		while (winCheck() == null) {
			if (fullCalc != 0) {
				moveOut();
			}
			
			if (fullCalc > 5000) {
				Collections.shuffle(table.get(0));
			}
			if (fullCalc > 6000) {
				break;
			}
			
			for (int r = 0; r < table.size(); r++) {
				calcRow(table.get(r));
				rowCalc++;
			}
			for (int r = table.size()-1; r >= 0; r--) {
				if (table.get(r).size() == 0) {
					table.remove(r);
				}
			}
			//*
			int stop = 50000;
			if (fullCalc > stop) {
				System.out.println(this);
				if (fullCalc > stop+10) {
					System.exit(0);
				}
			}
			//*/
			fullCalc++;
		}
	}
	private void initialize() {
		table = new ArrayList<ArrayList<Tile>>();
		outed = new ArrayList<Tile>();
		result = null;
		fullCalc = 0;
		rowCalc = 0;
		triCalc = 0;
		
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
		
		initial = new ArrayList<ArrayList<Tile>>();
		for (int i = 0; i < 5; i++) {
			initial.add(new ArrayList<Tile>(table.get(i)));
		}
		initial.add(new ArrayList<Tile>(outed));
	}
	
	public Game(List<ArrayList<Tile>> ini) throws IOException {
		PrintWriter writer = new PrintWriter("sample.txt", "UTF-8");;
		
		initialize(ini);
		System.out.println(this);
		while (winCheck() == null) {
			writer.write(this.toString() + "\n");
			
			if (fullCalc != 0) {
				moveOut();
			}
			
			if (fullCalc > 5000) {
				Collections.shuffle(table.get(0));
			}
			if (fullCalc > 6000) {
				break;
			}
			
			for (int r = 0; r < table.size(); r++) {
				calcRow(table.get(r));
				rowCalc++;
			}
			for (int r = table.size()-1; r >= 0; r--) {
				if (table.get(r).size() == 0) {
					table.remove(r);
				}
			}
			
			fullCalc++;
		}
		writer.write(this.toString() + "\n");
		writer.write("RESULT: " + result + "\n");
        writer.close();
        System.out.println("RESULT: " + result + "\n");
	}
	private void initialize(List<ArrayList<Tile>> ini) {
		table = new ArrayList<ArrayList<Tile>>();
		outed = new ArrayList<Tile>();
		result = null;
		fullCalc = 0;
		rowCalc = 0;
		triCalc = 0;
		
		for (int x = 0; x < 6; x++) {
			if (x == 5) {
				for (int i = 0; i < 7; i++) {
					outed.add(ini.get(x).remove(0));
				}
			} else {
				table.add(new ArrayList<Tile>());
				for (int y = 0; y < 5; y++) {
					table.get(x).add(ini.get(x).remove(0));
				}
			}
		}
		
		initial = new ArrayList<ArrayList<Tile>>();
		for (int i = 0; i < 5; i++) {
			initial.add(new ArrayList<Tile>(table.get(i)));
		}
		initial.add(new ArrayList<Tile>(outed));
	}
	
	private void calcRow(ArrayList<Tile> row) {
		if (row.size() >= 3) {
			for (int i = 0; i <= row.size()-3; i++) {
				for (int j = i+1; j <= row.size()-2; j++) {
					for (int k = j+1; k <= row.size()-1; k++) {
						Triple tri = new Triple(row.get(i), row.get(j), row.get(k));
						if (tri.ok(0) == true) {
							outed.add(0, row.set(k, null));
							outed.add(0, row.set(j, null));
							outed.add(0, row.set(i, null));
						}
						triCalc++;
					}
				}
			}
			
			for (int n = row.size()-1; n >= 0; n--) {
				if (row.get(n) == null) {
					row.remove(n);
				}
			}
		}
	}
	private void moveOut() {
		int x = outed.size() - table.size();
		for (int n = 0; n < table.size(); n++) {
			table.get(n).add(outed.remove(x));
		}
	}
	private Boolean winCheck() {
		if (outed.size() < table.size()) {
			result = false;
		} else if (table.size() == 0) {
			result = true;
		}
		return result;
	}
	
	public Boolean getResult() {
		return result;
	}
	
	
	public void printIni() {
		for (ArrayList<Tile> row: initial) {
			for (Tile t: row) {
				System.out.print(t + ",\t");
			}
			System.out.println();
		}
	}
	public void printResult() {
		System.out.println(result + "\n" + fullCalc + "\n" + rowCalc + "\n" + triCalc);
	}
	public String toString() {
		String str = "";
		for (ArrayList<Tile> row: table) {
			for (Tile t: row) {
				str += (t + ",\t");
			}
			str += "\n";
		}
		for (Tile t: outed) {
			str += (t + ",\t");
		}
		str += "\n";
		return str;
	}

}
