package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class MedicalGuiFrame extends JFrame {
  private HealthService service;

  public static String getNewTitle(HealthService service) {
    String titleFormat =
        "HELP Medical console - currently managing " + "%s" + " facilities and %s patients";

    return String.format(
        titleFormat, service.getMedicalFacilities().size(), service.getPatients().size());
  }

  public MedicalGuiFrame(HealthService service) {
    // Frame setup
    super(getNewTitle(service));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setService(service);

    // Setup the main menu
    MainMenu mainMenu = new MainMenu(service);
    mainMenu
        .getListPanel()
        .setUpListeners(
            new TableModelListener() {

              @Override
              public void tableChanged(TableModelEvent e) {
                setTitle(getNewTitle(getService()));
              }
            });

    // Setup menubar
    JMenuBar menuBar = new JMenuBar();
    JMenu customMenu = new JMenu("File");
    JMenuItem load = new JMenuItem("Load");

    load.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            MedicalFileChooser fileChooser = new MedicalFileChooser();
            fileChooser.load(service);
            HospitalTableModel hospitalTableModel = new HospitalTableModel(getService());
            mainMenu.getListPanel().setHospitalTableModel(hospitalTableModel);
            mainMenu.getListPanel().setClinicTableModel(new ClinicTableModel(getService()));
            mainMenu.getListPanel().getCurrentTable().setModel(hospitalTableModel);
            setTitle(getNewTitle(service));
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

    // Add components
    customMenu.add(save);
    customMenu.add(load);
    menuBar.add(customMenu);
    setJMenuBar(menuBar);
    add(mainMenu, BorderLayout.NORTH);
    pack();
  }

  public HealthService getService() {
    return this.service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }
}
