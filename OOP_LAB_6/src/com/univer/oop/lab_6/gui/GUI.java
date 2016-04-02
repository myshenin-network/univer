package com.univer.oop.lab_6.gui;

import com.univer.oop.lab_6.driver.MergeDriver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GUI {
    private JFrame mainFrame;
    private JPanel content;
    private JLabel mergeTitle;
    private JTextField mergedFileName;
    private JButton selectButton;
    private JButton mergeButton;
    private JFileChooser fileChooser;
    private MergeDriver mergeDriver;

    public void makeGUI(){
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        mainFrame = new JFrame("Лаб. №6 - Злиття файлів");
        mainFrame.setBounds(300, 300, 270, 130);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content = new JPanel(null);

        mergeTitle = new JLabel("Злиття файів: ");
        mergeTitle.setBounds(10, 10, 100, 20);
        content.add(mergeTitle);

        mergedFileName = new JTextField();
        mergedFileName.setBounds(110, 10, 130, 20);
        content.add(mergedFileName);

        selectButton = new JButton("Вибрати..");
        selectButton.setBounds(10, 40, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showDialog(null, "Вибрати") == JFileChooser.APPROVE_OPTION)
                    mergeDriver = new MergeDriver(fileChooser.getSelectedFiles());
            }
        });
        content.add(selectButton);

        mergeButton = new JButton("Злити");
        mergeButton.setBounds(140, 40, 100, 30);
        mergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mergeDriver.merge("D:\\univer\\OOP_LAB_6\\src\\com\\univer\\oop\\lab_6\\res\\" + mergedFileName.getText());
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        content.add(mergeButton);

        mainFrame.setContentPane(content);
        mainFrame.setVisible(true);
    }
}
