package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public abstract class MedicalFacilityViewPanel extends ObjectViewPanel {
  private MedicalFacility facility;

  public MedicalFacilityViewPanel(int id){
  }

  public MedicalFacilityViewPanel(MedicalFacility facility) {
    setFacility(facility);
  }

  public MedicalFacility getFacility() {
    return facility;
  }

  public void setFacility(MedicalFacility facility) {
    this.facility = facility;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MedicalFacilityViewPanel) {
      MedicalFacilityViewPanel other = (MedicalFacilityViewPanel) obj;

      return other.getFacility().equals(this.getFacility());
    }

    return false;
  }
}
