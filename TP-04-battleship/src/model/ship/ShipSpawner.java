package model.ship;
import util.Position;
import java.util.ArrayList;
import java.util.Arrays;

public class ShipSpawner {
	Ship myShipTab[] = new Ship[4];
	ArrayList<Position> myCheckCoord = new ArrayList<>();

	public ShipSpawner() {
		this.myShipTab[0] = new Carrier(4);
		this.myShipTab[1] = new Destroyer(3);
		this.myShipTab[2] = new Submarine(3);
		this.myShipTab[3] = new PatrolShip(2);
		this.initPosShip();
	}

	private void initCarrier() {
		int tempPosX, tempPoxY, myRandomGenerator;
		boolean shipAdded = false;
		do {
			myRandomGenerator = (int)(Math.random() * 2);
			tempPosX = (int) (Math.random() * 10);
			tempPoxY = (int) (Math.random() * 10);
			if (myRandomGenerator == 0 && tempPosX + myShipTab[0].getSize() < 10) {
				for (int i = 0; i < myShipTab[0].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX + i, tempPoxY));
					myShipTab[0].initPosition(i, new Position(tempPosX + i, tempPoxY));
				}
				shipAdded = true;
			} else if (myRandomGenerator == 1 && tempPoxY + myShipTab[0].getSize() < 10) {
				for (int i = 0; i < myShipTab[0].getSize(); i++) {
					myCheckCoord.add(new Position(tempPosX, tempPoxY + i));
					myShipTab[0].initPosition(i, new Position(tempPosX, tempPoxY + i));
				}
				shipAdded = true;
			}

		} while (!shipAdded);
	}

	private void initDestroyer() {
		int tempPosX,tempPoxY,myRandomGenerator;
		boolean shipAdded = false;
		do {
			myRandomGenerator = (int)(Math.random() * 2);
			tempPosX = (int)(Math.random() * 10);
			tempPoxY = (int)(Math.random() * 10);
			for (Position coord : myCheckCoord) {
				if(coord.getX() == tempPosX  && coord.getY() == tempPoxY || coord.getX() == tempPosX + 1 && coord.getY() == tempPoxY || coord.getX() == tempPosX + 2 && coord.getY() == tempPoxY || coord.getX() == tempPosX && coord.getY() == tempPoxY + 1 || coord.getX() == tempPosX && coord.getY() == tempPoxY + 2) {
					myRandomGenerator = -1;
				}
			}
			if (myRandomGenerator == 0 && tempPosX + myShipTab[1].getSize() < 10) {
				for(int i = 0 ; i <  myShipTab[1].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX + i, tempPoxY));
					myShipTab[1].initPosition(i, new Position(tempPosX + i, tempPoxY));
				}
				shipAdded = true;
			}else if(myRandomGenerator == 1 && tempPoxY +  myShipTab[1].getSize() < 10){
				for(int i = 0 ; i <  myShipTab[1].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX , tempPoxY + i));
					myShipTab[1].initPosition(i, new Position(tempPosX , tempPoxY + i));
				}
				shipAdded = true;
			}
			
		} while (!shipAdded);
	}

	private void initSubmarine() {
		int tempPosX,tempPoxY,myRandomGenerator;
		boolean shipAdded = false;
		do {
			myRandomGenerator = (int)(Math.random() * 2);
			tempPosX = (int)(Math.random() * 10);
			tempPoxY = (int)(Math.random() * 10);
			for (Position coord : myCheckCoord) {
				if(coord.getX() == tempPosX  && coord.getY() == tempPoxY || coord.getX() == tempPosX + 1 && coord.getY() == tempPoxY || coord.getX() == tempPosX + 2 && coord.getY() == tempPoxY || coord.getX() == tempPosX && coord.getY() == tempPoxY + 1 || coord.getX() == tempPosX && coord.getY() == tempPoxY + 2) {
					myRandomGenerator = -1;
				}
			}
			if (myRandomGenerator == 0 && tempPosX + myShipTab[2].getSize() < 10) {
				for(int i = 0 ; i < myShipTab[2].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX + i, tempPoxY));
					myShipTab[2].initPosition(i, new Position(tempPosX + i, tempPoxY));
				}
				shipAdded = true;
			}else if(myRandomGenerator == 1 && tempPoxY + myShipTab[2].getSize() < 10){
				for(int i = 0 ; i < myShipTab[2].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX , tempPoxY + i));
					myShipTab[2].initPosition(i, new Position(tempPosX , tempPoxY + i));
				}
				shipAdded = true;
			}
			
		} while (!shipAdded);
	}

	private void initPatrolShip() {
		int tempPosX,tempPoxY,myRandomGenerator;
		boolean shipAdded = false;
		do {
			myRandomGenerator = (int)(Math.random() * 2);
			tempPosX = (int)(Math.random() * 10);
			tempPoxY = (int)(Math.random() * 10);
			for (Position coord : myCheckCoord) {
				if(coord.getX() == tempPosX  && coord.getY() == tempPoxY || coord.getX() == tempPosX + 1 && coord.getY() == tempPoxY || coord.getX() == tempPosX && coord.getY() == tempPoxY + 1) {
					myRandomGenerator = -1;
				}
			}
			if (myRandomGenerator == 0 && tempPosX + myShipTab[3].getSize() < 10) {
				for(int i = 0 ; i < myShipTab[3].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX + i, tempPoxY));
					myShipTab[3].initPosition(i, new Position(tempPosX + i, tempPoxY));
				}
				shipAdded = true;
			}else if(myRandomGenerator == 1 && tempPoxY + myShipTab[3].getSize() < 10){
				for(int i = 0 ; i < myShipTab[3].getSize() ; i++) {
					myCheckCoord.add(new Position(tempPosX , tempPoxY + i));
					myShipTab[3].initPosition(i, new Position(tempPosX , tempPoxY + i));
				}
				shipAdded = true;
			}
			
		} while (!shipAdded);
	}

	public void initPosShip() {  			// fonction qui appel toutes le methode de generation de position pour chaque ship
		this.initCarrier();
		this.initDestroyer();
		this.initSubmarine();
		this.initPatrolShip();
	}

	public ArrayList<Ship> getMyShipTab() {   // methode qui retourne un arraylist de ship
		return new ArrayList<>(Arrays.asList(myShipTab));
	}

	private void setMyShipTab(Ship[] myShipTab) {
		this.myShipTab = myShipTab;
	}

	public ArrayList<Position> getMyCheckCoord() {
		return myCheckCoord;
	}

	private void setMyCheckCoord(ArrayList<Position> myCheckCoord) {
		this.myCheckCoord = myCheckCoord;
	}
	
	public static void main(String[] args)
	{
		ShipSpawner test = new ShipSpawner();
		
	}
}