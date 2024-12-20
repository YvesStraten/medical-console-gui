package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import javax.swing.table.AbstractTableModel;

public abstract class MedicalTableModel extends AbstractTableModel {
  private HealthService service;

  public MedicalTableModel(HealthService service) {
    super();
    setService(service);
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex != 0) {
      return true;
    }

    return false;
  }

  public HealthService getService() {
    return service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }
}
