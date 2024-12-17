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

  public HospitalViewPanel(Hospital hospital) {
    this(false, hospital, hospital.getProcedures());
  }

  public HospitalViewPanel(boolean isMutable, Hospital hospital, List<Procedure> procedures) {
    super(isMutable);
    setHospital(hospital);
    String name = hospital.getName();

    // Initialize main labels
    JLabel nameLabel = new JLabel("Name");
    JLabel proceduresLabel = new JLabel("Available procedures");
    JLabel selectedProceduresLabel = new JLabel("Selected Procedures");

    // Initialize detail areas
    JTextField nameTextArea = new JTextField(name);
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
    // Add procedures to selected procedures table
    selectProcedure.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int selectedRow = proceduresTable.getSelectedRow();
            Procedure selected =
                proceduresTableModel.getProcedures().get(selectedRow);
            proceduresTableModel.deleteProcedure(selectedRow);
            selectedProceduresModel.addProcedure(selected);
          }
        });

    JButton removeProcedure = new JButton("<-");
    // Move procedure back to the list of available procedures
    removeProcedure.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int selectedRow = selectedProceduresTable.getSelectedRow();
            Procedure selected =
                selectedProceduresModel
                    .getProcedures()
                    .get(selectedRow);
            proceduresTableModel.addProcedure(selected);
            selectedProceduresModel.deleteProcedure(selectedRow);
          }
        });

    // Update underlying hospital when
    // selected procedures change
    selectedProceduresModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        hospital.setProcedures(new ArrayList<Procedure>(selectedProceduresModel.getProcedures()));
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
