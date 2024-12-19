package com.yvesstraten.medicalconsolegui.editors;

import com.yvesstraten.medicalconsolegui.components.SimulateVisitButton;
import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class VisitButtonEditor extends DefaultCellEditor {
  private ActionListener event;
  public VisitButtonEditor(ActionListener event) {
    super(new JCheckBox());
    this.event = event;
  }

  @Override
  public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column) {
    SimulateVisitButton action = new SimulateVisitButton();
    action.addActionListener(event);
    return action;
  }
}

