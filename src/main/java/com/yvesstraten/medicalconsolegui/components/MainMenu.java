package com.yvesstraten.medicalconsolegui.components;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.yvesstraten.medicalconsole.HealthService;

public class MainMenu extends JPanel {
  private HealthService service;

  public MainMenu(HealthService service) {
    super();
    setService(service);
    setLayout(new GridLayout(2, 2));
    
    // Instantiate components
    ListPanel listPanel = new ListPanel(service);
    AddButtonsPane addButtonsPane = new AddButtonsPane(listPanel);
    RemoveButtonsPane removeButtonsPane = new RemoveButtonsPane(listPanel);
    add(addButtonsPane);
    add(removeButtonsPane);
    add(listPanel);
  }

  public HealthService getService() {
    return service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }
}
