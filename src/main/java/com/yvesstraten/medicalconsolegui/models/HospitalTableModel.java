package com.yvesstraten.medicalconsolegui.models;

import java.util.List;

import javax.swing.JList;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;

public class HospitalTableModel extends MedicalTableModel {
  private List<Hospital> hospitals;
  private final String[] columns = new String[] { "Id", "Name", "ProbAdmit", "Procedures"};

  public HospitalTableModel(HealthService service){
    super();
    setHospitals(service.getHospitals().toList());
  }

  public List<Hospital> getHospitals(){
    return this.hospitals;
  }

  public void setHospitals(List<Hospital> hospitals){
    this.hospitals = hospitals;
  }

  public void addHospital(Hospital hospital){
    getHospitals().add(hospital);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteHospital(int selectedRow){
    getHospitals().remove(selectedRow);
    fireTableRowsDeleted(selectedRow, selectedRow);
  }

  public void setHospital(int selectedRow, Hospital hospital){
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
  public String getColumnName(int column){
    return columns[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    if(getHospitals().isEmpty()){
      return null;
    }

    Hospital selectedHospital = getHospitals().get(rowIndex);
    switch(columnIndex){
      case 0: 
        return selectedHospital.getId();
      case 1: 
        return selectedHospital.getName();
      case 2: 
        return selectedHospital.getProbAdmit();
    }
    return null;
  }

  @Override 
  public Class<?> getColumnClass(int column){
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }
}
