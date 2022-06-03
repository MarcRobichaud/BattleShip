package view;

import exception.SettingNotFoundException;
import model.audio.Music;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionMenu
{
    BFrame frame = new BFrame("BattleShip - Options");

    JPanel mainPanel = new JPanel();
    JPanel musicPanel = new EmptyJPanel();
    JPanel soundEffectPanel = new EmptyJPanel();
//    JPanel southPanel = new JPanel();

    JLabel musicLabel = new ViewBoardJLabel("Music Volume");
    JLabel soundEffectLabel = new ViewBoardJLabel("Sound effect Volume");

    JSlider musicSlider = new JSlider(0, 100);
    JSlider soundEffectSlider = new JSlider(0, 100);

//    JButton closeButton = new JButton("Close");

    public OptionMenu()
    {
        initFrame();
        setLayouts();
        addElementsToFrame();
        sliderSettings();
        backgroundSettings();
        initListener();
        frame.getFrame().setVisible(true);
    }

    private void initFrame()
    {
        frame.setSize(new Dimension(275, 225));
        frame.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getFrame().addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                close();
            }
        });
    }

    private void setLayouts()
    {
        mainPanel.setLayout(new GridLayout(0,1));
        musicPanel.setLayout(new BorderLayout());
        soundEffectPanel.setLayout(new BorderLayout());
//        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private void addElementsToFrame()
    {
        frame.add(mainPanel);
//        frame.add(southPanel, BorderLayout.SOUTH);

        mainPanel.add(musicPanel);
        mainPanel.add(soundEffectPanel);

        musicPanel.add(musicLabel, BorderLayout.NORTH);
        musicPanel.add(musicSlider, BorderLayout.CENTER);

        soundEffectPanel.add(soundEffectLabel, BorderLayout.NORTH);
        soundEffectPanel.add(soundEffectSlider, BorderLayout.CENTER);

//        southPanel.add(closeButton);
    }

    private void sliderSettings()
    {
        try
        {
            musicSlider.setValue(SettingFromFile.getSetting(Settings.MUSIC_VOLUME));
            soundEffectSlider.setValue(SettingFromFile.getSetting(Settings.SOUND_EFFECT_VOLUME));
        }
        catch (SettingNotFoundException e)
        {
            e.printStackTrace();
        }

        // foreground
        musicSlider.setForeground(GameColor.MenuTextColor);

        soundEffectSlider.setForeground(GameColor.MenuTextColor);

        // paint the ticks and tracks
        musicSlider.setPaintTrack(true);
        musicSlider.setPaintTicks(true);
        musicSlider.setPaintLabels(true);

        soundEffectSlider.setPaintTrack(true);
        soundEffectSlider.setPaintTicks(true);
        soundEffectSlider.setPaintLabels(true);

        // set spacing
        musicSlider.setMajorTickSpacing(50);
        musicSlider.setMinorTickSpacing(5);
        musicSlider.setSnapToTicks(true);

        soundEffectSlider.setMajorTickSpacing(50);
        soundEffectSlider.setMinorTickSpacing(5);
        soundEffectSlider.setSnapToTicks(true);
    }

    private void backgroundSettings()
    {
        mainPanel.setBackground(GameColor.viewBoardBackGround);
//        southPanel.setBackground(GameColor.viewBoardBackGround);
        musicSlider.setOpaque(false);
        soundEffectSlider.setOpaque(false);
    }

    private void initListener()
    {
        musicSlider.addChangeListener(e ->
        {
            float value = (musicSlider.getValue() + 0.1f) * 0.005f;//for conversion to decibel
            Music.control.setValue((float) Math.log10(value) * 20);
        });

        soundEffectSlider.addChangeListener(e -> Debug.log(Integer.toString(soundEffectSlider.getValue())));
//        closeButton.addActionListener(e -> close());
    }

    private void close()
    {
        SettingFromFile.setSetting(Settings.MUSIC_VOLUME, musicSlider.getValue());
        SettingFromFile.setSetting(Settings.SOUND_EFFECT_VOLUME, soundEffectSlider.getValue());
        frame.getFrame().dispose();
    }
}