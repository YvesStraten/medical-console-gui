package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import java.util.List;
import javax.swing.JButton;

public class ClinicTableModel extends MedicalTableModel {
  private List<Clinic> clinics;
  private final String[] columns =
      new String[] {"Id", "Name", "Fee", "Gap percentage" };

  public ClinicTableModel(HealthService service) {
    super();
    setClinics(service.getClinics().toList());
  }

  public List<Clinic> getClinics() {
    return this.clinics;
  }

  public void addClinic(Clinic clinic) {
    getClinics().add(clinic);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteClinic(int index) {
    getClinics().remove(index);
    fireTableRowsDeleted(index, index);
  }

  public void setClinics(List<Clinic> clinics) {
    this.clinics = clinics;
  }

  @Override
  public int getRowCount() {
    return getClinics().size();
  }

  @Override
  public int getColumnCount() {
    return columns.length;
  }

  @Override
  public String getColumnName(int column) {
    return columns[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if(getClinics().isEmpty()){
      return null;
    }

    Clinic selectedClinic = getClinics().get(rowIndex);

    switch (columnIndex) {
      case 0:
        return selectedClinic.getId();
      case 1:
        return selectedClinic.getName();
      case 2:
        return selectedClinic.getFee();
      case 3:
        return (int) (selectedClinic.getGapPercent() * 100);
    }

    return null;
  }

  @Override
  public Class<?> getColumnClass(int column) {
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }
}
