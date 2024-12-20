package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClinicTableModel extends MedicalTableModel {
  private ArrayList<Clinic> clinics;
  private final String[] columns = new String[] {"Id", "Name", "Fee", "Gap percentage (%)"};

  public ClinicTableModel(HealthService service) {
    super(service);
    setClinics(service.getClinics().collect(Collectors.toCollection(ArrayList::new)));
  }

  public ArrayList<Clinic> getClinics() {
    return this.clinics;
  }

  public void setClinics(ArrayList<Clinic> clinics) {
    this.clinics = clinics;
  }

  public void addClinic(String name, double fee, double gapPercentage) {
    getService().initializeClinic(name, fee, gapPercentage);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteClinic(int index) {
    Clinic clinic = getClinics().get(index);
    getClinics().remove(index);
    getService().getMedicalFacilities().remove(clinic);
    fireTableRowsDeleted(index, index);
  }

  public void deleteClinic(Clinic selected) {
    int row = getClinics().indexOf(selected);
    getClinics().remove(selected);
    getService().getMedicalFacilities().remove(selected);
    fireTableRowsDeleted(row, row);
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
    if (getClinics().isEmpty()) {
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
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    System.out.println("Editing");
    Clinic row = getClinics().get(rowIndex);
    switch (columnIndex) {
      case 1:
        row.setName((String) aValue);
        break;
      case 2:
        row.setFee((Double) aValue);
        break;
      case 3:
        row.setGapPercent((Double) aValue);
        break;
    }
  }

  @Override
  public Class<?> getColumnClass(int column) {
    switch (column) {
      case 0:
        return Integer.class;
      case 1:
        return String.class;
      case 2:
        return Double.class;
      case 3:
        return Double.class;
      default:
        return Object.class;
    }
  }
}
