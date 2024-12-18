package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.table.TableModel;

public abstract class ObjectViewController implements ActionListener {
  private ObjectViewPanel view;
  private int selectedRow;

  public ObjectViewController(ObjectViewPanel panel, int selectedRow) {
    setView(panel);
    setSelectedRow(selectedRow);
  }

  public ObjectViewPanel getView() {
    return view;
  }

  public void setView(ObjectViewPanel view) {
    this.view = view;
  }

  public int getSelectedRow() {
    return selectedRow;
  }

  public void setSelectedRow(int selectedRow) {
    this.selectedRow = selectedRow;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ObjectViewController) {
      ObjectViewController other = (ObjectViewController) obj;
      return other.getView().equals(this.getView());
    }

    return false;
  }
}
