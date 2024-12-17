package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class ObjectViewPanel extends JPanel {
  private JButton deleteButton;
  private JButton editButton;
  private JButton saveButton;

  public ObjectViewPanel(){
    this(false);
  }

  public ObjectViewPanel(boolean isMutable) {
    JButton editButton = new JButton("Edit");
    JButton deleteButton = new JButton("Delete");
    JButton saveButton = new JButton("Save");

    add(editButton);
    add(deleteButton);
    add(saveButton);
    setDeleteButton(deleteButton);
    setEditButton(editButton);
    setSaveButton(saveButton);

    if(!isMutable){
      preventMutations();
    }
  }

  public JButton getEditButton() {
    return editButton;
  }

  public void setEditButton(JButton editButton) {
    this.editButton = editButton;
  }

  public JButton getDeleteButton() {
    return deleteButton;
  }

  public void setDeleteButton(JButton deleteButton) {
    this.deleteButton = deleteButton;
  }

  public JButton getSaveButton() {
    return saveButton;
  }

  public void setSaveButton(JButton saveButton) {
    this.saveButton = saveButton;
  }

  public void deleteView(ActionListener actionListener) {
    getDeleteButton().addActionListener(actionListener);
  }

  public void editView(ActionListener actionListener) {
    getEditButton().addActionListener(actionListener);
  }

  public void saveView(ActionListener actionListener){
    getSaveButton().addActionListener(actionListener);
  }

  public void preventMutations(){
    getEditButton().setEnabled(false);
    getDeleteButton().setEnabled(false);
  }
}
