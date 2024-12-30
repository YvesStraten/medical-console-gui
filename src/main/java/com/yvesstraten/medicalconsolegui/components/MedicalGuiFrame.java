package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.Refreshable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * This class acts as the main {@link JFrame} for the application
 *
 * @author YvesStraten e2400068
 */
public class MedicalGuiFrame extends JFrame {
  /**
   * Service used
   */
  private HealthService service;

  /**
   * This function returns a new title which can be used for this frame
   *
   * @param service service to query
   * @return title
   */
  public static String getNewTitle(HealthService service) {
    String titleFormat =
        "HELP Medical console - currently managing "
        + "%s" +
        " facilities and %s patients";

    return String.format(
        titleFormat,
        service.getMedicalFacilities().size(),
        service.getPatients().size());
  }

  /**
   * Constructor for MedicalGuiFrame.
   *
   * @param service service to use
   */
  public MedicalGuiFrame(HealthService service) {
    // Frame setup
    super(getNewTitle(service));

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
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
    setContentPane(mainMenu);
    mainMenu.setupListeners();

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

            HealthService service = getService();
            ListPanel listPanel = 
              mainMenu.getListPanel();

            TableModel[] models = 
              listPanel
                .getTableModels();
            
            for(TableModel model : models){
              // Can be refreshed and should
              if(model instanceof Refreshable){
                ((Refreshable) model).refresh();
              }
            }

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
    pack();

    // To spawn centered
    setLocationRelativeTo(null);
  }

  /**
   * Getter for the field <code>service</code>.
   *
   * @return service
   */
  public HealthService getService() {
    return this.service;
  }

  /**
   * Setter for the field <code>service</code>.
   *
   * @param service service to set
   */
  public void setService(HealthService service) {
    this.service = service;
  }
}
