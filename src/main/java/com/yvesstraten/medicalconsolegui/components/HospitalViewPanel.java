package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HospitalViewPanel extends MedicalFacilityViewPanel {
  public HospitalViewPanel(int id){
    this(new Hospital(id, "Please input a name"), new ArrayList<Procedure>());
  }

  public HospitalViewPanel(Hospital hospital, ArrayList<Procedure> procedures) {
    super(hospital);
    setSetProcedures(
      getFacility().getProcedures());

    // Initialize main labels
    JLabel nameLabel = new JLabel("Name");
    JLabel proceduresLabel = new JLabel("Selected procedures");

    // Initialize detail areas
    JTextField nameTextArea = new MedicalTextField(getFacility().getName(), 10);
    setInputComponents(new JTextField[] {nameTextArea});

    nameTextArea
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              @Override
              public void changedUpdate(DocumentEvent e) {}

              @Override
              public void insertUpdate(DocumentEvent e) {
                hospital.setName(nameTextArea.getText());
              }

              @Override
              public void removeUpdate(DocumentEvent e) {
                hospital.setName(nameTextArea.getText());
              }
            });

    // Available procedures
    ProcedureTableModel proceduresTableModel = new ProcedureTableModel(procedures);
    JTable proceduresTable = new JTable(proceduresTableModel);
    JScrollPane scrollpane = new JScrollPane(proceduresTable);

    // Add components to Panel
    add(nameLabel);
    add(nameTextArea);
    add(proceduresLabel);
    add(scrollpane);
  }

  public static HospitalViewPanel showAddPanel(int id) {
    HospitalViewPanel panel = new HospitalViewPanel(id);
    panel.enableInputComponents();

    return panel;
  }

  public static HospitalViewPanel showViewPanel(Hospital hospital, ArrayList<Procedure> allProcs) {
    HospitalViewPanel panel = new HospitalViewPanel(hospital, allProcs);
    panel.getEditButton().setEnabled(true);
    panel.getDeleteButton().setEnabled(true);

    return panel;
  }

  @Override
  public Hospital getFacility() {
    return (Hospital) super.getFacility();
  }

  public void setFacility(Hospital hospital) {
    super.setFacility(hospital);
  }

  private ArrayList<Procedure> setProcedures;

  public ArrayList<Procedure> getSetProcedures() {
    return setProcedures;
  }

  public void setSetProcedures(ArrayList<Procedure> setProcedures) {
    this.setProcedures = setProcedures;
  }

  @Override
  public void save() {}
}
