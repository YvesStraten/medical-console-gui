package com.yvesstraten.medicalconsolegui.components;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.operations.EditOperations;

public class ClinicViewPanel extends ObjectViewPanel {
    public ClinicViewPanel(Clinic clinic){
        super();
        System.out.println(clinic.toString());
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
}
