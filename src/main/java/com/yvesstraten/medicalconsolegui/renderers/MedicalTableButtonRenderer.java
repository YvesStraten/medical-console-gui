package com.yvesstraten.medicalconsolegui.renderers;

import javax.swing.JTable;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;

public class MedicalTableButtonRenderer implements TableCellRenderer {
  private final TableCellRenderer defaultRenderer;

  public MedicalTableButtonRenderer(TableCellRenderer renderer){
    defaultRenderer = renderer;
  }

  public Component getTableCellRendererComponent(JTable table,
    Object value,
    boolean isSelected, 
    boolean hasFocus,
    int row,
    int column
  ){
    if(value instanceof Component)
      return (Component) value;

    return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

  }
}
