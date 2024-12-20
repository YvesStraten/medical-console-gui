package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ListPanel extends JPanel {
  private JTable currentTable;
  private JScrollPane scrollPane;
  private HospitalTableModel hospitalTableModel;
  private ClinicTableModel clinicTableModel;
  private PatientTableModel patientTableModel;
  private ProcedureTableModel procedureTableModel;

  public ListPanel(HealthService service) {
    setLayout(new GridLayout(2, 1));
    HospitalTableModel hospitalTableModel = new HospitalTableModel(service);
    ClinicTableModel clinicTableModel = new ClinicTableModel(service);
    PatientTableModel patientTableModel = new PatientTableModel(service);
    ProcedureTableModel procedureTableModel = new ProcedureTableModel(service);
    setHospitalTableModel(hospitalTableModel);
    setClinicTableModel(clinicTableModel);
    setPatientTableModel(patientTableModel);
    setProcedureTableModel(procedureTableModel);

    JTable listTable =
        new JTable(hospitalTableModel) {
          @Override
          public String getToolTipText(MouseEvent e) {
            String tip = null;
            Point mouse = e.getPoint();
            int rowIndex = rowAtPoint(mouse);
            int columnIndex = columnAtPoint(mouse);
            int realColumnIndex = convertColumnIndexToModel(columnIndex);
            int realRowIndex = convertRowIndexToModel(rowIndex);

            if (getModel() instanceof HospitalTableModel) {
              if (realColumnIndex == 3) {
                tip =
                    getProcedureTableModel().getProcedures().stream()
                        .map(procedure -> procedure.getName() + " " + procedure.getDescription())
                        .reduce("", (before, next) -> before + next + "\n");
              }
            } else if (getModel() instanceof PatientTableModel) {
              if (realColumnIndex == 4) {
                MedicalFacility facility =
                    getPatientTableModel().getPatients().get(realRowIndex).getCurrentFacility();
                if (facility != null) {
                  if (facility instanceof Clinic) {
                    Clinic clinic = (Clinic) facility;
                    tip =
                        "Clinic "
                            + clinic.getId()
                            + " "
                            + clinic.getName()
                            + " with cost "
                            + clinic.getFee();
                  } else {
                    tip = "Hospital" + facility.getId() + " " + facility.getName();
                  }
                }
              }
            }

            return tip;
          }
        };

    setCurrentTable(listTable);
    JScrollPane scrollPane = new JScrollPane(getCurrentTable());
    setScrollPane(scrollPane);

    JPanel selectorPanel = new JPanel();
    // ComboxBox to select between lists
    JComboBox<String> comboBox =
        new JComboBox<String>(new String[] {"Hospitals", "Clinics", "Patients", "Procedures"});

    comboBox.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              TableModel tableModel = null;
              int selectedIndex = comboBox.getSelectedIndex();

              if (selectedIndex == 0) {
                tableModel = hospitalTableModel;
              } else if (selectedIndex == 1) {
                tableModel = clinicTableModel;
              } else if (selectedIndex == 2) {
                tableModel = patientTableModel;
              } else {
                tableModel = procedureTableModel;
              }

              getCurrentTable().setModel(tableModel);
            }
          }
        });

    // Add components to selector panel
    selectorPanel.add(comboBox);

    // Add components to panel
    add(selectorPanel);
    add(scrollPane);
  }

  public JTable getCurrentTable() {
    return this.currentTable;
  }

  public JScrollPane getScrollPane() {
    return this.scrollPane;
  }

  public void setCurrentTable(JTable table) {
    this.currentTable = table;
  }

  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  public HospitalTableModel getHospitalTableModel() {
    return hospitalTableModel;
  }

  public void setHospitalTableModel(HospitalTableModel hospitalTableModel) {
    this.hospitalTableModel = hospitalTableModel;
  }

  public ClinicTableModel getClinicTableModel() {
    return clinicTableModel;
  }

  public void setClinicTableModel(ClinicTableModel clinicTableModel) {
    this.clinicTableModel = clinicTableModel;
  }

  public PatientTableModel getPatientTableModel() {
    return patientTableModel;
  }

  public void setPatientTableModel(PatientTableModel patientTableModel) {
    this.patientTableModel = patientTableModel;
  }

  public ProcedureTableModel getProcedureTableModel() {
    return procedureTableModel;
  }

  public void setProcedureTableModel(ProcedureTableModel procedureTableModel) {
    this.procedureTableModel = procedureTableModel;
  }
}
