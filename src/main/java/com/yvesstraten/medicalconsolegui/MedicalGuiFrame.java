package com.yvesstraten.medicalconsolegui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.yvesstraten.medicalconsole.HealthService;

public class MedicalGuiFrame extends JFrame {
  private HealthService service;

  public MedicalGuiFrame(HealthService service){
    // Frame setup
    super("HELP Medical console - currently managing " + service.getMedicalFacilities().size() + " facilities and " + service.getPatients().size() + " patients");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setService(service);

    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel mainMenu = new MainMenu(service, tabbedPane);
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
