package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.yvesstraten.medicalconsole.HealthService;

public abstract class ObjectViewPanel extends JPanel {
    private JPanel fieldsPanel;

    public ObjectViewPanel(){
        super();
        add(drawButtonsPanel());
    }

    public JPanel getFieldsPanel(){
        return this.fieldsPanel;
    }

    public void setFieldsPanel(JPanel panel){
        this.fieldsPanel = panel;
    }

    public JPanel drawButtonsPanel(){
        JPanel container = new JPanel();
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        container.add(editButton);
        container.add(deleteButton);
        return container;
    }

    public abstract JPanel drawFields();
}
