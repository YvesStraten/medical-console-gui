package com.yvesstraten.medicalconsolegui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public class MedicalFileChooser extends JFileChooser {
    private SaveType saveType;

    /**
     * Default constructor
     * Sets the save type as binary
     * @see SaveType
    */
    public MedicalFileChooser(){
        this(SaveType.BINARY);
    }

    /**
     * Alternate constructor
     * Sets the save type to input type
     * @param saveType type of save wanted
     * @see SaveType
    */
    public MedicalFileChooser(SaveType saveType){
        super();
        setSaveType(saveType);
    }

    public SaveType getSaveType(){
        return this.saveType;
    }

    public void setSaveType(SaveType saveType){
        this.saveType = saveType;
    }

    /**
     * Enum representing all possible
     * save types
    */
    public enum SaveType {
        BINARY,
        TEXT
    }

    /**
     * {@inheritDoc}
    */
    @Override
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.add(createExtraPanel(), BorderLayout.SOUTH);

        dialog.pack();
        return dialog;
    }

    private JPanel createExtraPanel(){
        JPanel panel = new JPanel();
        boolean selected = getSaveType() == SaveType.BINARY ? true : false;
        JCheckBox saveType = new JCheckBox("Save as binary file", selected);
        saveType.addItemListener(new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            SaveType newType = getSaveType() == SaveType.BINARY
              ? SaveType.TEXT
              : SaveType.BINARY;
            setSaveType(newType);

            System.out.println(getSaveType().toString());
          }
        });

        panel.add(saveType);
        return panel;
    }

    // TODO implement
    private void loadText(HealthService service){
    }

    private void loadBinary(HealthService service)
      throws IOException, ClassNotFoundException {
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

    // TODO Implement
    private void saveText(HealthService service){

    }

    private void saveBinary(HealthService service)
     throws IOException {
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

    public void save(HealthService service){
        boolean success = false;
        while (!success) {
          int returnVal = showSaveDialog(this);
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                if(getSaveType() == SaveType.BINARY){
                    saveBinary(service);
                } else {
                    saveText(service);
                }
              success = true;
            } catch(IOException ioe){
              // It is very likely that most IOExceptions come from the user
              // selecting a location that they cannot save to/path to save to
              // does not exist
              Object[] options = new Object[] { "Ok", "Cancel" };
              int chosenOption = JOptionPane.showOptionDialog(this,
                                           "Could not write to file!, Please choose another location!",
                                           "Warning",
                                           JOptionPane.DEFAULT_OPTION,
                                           JOptionPane.WARNING_MESSAGE,
                                           null, options, options[0]);

              if(chosenOption == 1 &&
                 chosenOption == JOptionPane.CANCEL_OPTION){
                System.out.println("Abort saving");
                success = true;
              }
            }
          } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            success = true;
          }
        }

    }

    public void load(HealthService service){
        try {
            if(getSaveType() == SaveType.BINARY){
                loadBinary(service);
            } else
                loadText(service);
        } catch (IOException ioe) {
          JOptionPane.showMessageDialog(this,
                                        "Error when loading file");
        } catch (ClassNotFoundException cnfe) {
          JOptionPane.showMessageDialog(this,
                                        "The file has invalid data!");
        }
    }
}
