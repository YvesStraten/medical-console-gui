package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class ObjectViewPanel extends JPanel {
    private JButton deleteButton;
    private JButton editButton;

    public ObjectViewPanel(){
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        add(editButton);
        add(deleteButton);
        setDeleteButton(deleteButton);
        setEditButton(editButton);
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

    public void deleteView(ActionListener actionListener){
        deleteButton.addActionListener(actionListener);
    }

    public void editView(ActionListener actionListener){
        editButton.addActionListener(actionListener);
    }
}
