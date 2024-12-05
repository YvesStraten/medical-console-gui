package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(menuBar);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
              try {
                FileInputStream chosenFile = new FileInputStream(fileChooser.getSelectedFile());
                ObjectInputStream inputStream = new ObjectInputStream(chosenFile);
                Object read = inputStream.readObject();
                while (read != null) {
                  System.out.println(read.toString());
                  if (read instanceof Patient) {
                    service.addPatient((Patient) read);
                  } else if (read instanceof MedicalFacility) {
                    service.addMedicalFacility((MedicalFacility) read);
                  }
                  read = inputStream.readObject();
                }
              } catch (IOException ioe) {
                JOptionPane.showMessageDialog(fileChooser,
                                              "Error when loading file");
              } catch (ClassNotFoundException cnfe) {
                JOptionPane.showMessageDialog(fileChooser,
                                              "The file has invalid data!");
              }
              setTitle(getNewTitle(service));
            }
          }
        });

    JMenuItem save = new JMenuItem("Save");
    save.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            boolean success = false;
            while (!success) {
              int returnVal = fileChooser.showSaveDialog(menuBar);
              if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                  FileOutputStream chosenFile = new FileOutputStream(fileChooser.getSelectedFile());
                  ObjectOutputStream stream = new ObjectOutputStream(chosenFile);
                  System.out.println("Writing!");
                  for (Patient patient : service.getPatients()) {
                    System.out.println("Writing!" + patient.toString());
                    stream.writeObject(patient);
                  }

                  for (MedicalFacility facility : service.getMedicalFacilities()) {
                    System.out.println("Writing!" + facility.toString());
                    stream.writeObject(facility);
                  }

                  stream.close();

                  success = true;
                } catch(IOException ioe){
                  // It is very likely that most IOExceptions come from the user
                  // selecting a location that they cannot save to/path to save to
                  // does not exist
                  Object[] options = new Object[] { "Ok", "Cancel" };
                  int chosenOption = JOptionPane.showOptionDialog(fileChooser,
                                               "Could not write to file!, Please choose another location!",
                                               "Warning",
                                               JOptionPane.DEFAULT_OPTION,
                                               JOptionPane.WARNING_MESSAGE,
                                               null, options, options[0]);

                  if(chosenOption == 1 &&
                     chosenOption == JOptionPane.CANCEL_OPTION){
                    System.out.println("Abort saving");
                    success = true;
                  } else {
                    returnVal = fileChooser.showSaveDialog(menuBar);
                  }
                }
              } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                success = true;
              }
            }
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
