package com.yvesstraten.medicalconsolegui;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class RemoveComboRenderer extends DefaultListCellRenderer {
  public RemoveComboRenderer() {
    super();
  }

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

    }     

    return super.getListCellRendererComponent(
        list, value, index, isSelected, cellHasFocus);
  }
}
