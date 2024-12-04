package com.yvesstraten.medicalconsolegui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.yvesstraten.medicalconsole.HealthService;

public abstract class ApplicationPane extends JPanel {
  private HealthService service;
  private JTabbedPane tabs;

  public ApplicationPane(HealthService service, JTabbedPane tabs){
    super();
    setService(service);
    setTabs(tabs);
  }

  public HealthService getService(){
    return this.service;
  }

  public JTabbedPane getTabs(){
    return this.tabs;
  }

  public void setService(HealthService service){
    this.service = service;
  }

  public void setTabs(JTabbedPane tabs){
    this.tabs = tabs;
  }
}
