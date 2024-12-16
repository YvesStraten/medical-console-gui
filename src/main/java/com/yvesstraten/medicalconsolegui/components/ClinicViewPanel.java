package com.yvesstraten.medicalconsolegui.components;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.yvesstraten.medicalconsole.facilities.Clinic;

public class ClinicViewPanel extends ObjectViewPanel {
    private Clinic clinic;

    public ClinicViewPanel(Clinic clinic){
        super();
        setClinic(clinic);
        String name = clinic.getName();
        Double fee = clinic.getFee();
        Double gapPercent = clinic.getGapPercent();

        // Initialize main labels
        JLabel nameLabel = new JLabel("Name");
        JLabel feeLabel = new JLabel("Clinic Fee");
        JLabel gapPercentLabel = new JLabel("Gap Percentage");

        // Initialize text areas
        JTextArea nameTextArea = new JTextArea(name);
        JTextArea feeTextArea = new JTextArea(fee.toString());
        JTextArea gapPercentTextArea = new JTextArea(gapPercent.toString());

        // Add components to Panel
        add(nameLabel);
        add(nameTextArea);
        add(feeLabel);
        add(feeTextArea);
        add(gapPercentLabel);
        add(gapPercentTextArea);
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ClinicViewPanel){
            ClinicViewPanel other = (ClinicViewPanel) obj;

            return other.getClinic().equals(this.getClinic());
        }

        return false;
    }
}
