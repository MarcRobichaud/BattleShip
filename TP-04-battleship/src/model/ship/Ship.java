package model.ship;

import util.Position;

public class Ship {
	protected int size;
	protected Position[] positions = new Position[4];
	protected int health;
	protected String name;
	protected boolean isSunk;

	public Ship(int size, String name) {
		this.size = size;
		this.name = name;
		health = size;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j <this.size; j++) {
				this.positions[j] = new Position(-1, -1);
			}
		}
	}

	public Position[] getPositions() {
		return positions;
	}

	public void initPosition(int index, Position position) {
		this.positions[index] = position;
	}

	public void removeHealth()
	{
		health--;
		if (health <= 0)
		{
			isSunk = true;
		}
	}

	public int getSize() {
		return size;
	}

	public String getName()
	{
		return name;
	}

	public Boolean isSunk()
	{
		return isSunk;
	}
}