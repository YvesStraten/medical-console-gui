package com.yvesstraten.medicalconsolegui.renderers;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;

import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;

public class MedicalTableButtonRenderer extends DefaultTableCellRenderer {
  public MedicalTableButtonRenderer(){
    super();
  }

  @Override
  public Component getTableCellRendererComponent(JTable table,
    Object value,
    boolean isSelected, 
    boolean hasFocus,
    int row,
    int column
  ){
    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

    ViewObjectButton viewButton = new ViewObjectButton();
    return viewButton;
  }
}
