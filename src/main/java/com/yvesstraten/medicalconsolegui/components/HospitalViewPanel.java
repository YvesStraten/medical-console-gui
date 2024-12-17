package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class HospitalViewPanel extends ObjectViewPanel {
  private Hospital hospital;

  public HospitalViewPanel(Hospital hospital) {
    this(false, hospital);
  }

  public HospitalViewPanel(boolean isMutable, Hospital hospital) {
    super(isMutable);
    setHospital(hospital);
    String name = hospital.getName();
    Double probAdmit = hospital.getProbAdmit();
    List<Procedure> procedures = hospital.getProcedures();

    // Initialize main labels
    JLabel nameLabel = new JLabel("Name");
    JLabel probAdmitLabel = new JLabel("Probability of admission");
    JLabel proceduresLabel = new JLabel("Available procedures");

    // Initialize detail areas
    JTextArea nameTextArea = new JTextArea(name);
    JTextArea probAdmitTextArea = new JTextArea(probAdmit.toString());
    JTable proceduresTable = new JTable(new ProcedureTableModel(procedures));
    JScrollPane scrollpane = new JScrollPane(proceduresTable);

    // Add components to Panel
    add(nameLabel);
    add(nameTextArea);
    add(probAdmitLabel);
    add(probAdmitTextArea);
    add(proceduresLabel);
    add(scrollpane);
  }

  public Hospital getHospital() {
    return hospital;
  }

  public void setHospital(Hospital hospital) {
    this.hospital = hospital;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof HospitalViewPanel) {
      HospitalViewPanel other = (HospitalViewPanel) obj;
      return other.getHospital().equals(this.getHospital());
    }

    return false;
  }
}
