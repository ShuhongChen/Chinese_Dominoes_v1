import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class TileComparator implements Comparator<Tile> {
	@Override
	public int compare(Tile x, Tile y) {
        return ((Double) x.num()).compareTo(y.num());
    }
}

class Tile {
	private int[] id;
	private double num;
	
	public Tile(int i, int j) {
		id = new int[2];
		id[0] = i;
		id[1] = j;
		num = i + (double) j / 10;
	}
	public Tile(double n) {
		num = n;
		id = new int[2];
		id[0] = (int) n;
		id[1] = (int) ((n - id[0]) * 10);
	}
	public int[] id() {
		return id;
	}
	public int id(int i) {
		return id[i];
	}
	public double num() {
		return num;
	}
	
	public String toString() {
		return "" + num;
	}
}

class Triple {
	private List<Tile> tri;
	private boolean[] ok;
	
	public Triple(Tile x, Tile y, Tile z) {
		tri = new ArrayList<Tile>();
		tri.add(x);
		tri.add(y);
		tri.add(z);
		ok = test(tri);
	}
	public Triple(double x, double y, double z) {
		tri = new ArrayList<Tile>();
		tri.add(new Tile(x));
		tri.add(new Tile(y));
		tri.add(new Tile(z));
		ok = test(tri);
	}
	public boolean[] ok() {
		return ok;
	}
	public boolean ok(int n) {
		return ok[n];
	}
	
	// tests fresh (industrial)
	public static boolean[] test(List<Tile> tri) {
		boolean[] ok = new boolean[8];
		if (tri.get(0) == null || tri.get(1) == null || tri.get(2) == null) {
		} else {
			Collections.sort(tri, new TileComparator());
			List<Integer> nums = new ArrayList<Integer>();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					nums.add(tri.get(i).id(j));
				}
			}
			
			Collections.sort(nums);

			ok[1] = demiA(nums);
			ok[2] = demiB(nums);
			ok[3] = three(nums);
			ok[4] = four(nums);
			ok[5] = five(nums);
			ok[6] = fourteen(nums);
			ok[7] = whole(nums);
			for (int i = 1; i <= 7; i++) {
				if (ok[i] == true) {
					ok[0] = true;
					break;
				}
			}
		}
		return ok;
	}
	private static boolean demiA(List<Integer> nums) {
		int n = 0;
		for (int i = 1; i <= 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (nums.get(n) != i) {
					return false;
				}
				n++;
			}
		}
		return true;
	}
	private static boolean demiB(List<Integer> nums) {
		int n = 0;
		for (int i = 4; i <= 6; i++) {
			for (int j = 0; j < 2; j++) {
				if (nums.get(n) != i) {
					return false;
				}
				n++;
			}
		}
		return true;
	}
	private static boolean three(List<Integer> nums) {
		for (int i = 0; i < 2; i++) {
			if (nums.get(i) != nums.get(i+1)) {
				return false;
			}
			if (nums.get(i+3) != nums.get(i+4)) {
				return false;
			}
		}
		return true;
	}
	private static boolean four(List<Integer> nums) {
		for (int i = 0; i < 3; i++) {
			if (nums.get(i) == nums.get(i+3)) {
				int n = 0;
				for (int j = 0; j < 6; j++) {
					if (j < i || j > i+3) {
						n += nums.get(j);
					}
				}
				if (n == nums.get(i)) {
					return true;
				}
			}
		}
		return false;
	}
	private static boolean five(List<Integer> nums) {
		if (nums.get(0) == nums.get(4) || nums.get(1) == nums.get(5)) {
			return true;
		}
		return false;
	}
	private static boolean fourteen(List<Integer> nums) {
		for (int i = 0; i < 4; i++) {
			if (nums.get(i) == nums.get(i+2)) {
				int n = 0;
				for (int j = 0; j < 6; j++) {
					if (j < i || j > i+2) {
						if (nums.get(j) == nums.get(i)) {
							n = 0;
							break;
						}
						n += nums.get(j);
					}
				}
				if (n >= 14) {
					return true;
				}
			}
		}
		return false;
	}
	private static boolean whole(List<Integer> nums) {
		for (int i = 0; i < 6; i++) {
			if (nums.get(i) != i+1) {
				return false;
			}
		}
		return true;
	}
	
	// tests fresh (utility)
	public static boolean[] test(Tile x, Tile y, Tile z) {
		Triple tri = new Triple (x, y, z);
		boolean[] ok = tri.ok();
		return ok;
	}
	
	// checks with solution set
	public static boolean[] check(Tile x, Tile y, Tile z) {
		return null;
	}
	
	public String toString() {
		String x = " ; ";
		return tri.get(0) + x +
				tri.get(1) + x +
				tri.get(2);
	}
}

public class TileSet {
	private ArrayList<Tile> typeSet;
	private ArrayList<Tile> allSet;
	
	public TileSet() {
		typeSet = new ArrayList<Tile>();
		for (int i = 1; i <= 6; i++) {
			for (int j = i; j <= 6; j++) {
				typeSet.add(new Tile(i, j));
			}
		}
		
		List<Tile> temp = new ArrayList<Tile>(typeSet);
		for (int i = 1; i <=6; i++) {
			temp.add(new Tile(i, i));
		}
		temp.add(new Tile(1, 3));
		temp.add(new Tile(1, 5));
		temp.add(new Tile(1, 6));
		temp.add(new Tile(4, 6));
		temp.add(new Tile(5, 6));
		Collections.sort(temp, new TileComparator());
		allSet = new ArrayList<Tile>(temp);
	}
	
	public ArrayList<Tile> typeSet() {
		return typeSet;
	}
	public ArrayList<Tile> allSet() {
		return allSet;
	}
}
