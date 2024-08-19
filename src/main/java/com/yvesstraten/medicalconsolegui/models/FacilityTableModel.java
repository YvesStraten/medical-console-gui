package com.yvesstraten.medicalconsolegui.models;

import java.util.ArrayList;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public class FacilityTableModel extends MedicalTableModel {
  private ArrayList<MedicalFacility> facilities;
  private final String[] columns =
      new String[] {"Id", "Name", "Type" };

  public FacilityTableModel(HealthService service) {
    super();
    setFacilities(service.getMedicalFacilities());
  }

  public ArrayList<MedicalFacility> getFacilities() {
    return this.facilities;
  }

  public void addFacility(MedicalFacility facility) {
    getFacilities().add(facility);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteFacility(int index) {
    getFacilities().remove(index);
    fireTableRowsDeleted(index, index);
  }

  public void updateFacility(int index, MedicalFacility facility){
    getFacilities().set(index, facility);
    fireTableRowsUpdated(index, index);
  }

  public void setFacilities(ArrayList<MedicalFacility> facilities) {
    this.facilities = facilities;
  }

  @Override
  public int getRowCount() {
    return getFacilities().size();
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
    if(getFacilities().isEmpty()){
      return null;
    }

    MedicalFacility selectedFacility = getFacilities().get(rowIndex);

    switch (columnIndex) {
      case 0:
        return selectedFacility.getId();
      case 1:
        return selectedFacility.getName();
      case 2: 
        return selectedFacility.getClass().getSimpleName();
    }

    return null;
  }

  @Override
  public Class<?> getColumnClass(int column) {
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }

}
