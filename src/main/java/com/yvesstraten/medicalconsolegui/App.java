package com.yvesstraten.medicalconsolegui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.yvesstraten.medicalconsole.Patient;

public class App {
    public static void main(String[] args) {
		Patient patient = new Patient(0, "Test", true, 300);
		JFrame frame = new JFrame("Test");
		frame.add(new JLabel(patient.getName()));
		frame.doLayout();
		frame.setVisible(true);
    }
}
