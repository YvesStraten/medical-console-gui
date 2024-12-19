package com.yvesstraten.medicalconsolegui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.yvesstraten.medicalconsole.HealthService;

public class MainMenu extends JPanel {
  private HealthService service;

  public MainMenu(HealthService service) {
    setLayout(new BorderLayout(0, 30));
    ListPanel listPanel = new ListPanel(service);
    add(listPanel);
  }

  public HealthService getService() {
    return service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }
}
