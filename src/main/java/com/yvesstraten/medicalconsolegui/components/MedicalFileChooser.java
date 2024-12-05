package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MedicalFileChooser extends JFileChooser {
  private SaveType saveType;
  private static FileNameExtensionFilter textExtFilter =
      new FileNameExtensionFilter("Only text files", "txt");

  private static FileNameExtensionFilter binaryExtFilter =
      new FileNameExtensionFilter("Only meddat files", "meddat");

  /**
   * Default constructor Sets the save type as binary
   *
   * @see SaveType
   */
  public MedicalFileChooser() {
    this(SaveType.BINARY, binaryExtFilter);
  }

  /**
   * Alternate constructor Sets the save type to input type
   *
   * @param saveType type of save wanted
   * @see SaveType
   */
  public MedicalFileChooser(SaveType saveType, FileNameExtensionFilter fileFilter) {
    super();
    setSaveType(saveType);
    setFileFilter(fileFilter);

    if (getSaveType() == SaveType.BINARY) setSelectedFile(new File("data.meddat"));
    else setSelectedFile(new File("data.txt"));
  }

  public SaveType getSaveType() {
    return this.saveType;
  }

  public void setSaveType(SaveType saveType) {
    this.saveType = saveType;
  }

  /** Enum representing all possible save types */
  public enum SaveType {
    BINARY,
    TEXT
  }

  /** {@inheritDoc} */
  @Override
  protected JDialog createDialog(Component parent) throws HeadlessException {
    JDialog dialog = super.createDialog(parent);
    dialog.add(createExtraPanel(), BorderLayout.SOUTH);

    dialog.pack();
    return dialog;
  }

  private JPanel createExtraPanel() {
    JPanel panel = new JPanel();
    boolean selected = getSaveType() == SaveType.BINARY ? true : false;
    String message = null;
    if (getDialogType() == JFileChooser.SAVE_DIALOG) {
      message = "Save as binary file";
    } else if (getDialogType() == JFileChooser.OPEN_DIALOG) {
      message = "Load as binary file";
    }

    JCheckBox operationType = new JCheckBox(message, selected);
    operationType.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            SaveType newType = getSaveType() == SaveType.BINARY ? SaveType.TEXT : SaveType.BINARY;
            setSaveType(newType);
            setSelectedFile(
                new File(getSaveType() == SaveType.BINARY ? "data.meddat" : "data.txt"));
            setFileFilter(getSaveType() == SaveType.BINARY ? binaryExtFilter : textExtFilter);
          }
        });

    panel.add(operationType);
    return panel;
  }

  private void loadText(HealthService service) throws IOException {
    FileReader fr = new FileReader(getSelectedFile());
    BufferedReader bf = new BufferedReader(fr);
    String line = bf.readLine();
    while (line != null) {
      System.out.println("Current line " + line);
      String[] split = line.split(",");
      // Starting identifier
      String ident = split[0];
      if (ident.equals("PATIENT")) {
        int id = Integer.parseInt(split[1]);
        String name = split[2];
        double balance = Double.parseDouble(split[3]);
        boolean isPrivate = Boolean.parseBoolean(split[4]);
        String facilityId = split[5];
        MedicalFacility facility = null;

        if (!facilityId.equals(" ")) {
          // There is a facility entry
          int parsedId = Integer.parseInt(facilityId);
          facility =
              service
                  .getMedicalFacilitiesStream()
                  .filter(item -> service.hashCode() == parsedId)
                  .toList()
                  .get(0);
        }

        Patient patientToAdd = new Patient(id, name, isPrivate, balance, facility);

        service.addPatient(patientToAdd);
      } else if (ident.equals("CLINIC")) {
        int id = Integer.parseInt(split[1]);
        String name = split[2];
        double fee = Double.parseDouble(split[3]);
        double gapPercent = Double.parseDouble(split[4]);

        Clinic clinic = new Clinic(id, name, fee, gapPercent);
        service.addMedicalFacility(clinic);
      } else if (ident.equals("HOSPITAL")) {
        int id = Integer.parseInt(split[1]);
        String name = split[2];

        if (split[3].equals(" ")) {
          // No procedures associated with this
          // hospital
          Hospital hospital = new Hospital(id, name);
          service.addMedicalFacility(hospital);
        } else {
          ArrayList<Procedure> procedures = new ArrayList<Procedure>();

          int i = 4;
          System.out.println(split[split.length - 1]);
          while (i != split.length - 1) {
            int procedureId = Integer.parseInt(split[3]);
            String procedureName = split[4];
            String procedureDesc = split[5];
            boolean isElective = Boolean.parseBoolean(split[6]);
            double basicCost = Double.parseDouble(split[7]);

            Procedure procedure =
                new Procedure(procedureId, procedureName, procedureDesc, isElective, basicCost);

            procedures.add(procedure);
            i++;
          }
        }
      }

      line = bf.readLine();
    }

    bf.close();
  }

  private void loadBinary(HealthService service) throws IOException, ClassNotFoundException {
    FileInputStream chosenFile = new FileInputStream(getSelectedFile());
    ObjectInputStream inputStream = new ObjectInputStream(chosenFile);
    Object read = inputStream.readObject();
    while (read != null) {
      System.out.println(read.toString());
      if (read instanceof Patient) {
        service.addPatient((Patient) read);
      } else if (read instanceof MedicalFacility) {
        service.addMedicalFacility((MedicalFacility) read);
      }
      read = inputStream.readObject();
    }

    inputStream.close();
  }

  private void saveText(HealthService service) throws IOException {
    PrintWriter pw = new PrintWriter(getSelectedFile());

    StringBuilder toWrite = new StringBuilder();
    for (Clinic clinic : service.getClinics().toList()) {
      toWrite
          .append("CLINIC")
          .append(",")
          .append(clinic.getId())
          .append(",")
          .append(clinic.getName())
          .append(",")
          .append(clinic.getFee())
          .append(",")
          .append(clinic.getGapPercent())
          .append("\n");
    }

    for (Hospital hospital : service.getHospitals().toList()) {
      toWrite
          .append("HOSPITAL")
          .append(",")
          .append(hospital.getId())
          .append(",")
          .append(hospital.getName())
          .append(",");

      ArrayList<Procedure> procedures = hospital.getProcedures();
      if (procedures.size() == 0) {
        toWrite.append(",").append(" ").append(",");
      } else {
        for (Procedure procedure : hospital.getProcedures()) {
          toWrite
              .append(procedure.getId())
              .append(",")
              .append(procedure.getName())
              .append(",")
              .append(procedure.getDescription())
              .append(",")
              .append(procedure.isElective())
              .append(",")
              .append(procedure.getCost());
        }

        toWrite.append("\n");
      }

      // Last as patients depend on the facility already
      // being loaded to load themselves
      for (Patient patient : service.getPatients()) {
        toWrite
            .append("PATIENT")
            .append(",")
            .append(patient.getId())
            .append(",")
            .append(patient.getName())
            .append(",")
            .append(patient.getBalance())
            .append(",")
            .append(patient.isPrivate());

        MedicalFacility current = patient.getCurrentFacility();
        if (current == null) {
          toWrite.append(",").append(" ").append(",");
        } else {
          toWrite.append(",").append(current.getId());
        }

        toWrite.append("\n");
      }
    }

    System.out.print(toWrite.toString());
    pw.print(toWrite.toString());
    pw.close();
  }

  private void saveBinary(HealthService service) throws IOException {
    FileOutputStream chosenFile = new FileOutputStream(getSelectedFile());
    ObjectOutputStream stream = new ObjectOutputStream(chosenFile);
    System.out.println("Writing!");
    for (Patient patient : service.getPatients()) {
      System.out.println("Writing!" + patient.toString());
      stream.writeObject(patient);
    }

    for (MedicalFacility facility : service.getMedicalFacilities()) {
      System.out.println("Writing!" + facility.toString());
      stream.writeObject(facility);
    }

    stream.close();
  }

  public void save(HealthService service) {
    boolean success = false;
    while (!success) {
      int returnVal = showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        try {
          if (getSaveType() == SaveType.BINARY) {
            saveBinary(service);
          } else {
            saveText(service);
          }
          success = true;
        } catch (IOException ioe) {
          // It is very likely that most IOExceptions come from the user
          // selecting a location that they cannot save to/path to save to
          // does not exist
          Object[] options = new Object[] {"Ok", "Cancel"};
          int chosenOption =
              JOptionPane.showOptionDialog(
                  this,
                  "Could not write to file!, Please choose another location!",
                  "Warning",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.WARNING_MESSAGE,
                  null,
                  options,
                  options[0]);

          if (chosenOption == 1 && chosenOption == JOptionPane.CANCEL_OPTION) {
            System.out.println("Abort saving");
            success = true;
          }
        }
      } else if (returnVal == JFileChooser.CANCEL_OPTION) {
        success = true;
      }
    }
  }

  public void load(HealthService service) {
    try {
      if (getSaveType() == SaveType.BINARY) {
        loadBinary(service);
      } else loadText(service);

      // Loaded data successfully, we are done here
      JOptionPane.showMessageDialog(this, "Data has been loaded successfully!");
    } catch (IOException ioe) {
      System.err.println(ioe.toString());
      JOptionPane.showMessageDialog(this, "Error when loading file");
    } catch (ClassNotFoundException cnfe) {
      JOptionPane.showMessageDialog(this, "The file has invalid data!");
    }
  }
}
