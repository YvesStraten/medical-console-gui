package com.yvesstraten.medicalconsolegui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;

public class HospitalTableModel extends AbstractTableModel {
  private List<Hospital> hospitals;
  private final String[] columns = new String[] { "Id", "Name", "ProbAdmit", "Procedures", "View"};

  public HospitalTableModel(List<Hospital> hospitals){
    super();
    setHospitals(hospitals);
  }

  public List<Hospital> getHospitals(){
    return this.hospitals;
  }

  public void setHospitals(List<Hospital> hospitals){
    this.hospitals = hospitals;
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
    Hospital selectedHospital = getHospitals().get(rowIndex);
    switch(columnIndex){
      case 0: 
        return selectedHospital.getId();
      case 1: 
        return selectedHospital.getName();
      case 2: 
        return selectedHospital.getProbAdmit();
      case 3: 
        return new JComboBox<Procedure>(selectedHospital.getProceduresStream().toArray(Procedure[]::new));
      case 4: 
        JButton view = new JButton("View"); 
        return view;
    }
    return null;
  }

  @Override 
  public Class<?> getColumnClass(int column){
    return getValueAt(0, column).getClass();
  }
}
