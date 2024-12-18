package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class ObjectViewPanel extends JPanel {
  private DeleteObjectButton deleteButton;
  private EditObjectButton editButton;
  private SaveObjectButton saveButton;

  private JTextField[] textFields;
  public ObjectViewPanel() {
    EditObjectButton editButton = new EditObjectButton();
    DeleteObjectButton deleteButton = new DeleteObjectButton("Delete");
    SaveObjectButton saveButton = new SaveObjectButton();

    add(editButton);
    add(deleteButton);
    add(saveButton);
    setDeleteButton(deleteButton);
    setEditButton(editButton);
    setSaveButton(saveButton);
  }

  public EditObjectButton getEditButton() {
    return editButton;
  }

  public void setEditButton(EditObjectButton editButton) {
    this.editButton = editButton;
  }

  public DeleteObjectButton getDeleteButton() {
    return deleteButton;
  }

  public void setDeleteButton(DeleteObjectButton deleteButton) {
    this.deleteButton = deleteButton;
  }

  public SaveObjectButton getSaveButton() {
    return saveButton;
  }

  public void setSaveButton(SaveObjectButton saveButton) {
    this.saveButton = saveButton;
  }

  public JTextField[] getTextFields() {
    return textFields;
  }

  public void setTextFields(JTextField[] textFields) {
    this.textFields = textFields;
  }


  public void deleteView(ActionListener actionListener) {
    getDeleteButton().addActionListener(actionListener);
  }

  public void editView(ActionListener actionListener) {
    getEditButton().addActionListener(actionListener);
  }

  public abstract void allowEdit();

  public abstract void save();

  public void saveView(ActionListener actionListener){
    getSaveButton().addActionListener(actionListener);
  }

  public void preventMutations(){
    getEditButton().setEnabled(false);
    getDeleteButton().setEnabled(false);
  }
}
