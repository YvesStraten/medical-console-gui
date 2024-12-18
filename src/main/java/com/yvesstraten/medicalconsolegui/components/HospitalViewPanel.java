package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class HospitalViewPanel extends ObjectViewPanel {
  private Hospital hospital;
  private JButton selectButton;
  private JButton deselectButton;
  private JTextField[] textFields;
  private String setName;

  public HospitalViewPanel(Hospital hospital, List<Procedure> procedures) {
    super();
    setHospital(hospital);
    String placeholderName = "Name of hospital";

    // Initialize main labels
    JLabel nameLabel = new JLabel("Name");
    JLabel proceduresLabel = new JLabel("Available procedures");
    JLabel selectedProceduresLabel = new JLabel("Selected Procedures");

    // Initialize detail areas
    JTextField nameTextArea = new MedicalTextField(placeholderName);
    textFields = new JTextField[] {nameTextArea};

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

    // Selected procedures
    ProcedureTableModel selectedProceduresModel =
        new ProcedureTableModel(new ArrayList<Procedure>());

    // Selected procedures
    JTable selectedProceduresTable = new JTable(selectedProceduresModel);

    // Scroll panes for the 2 tables
    JScrollPane scrollpane = new JScrollPane(proceduresTable);
    JScrollPane selectedProceduresScroll = new JScrollPane(selectedProceduresTable);

    // Buttons to select or remove procedures
    JButton selectProcedure = new JButton("->");
    setSelectButton(selectProcedure);
    // Add procedures to selected procedures table
    selectProcedure.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int selectedRow = proceduresTable.getSelectedRow();
            if (selectedRow != -1) {
              Procedure selected = proceduresTableModel.getProcedures().get(selectedRow);
              proceduresTableModel.deleteProcedure(selectedRow);
              selectedProceduresModel.addProcedure(selected);
            }
          }
        });

    JButton removeProcedure = new JButton("<-");
    setDeselectButton(removeProcedure);
    // Move procedure back to the list of available procedures
    removeProcedure.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int selectedRow = selectedProceduresTable.getSelectedRow();
            if (selectedRow != 1) {
              Procedure selected = selectedProceduresModel.getProcedures().get(selectedRow);
              proceduresTableModel.addProcedure(selected);
              selectedProceduresModel.deleteProcedure(selectedRow);
            }
          }
        });

    // Update underlying hospital when
    // selected procedures change
    selectedProceduresModel.addTableModelListener(
        new TableModelListener() {
          @Override
          public void tableChanged(TableModelEvent e) {
            setSetProcedures(
                new ArrayList<Procedure>(selectedProceduresModel.getProcedures()));
          }
        });

    // Add components to Panel
    add(nameLabel);
    add(nameTextArea);
    add(proceduresLabel);
    add(scrollpane);
    add(selectProcedure);
    add(removeProcedure);
    add(selectedProceduresLabel);
    add(selectedProceduresScroll);
  }

  public static HospitalViewPanel showAddPanel(Hospital hospital, List<Procedure> allProcs) {
    HospitalViewPanel panel = new HospitalViewPanel(hospital, allProcs);
    panel.preventMutations();

    return panel;
  }

  public static HospitalViewPanel showViewPanel(Hospital hospital, List<Procedure> allProcs) {
    HospitalViewPanel panel = new HospitalViewPanel(hospital, allProcs);
    panel.getEditButton().setEnabled(true);
    panel.getSelectButton().setEnabled(false);
    panel.getDeselectButton().setEnabled(false);
    panel.getDeleteButton().setEnabled(true);

    return panel;
  }

  public Hospital getHospital() {
    return hospital;
  }

  public void setHospital(Hospital hospital) {
    this.hospital = hospital;
  }

  public String getSetName() {
    return setName;
  }

  public void setSetName(String setName) {
    this.setName = setName;
  }

  private ArrayList<Procedure> setProcedures;

  public ArrayList<Procedure> getSetProcedures() {
    return setProcedures;
  }

  public void setSetProcedures(ArrayList<Procedure> setProcedures) {
    this.setProcedures = setProcedures;
  }


  public JButton getSelectButton() {
    return selectButton;
  }

  public void setSelectButton(JButton selectButton) {
    this.selectButton = selectButton;
  }

  public JButton getDeselectButton() {
    return deselectButton;
  }

  public void setDeselectButton(JButton deselectButton) {
    this.deselectButton = deselectButton;
  }

  @Override
  public void allowEdit() {
    textFields[0].setEnabled(true);
    getSelectButton().setEnabled(true);
    getDeselectButton().setEnabled(true);
  }


  @Override
  public void save() {
     getHospital().setName(getName());
     getHospital().setProcedures(getSetProcedures());
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
