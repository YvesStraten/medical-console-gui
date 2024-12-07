package com.yvesstraten.medicalconsolegui.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;

public class ClinicViewPanel extends ObjectViewPanel {
    private Clinic clinic;

    public ClinicViewPanel(Clinic clinic){
        super();
        setClinic(clinic);
        JPanel drawnFields = drawFields();
        super.setFieldsPanel(drawFields());
        add(drawnFields);
    }

    public Clinic getClinic(){
        return this.clinic;
    }

    public void setClinic(Clinic clinic){
        this.clinic = clinic;
    }

    @Override
    public JPanel drawFields() {
        JPanel fieldContainer = new JPanel();
        JLabel name = new JLabel("Name");
        JTextArea nameTextArea = new JTextArea(getClinic().getName());
        fieldContainer.add(name);
        fieldContainer.add(nameTextArea);

        return fieldContainer;
    }
}
