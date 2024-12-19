package com.yvesstraten.medicalconsolegui.models;

import javax.swing.table.AbstractTableModel;

import com.yvesstraten.medicalconsole.HealthService;

public abstract class MedicalTableModel extends AbstractTableModel {
  private HealthService service;

  public MedicalTableModel(HealthService service) {
    super();
    setService(service);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  public HealthService getService() {
    return service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }
}
