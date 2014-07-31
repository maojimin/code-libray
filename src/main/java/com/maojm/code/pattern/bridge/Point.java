package com.maojm.code.pattern.bridge;

public class Point {

	private int coordinateX;
	private int coordinateY;

	public Point(int coordinateX, int coordinateY) {
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
	}

	public String toString() {
		return "Point[x=" + coordinateX + ",y=" + coordinateY + "]";
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}
}
