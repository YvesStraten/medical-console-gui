package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class ViewObjectButton extends JButton {
    public ViewObjectButton(){
        super("View");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("You need to add tab adding with view!!");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("You need to add tab adding with view!!");
            }
        });
    }
}
