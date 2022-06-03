package view;

import controller.IController;
import util.BFrame;
import util.Debug;
import util.MenuJButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class MainMenu implements IMainMenu, ActionListener
{
	private IController controller;
	private JLabel myImgContainer;
	private ImageIcon imageTest;
	private BFrame menu;
	private MenuJButton PlayVsAi,PlayOnline,exit,option;

	public MainMenu()
	{
		menu();
	}

	public void setController(IController controller)
	{
		this.controller = controller;
	}

	private void menu() { //methode pour afficher le menu
		
		JFrame myframe = new JFrame();						// initialisation de frame
		imageTest = new ImageIcon(this.getClass().getResource("../resources/battle1.png"));
		myImgContainer = new JLabel(imageTest);
		myImgContainer.setSize(470,599);
		menu = new BFrame("BattleShip - Main Menu");
		menu.setSize(new Dimension(1000, 666));

		menu.add(myImgContainer);
		//myImgContainer.setLayout(new FlowLayout());
		myImgContainer.setLayout(new GridLayout(2,3, 0, 10));

		for (int i = 0; i < 5; i++)
		{
			JPanel empty = new JPanel();
			empty.setOpaque(false);
			myImgContainer.add(empty);
		}

		JPanel ligne4 = new JPanel();
		ligne4.setLayout(new GridLayout(0,1));
		ligne4.setOpaque(false);

		PlayVsAi = new MenuJButton("Play vs AI");
		ligne4.add(PlayVsAi);
		PlayVsAi.addActionListener(this);
		
		PlayOnline = new MenuJButton("Play Online");
		ligne4.add(PlayOnline);
		PlayOnline.addActionListener(this);

		option = new MenuJButton("Option");
		ligne4.add(option);
		option.addActionListener(this);

		exit = new MenuJButton("Exit");
		ligne4.add(exit);
		exit.addActionListener(this);

		menu.getFrame().setResizable(false);
		myImgContainer.add(ligne4);

		menu.getFrame().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {	//action listener pour les bouttons
		switch (e.getActionCommand())
		{
			case "Play vs AI" -> playVsAi();			//les appels de fonction sur le click des bouttons
			case "Play Online" -> playOnline();
			case "Option" -> optionMenu();
			case "Exit" -> exit();
		}
	}

	private void playVsAi() // fonction qui lance jeu contre AI
	{
		controller.startVsAi();
		menu.getFrame().dispose();
	}

	private void playOnline() // fonction qui lance jeu multi-joueur
	{
		new OnlineView(controller, this);
		this.menu.getFrame().setVisible(false);
	}
	
	private void optionMenu() { // fonction qui lance jeu multi-joueur
		new OptionMenu();
		Debug.log("menu");
	}

	public void visible()
	{
		menu.getFrame().setVisible(true);
	}

	private void exit()	// fonction de sortie du jeu
	{
		menu.getFrame().dispose();
	}
}