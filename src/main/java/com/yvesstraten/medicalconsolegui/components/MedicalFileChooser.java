package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class acts as a custom file chooser 
 * for saving and loading data to and fro 
 * this application
 * @author YvesStraten e2400068
 */
public class MedicalFileChooser extends JFileChooser {
  /** Extension filter for binary files */
  public static FileNameExtensionFilter binaryExtFilter =
      new FileNameExtensionFilter("Only meddat files", "meddat");

  /**
   * Default constructor, sets file filter 
   * to {@link binaryExtFilter}
   *
   */
  public MedicalFileChooser() {
    this(binaryExtFilter);
  }

  /**
   * Alternate constructor sets the save type to input type
   *
   * @param fileFilter file filter to set
   */
  public MedicalFileChooser(FileNameExtensionFilter fileFilter) {
    super();
    setFileFilter(fileFilter);
    setSelectedFile(new File("data.meddat"));
  }

  /**
   * This function will try to load the objects stored a binary file into the specified service
   *
   * @param service HealthService to load data to
   * @throws IOException if file has not been found are wrong or other I/O error
   * @throws ClassNotFoundException if file contains objects not defined in this program
   * @throws ClassCastException if any casting to the ArrayList fails
   */
  @SuppressWarnings("unchecked")
  private void loadBinary(HealthService service)
      throws IOException, ClassNotFoundException, ClassCastException {
    FileInputStream chosenFile = new FileInputStream(getSelectedFile());
    ObjectInputStream inputStream = new ObjectInputStream(chosenFile);
    Object read = inputStream.readObject();

    ArrayList<MedicalFacility> facilities = (ArrayList<MedicalFacility>) read;
    ArrayList<Patient> patients = (ArrayList<Patient>) read;
    service.setMedicalFacilities(facilities);
    service.setPatients(patients);

    inputStream.close();
    chosenFile.close();
  }

  /**
   * This function will try to save the objects stored in the specified service to a binary file
   *
   * @param service HealthService to save data from
   * @throws IOException if file has not been found are wrong or other I/O error
   */
  private void saveBinary(HealthService service) throws IOException {
    FileOutputStream chosenFile = new FileOutputStream(getSelectedFile());
    ObjectOutputStream stream = new ObjectOutputStream(chosenFile);

    stream.writeObject(service.getMedicalFacilities());
    stream.writeObject(service.getPatients());
    stream.close();
  }

  /**
   * This function will try to save the objects stored in the specified service to a binary or text
   * file
   *
   * @param service HealthService to save data from
   */
  public void save(HealthService service) {
    boolean success = false;
    while (!success) {
      int returnVal = showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        try {
          saveBinary(service);
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
            success = true;
          }
        }
      } else if (returnVal == JFileChooser.CANCEL_OPTION) {
        success = true;
      }
    }
  }

  /**
   * This function will try to load the file stored in a binary or textual manner into the specified
   * service
   *
   * @param service HealthService to load data into
   */
  public void load(HealthService service) {
    int returnVal = showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        loadBinary(service);

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
}
