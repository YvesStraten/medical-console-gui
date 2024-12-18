package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.yvesstraten.medicalconsole.HealthService;

public abstract class ObjectViewController {
    ObjectViewPanel view;
    HealthService model;

    public ObjectViewController(ObjectViewPanel panel, HealthService model) {
        setView(panel);
        setModel(model);

        view.editView(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            view.allowEdit();
          }
        });

        view.saveView(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              view.save();
          }
        });
    }

    public ObjectViewPanel getView() {
        return view;
    }

    public void setView(ObjectViewPanel view) {
        this.view = view;
    }

    public HealthService getModel() {
        return model;
    }

    public void setModel(HealthService model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ObjectViewController){
            ObjectViewController other = (ObjectViewController) obj;
            return other.getView().equals(this.getView()) &&
                other.getModel().equals(this.getModel());
        }

        return false;
    }
}
