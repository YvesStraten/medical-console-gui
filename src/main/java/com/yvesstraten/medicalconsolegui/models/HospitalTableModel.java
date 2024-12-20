package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HospitalTableModel extends MedicalTableModel {
  private ArrayList<Hospital> hospitals;
  private final String[] columns = new String[] {"Id", "Name", "ProbAdmit", "Num procedures"};

  public HospitalTableModel(HealthService service) {
    super(service);
    setHospitals(service.getHospitals().collect(Collectors.toCollection(ArrayList::new)));
  }

  public ArrayList<Hospital> getHospitals() {
    return this.hospitals;
  }

  public void setHospitals(ArrayList<Hospital> hospitals) {
    this.hospitals = hospitals;
  }

  public void addHospital(String name) {
    getService().initializeHospital(name);
    ArrayList<MedicalFacility> facilities = getService().getMedicalFacilities();
    Hospital hospital = (Hospital) facilities.get(facilities.size() - 1);
    getHospitals().add(hospital);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteHospital(int selectedRow) {
    Hospital hospital = getHospitals().get(selectedRow);
    getService().getMedicalFacilities().remove(hospital);
    getHospitals().remove(selectedRow);
    fireTableRowsDeleted(selectedRow, selectedRow);
  }

  public void deleteHospital(Hospital selected) {
    int row = getHospitals().indexOf(selected);
    getService().getMedicalFacilities().remove(selected);
    getHospitals().remove(selected);
    fireTableRowsDeleted(row, row);
  }

  public void setHospital(int selectedRow, Hospital hospital) {
    getHospitals().set(selectedRow, hospital);
    fireTableRowsUpdated(selectedRow, selectedRow);
  }

  @Override
  public int getRowCount() {
    return getHospitals().size();
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
    if (getHospitals().isEmpty()) {
      return null;
    }

    Hospital selectedHospital = getHospitals().get(rowIndex);
    switch (columnIndex) {
      case 0:
        return selectedHospital.getId();
      case 1:
        return selectedHospital.getName();
      case 2:
        return selectedHospital.getProbAdmit();
      case 3:
        return selectedHospital.getProcedures().size();
    }
    return null;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Hospital row = getHospitals().get(rowIndex);
    if (columnIndex == 1) {
      row.setName((String) aValue);
    } else if (columnIndex == 2) {
      row.setProbAdmit((Double) aValue);
    }
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex == 0 || columnIndex == 3) {
      return false;
    }

    return true;
  }

  @Override
  public Class<?> getColumnClass(int column) {
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }
}
