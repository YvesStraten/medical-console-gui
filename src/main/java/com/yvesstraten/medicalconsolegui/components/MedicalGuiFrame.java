package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MedicalGuiFrame extends JFrame {
  private HealthService service;

  public static String getNewTitle(HealthService service){
    String titleFormat = "HELP Medical console - currently managing "
    + "%s" + " facilities and %s patients";

    return String.format(titleFormat,
                        service.getMedicalFacilities().size()
                        , service.getPatients().size());
  }

  public MedicalGuiFrame(HealthService service) {
    // Frame setup
    super(getNewTitle(service));

    // Default options for this frame
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setService(service);

    // Setup tabs
    JTabbedPane tabbedPane = new JTabbedPane();
    // Setup the main menu
    JPanel mainMenu = new MainMenu(service, tabbedPane);

    // Setup menubar
    JMenuBar menuBar = new JMenuBar();
    JMenu customMenu = new JMenu("File");
    JMenuItem load = new JMenuItem("Load");

    load.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            MedicalFileChooser fileChooser = new MedicalFileChooser();
            int returnVal = fileChooser.showOpenDialog(menuBar);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
              fileChooser.load(service);
              setTitle(getNewTitle(service));
            }
          }
        });

    JMenuItem save = new JMenuItem("Save");
    save.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            MedicalFileChooser fileChooser = new MedicalFileChooser();
            fileChooser.save(service);
          }
        });

    customMenu.add(save);
    customMenu.add(load);
    menuBar.add(customMenu);

    setJMenuBar(menuBar);
    tabbedPane.addTab("Main", mainMenu);
    add(tabbedPane);
  }

  public HealthService getService() {
    return this.service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }
}
