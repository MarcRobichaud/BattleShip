package controller;

import model.network.HostClient;
import model.ship.Ship;
import util.Position;
import util.ShootType;

import java.util.ArrayList;

public interface IController
{
    void startVsAi();

    void startOnline(HostClient server);

    ArrayList<Ship> spawnShips();

    void shoot(Position position);

    ShootType shootPlayer2At(Position position);

    ShootType shootPlayer1At(Position position);

    void changeConsoleOutputOfViewBoard(String output);

    void quit();

    void restart();

    void option();
}