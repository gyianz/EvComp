package EvComp_TSP;

import java.io.*;
import java.util.*;

public class Map {

	private double[][] distanceVector;
	public String fileName;
	private ArrayList<Point> collection;

	/**
	 * Private class Point to parse the input file
	 * @author Zi Yang Pang
	 */
	private class Point {

		private int x;
		private int y;

		/**
		 * Constructor for the Point class
		 * @param initX The X coordinate of the point
		 * @param initY The Y coordinate of the point
		 */
		public Point(int initX, int initY) {
			x = initX;
			y = initY;
		}

		/**
		 * 
		 * @return X coordinate of the point
		 */
		public int getX() {
			return x;
		}

		/**
		 * 
		 * @return Y coordinate of the point
		 */
		public int getY() {
			return y;
		}
	}

	/**
	 * Constructor for the Map class
	 * @param initName The initial .tour file from the problem
	 */
	public Map(String initName) {
		fileName = initName;
		parseFile();
		initArray();
	}

	/**
	 * Parse the file to read in all the coordinates.
	 */
	public void parseFile() {
		collection = new ArrayList<Point>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			int headerLines = 6;
			while (headerLines != 0) {
				headerLines--;
				line = br.readLine();
			}
			while (line != null && !line.contains("EOF")) {
				String[] breakPoint = line.split(" ");
				Point newPoint = new Point(Double.valueOf(breakPoint[1]).intValue(), Double.valueOf(breakPoint[2]).intValue());
				collection.add(newPoint);
				line = br.readLine();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Initialize a 2D array that contains all the distance between points
	 */
	public void initArray() {
		distanceVector = new double[getMapSize()][getMapSize()];
		for (int i = 0; i < getMapSize(); i++) {
			for (int j = 0; j < getMapSize(); j++) {
				Point pointA = collection.get(i);
				Point pointB = collection.get(j);
				distanceVector[i][j] = Math.sqrt(Math.pow(pointA.getX() - pointB.getX(), 2) + Math.pow(pointA.getY() - pointB.getY(), 2));
			}
		}
	}

	/**
	 * Gets the map size
	 * @return map size
	 */
	public int getMapSize() {
		return collection.size();
	}

	/**
	 * Returns the distance between Point A and Point B.
	 *
	 * @param pointA Index of Point A.
	 * @param pointB Index of Point B.
	 * @return Real distance in double.
	 */
	public double getDistance(int pointA, int pointB) {
		return distanceVector[pointA][pointB];
	}

	/**
	 * Prints the 2D array
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getMapSize(); i++) {
			sb.append(String.format("%.2f", distanceVector[i][0]));
			for (int j = 1; j < getMapSize(); j++) {
				sb.append(" ").append(String.format("%.2f", distanceVector[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
