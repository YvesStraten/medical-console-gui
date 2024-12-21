package com.yvesstraten.medicalconsolegui;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.components.SelectObjectDialog;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * This custom render overrides the combox box renderer for JOptionPane to allow for shorter
 * descriptions of objects in this application
 *
 * @author YvesStraten e2400068
 * @see SelectObjectDialog
 */
public class RemoveComboRenderer extends DefaultListCellRenderer {
  /** Constructor for RemoveComboRenderer. */
  public RemoveComboRenderer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public Component getListCellRendererComponent(
      JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    if (value instanceof MedicalFacility) {
      StringBuilder builder = new StringBuilder();
      MedicalFacility facility = (MedicalFacility) value;

      if (facility instanceof Clinic) {
        builder.append("Clinic");
      } else {
        builder.append("Hospital");
      }

      builder.append(" ").append(facility.getId()).append(" ").append(facility.getName());
      return super.getListCellRendererComponent(
          list, builder.toString(), index, isSelected, cellHasFocus);
    } else if (value instanceof Patient) {
      Patient patient = (Patient) value;
      return super.getListCellRendererComponent(
          list,
          "Patient " + patient.getId() + " named " + patient.getName(),
          index,
          isSelected,
          cellHasFocus);
    } else if (value instanceof Procedure) {
      Procedure procedure = (Procedure) value;
      String status = procedure.isElective() ? "Elective" : "Not elective";
      return super.getListCellRendererComponent(
          list,
          status
              + " Procedure "
              + procedure.getId()
              + " "
              + procedure.getName()
              + " and cost "
              + procedure.getCost(),
          index,
          isSelected,
          cellHasFocus);
    }

    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
  }
}
