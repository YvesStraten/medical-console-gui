package com.yvesstraten.medicalconsolegui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class MedicalTable extends JTable {
  public MedicalTable(){
    throw new UnsupportedOperationException("Unsupported constructor " + " please provide a model");
  }

  public MedicalTable(TableModel model){
    super(model);
    TableCellRenderer renderer = getDefaultRenderer(JButton.class);

    setDefaultRenderer(JButton.class, new MedicalTableButtonRenderer(renderer));
    setDefaultRenderer(JComboBox.class, new MedicalTableButtonRenderer(renderer));
  }
}
