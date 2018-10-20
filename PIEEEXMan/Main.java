// Don't place your source in a package
import java.util.*;
import java.lang.*;
import java.io.*;

// Please name your class Main
class Main {
	static Scanner sc = new Scanner(getInputStream());
	static InputStream getInputStream() {
		try {
			return new FileInputStream(new File("map.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return System.in;
	}
	static GameMap map;
	
	static int count = 0;
	public static void main(String args[]) {
		int height = sc.nextInt();
		int width = sc.nextInt();
		map = new GameMap(width, height);
		sc.nextLine();
		
		
		for (int row = 0; row < 2 * height + 1; row++) {
			String line = sc.nextLine();
			for (int col = 0; col < line.length(); col++) {
				map.setChar(col, row, line.charAt(col));
			}
		}
		// ==end input==
//		Cell start = map.findPath(new Cell(5, 13), new Cell(9, 5));
////		while (start.cameFrom != null) {
////			System.out.println(start.y + ", " + start.x);
////			start = start.cameFrom;
////		}
//		ArrayList<Cell> path = reversePath(start);
//		for (int i = 1; i < path.size(); i++) {
//			chooseOption(path.get(i), path.get(i-1));
//		}
			
		
		
		
		Cell start = map.player;
		
		boolean shouldBreak = false;
		while (!shouldBreak && map.cherryLocations.size() > 0) {
			Cell current = findShortest(start);
			start = current;
////map.setChar(current.x, current.y, ("" + count++).charAt(0));
			ArrayList<Cell> path = reversePath(current);
			shouldBreak = choosePathOptions(path);
//printMap();
		}
	}
	static boolean choosePathOptions(ArrayList<Cell> path) {
		for (int i = 2; i < path.size(); i += 2)
			if (chooseOption(path.get(i), path.get(i-2)))
				return true;
		return false;
	}
	static ArrayList<Cell> reversePath(Cell current) {
		ArrayList<Cell> path = new ArrayList<Cell>();
		
		do {
			path.add(0, current);
			current = current.cameFrom;
		} while (current != null);
		
		return path;
	}
	static Cell findShortest(Cell start) {
		Cell shortest = null;
		int cherryIndex = 0;
		
		for (int i = 0; i < map.cherryLocations.size(); i++) {
			Cell cherry = map.cherryLocations.get(i);
			Cell current = map.findPath(start, cherry);
			if (current != null && (shortest == null || shortest.startDist > current.startDist)) {
				shortest = current;
				cherryIndex = i;
			}
		}
		
		map.cherryLocations.remove(cherryIndex);
		
		return shortest;
	}
	static boolean chooseOption(Cell current, Cell previous) {
		int distX = current.distanceX(previous);
		int distY = current.distanceY(previous);
		char choice;
		
		if (distX > 0) {
			choice = 'R';
		}
		else if (distX < 0) {
			choice = 'L';
		}
		else if (distY > 0) {
			choice = 'D';
		}
		else if (distY < 0) {
			choice = 'U';
		}
		else {
			choice = 'W';
		}
		
		System.out.println(choice);
		
		if (sc.hasNextLine())
			return sc.nextLine().endsWith("X");
		else
			return false;
	}
	
	
	static void printMap() {
		for (char[] row: map.charCells) {
			for (char cell: row)
				System.out.print(cell);
			System.out.println();
		}
	}
}

class GameMap {
	char[][] charCells;
	ArrayList<Cell> cherryLocations = new ArrayList<Cell>();
	Cell player;
	
	
	public GameMap(int width, int height) {
		charCells = new char[2 * height + 1][2 * width + 1];
		
		for (int y = 0; y < charCells.length; y++) {
			for (int x = 0; x < charCells[0].length; x++) {
				setChar(x, y, '_');
			}
		}
	}
	
	
	public void setChar(int x, int y, char c) {
		charCells[y][x] = c;
		
		if (c == '@')
			cherryLocations.add(new Cell(x, y));
		else if (c == '1')
			player = new Cell(x, y);
	}
	public char getChar(int x, int y) {
		return charCells[y][x];
	}

	int count = 0;
	public Cell findPath(Cell start, Cell target) {
		int height = charCells.length,
			width = charCells[0].length;

		ArrayList<Cell> closed = new ArrayList<Cell>();
		ArrayList<Cell> open = new ArrayList<Cell>();
		Cell[][] cells = new Cell[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cells[y][x] = new Cell(x, y);
				if (getChar(x, y) != '#')
					open.add(cells[y][x]);
				else
					closed.add(cells[y][x]);
			}
		}
		
		cells[start.y][start.x].gScore = 0;
		cells[start.y][start.x].fScore = start.distance(target);
		cells[start.y][start.x].startDist = 0;
//System.out.println(">>From " + start.y + ", " + start.x + " to " + target.y + ", " + target.x);
		while (!open.isEmpty()) {
			Cell current = lowestFScore(open);
			
//System.out.println(current.x + "," + current.y + " | " + start.x + "," + start.y + " | " + current.fScore);
//setChar(current.x, current.y, ("" + count++).charAt(0));
			
			if (current.distance(target) == 0) {
//System.out.println("Return " + current.y + ", " + current.x);
				return current;
			}
			
			open.remove(current);
			closed.add(current);
			
			int x = current.x;
			int y = current.y;
			ArrayList<Cell> neighborArray = new ArrayList<Cell>(4);
			if (y < cells[0].length - 1)
				neighborArray.add(cells[y + 1][x]);
			if (y > 0)
				neighborArray.add(cells[y - 1][x]);
			if (x < cells.length - 1)
				neighborArray.add(cells[y][x + 1]);
			if (x > 0)
				neighborArray.add(cells[y][x - 1]);
			
			
			for (Cell neighbor: neighborArray) {
//if (start.y == 13 && start.x == 5 && target.y == 5 && target.x == 9)
//System.out.println("Neighbor: " + current.y + "/" + neighbor.y + ", " + current.x + "/" + neighbor.x + " | " + neighbor.fScore + " " + getChar(neighbor.x, neighbor.y));
				if (closed.contains(neighbor)) {
//if (start.y == 13 && start.x == 5 && target.y == 5 && target.x == 9)
//System.out.println("Skipped");
					continue;
				}
//if (current.distance(neighbor) > 1)
//	System.out.println(">>ERROR: " + neighbor.y + "," + neighbor.x + " | " + current.y + ", " + current.x);
				
				int gScore = current.gScore + current.distance(neighbor);
				
				if (!open.contains(neighbor))
					open.add(neighbor);
				else if (gScore >= neighbor.gScore)
					continue;
				
//System.out.println("add " + neighbor.y + "," + neighbor.x);
				neighbor.cameFrom = current;
				neighbor.gScore = gScore;
				neighbor.fScore = gScore + neighbor.distance(target);
				neighbor.startDist = current.startDist + 1;
			}
		}
		
		return null;
	}
	private Cell lowestFScore(ArrayList<Cell> open) {
		Cell lowest = open.get(0);
		
		for (int i = 1; i < open.size(); i++)
			if (lowest.fScore > open.get(i).fScore)
				lowest = open.get(i);
		
		return lowest;
	}
	
}

class Cell {
	Cell cameFrom;
	int gScore;
	int fScore;
	int startDist;
	int x, y;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;

		gScore = Integer.MAX_VALUE;
		fScore = Integer.MAX_VALUE;
	}
	
	public int distanceX(Cell other) {
		return x - other.x;
	}
	public int distanceY(Cell other) {
		return y - other.y;
	}
	public int distance(Cell other) {
		return Math.abs(distanceX(other)) + Math.abs(distanceY(other));
	}
}