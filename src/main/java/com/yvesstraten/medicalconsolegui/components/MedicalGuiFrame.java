package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * <p>MedicalGuiFrame class.</p>
 *
 * @author YvesStraten e2400068
 */
public class MedicalGuiFrame extends JFrame {
  private HealthService service;

  /**
   * This function returns a new title 
   * which can be used for this frame
   *
   * @param service service to query 
   * @return title 
   */
  public static String getNewTitle(HealthService service) {
    String titleFormat =
        "HELP Medical console - currently managing " + "%s" + " facilities and %s patients";

    return String.format(
        titleFormat, service.getMedicalFacilities().size(), service.getPatients().size());
  }

  /**
   * <p>Constructor for MedicalGuiFrame.</p>
   *
   * @param service service to use 
   */
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
            HospitalTableModel hospitalTableModel =
                new HospitalTableModel(getService(), new ProcedureTableModel(getService()));
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

  /**
   * <p>Getter for the field <code>service</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsole.HealthService} object
   */
  public HealthService getService() {
    return this.service;
  }

  /**
   * <p>Setter for the field <code>service</code>.</p>
   *
   * @param service a {@link com.yvesstraten.medicalconsole.HealthService} object
   */
  public void setService(HealthService service) {
    this.service = service;
  }
}
