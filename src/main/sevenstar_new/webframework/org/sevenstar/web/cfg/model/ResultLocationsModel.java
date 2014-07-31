package org.sevenstar.web.cfg.model;

import java.util.ArrayList;
import java.util.List;

public class ResultLocationsModel {
  private List locationsList;

  public void addLocationModel(ResultLocationModel locationModel) {
    if (this.locationsList == null) {
       this.locationsList = new ArrayList();
    }
    getLocationsList().add(locationModel);
  }

  public List getLocationsList() {
     if (this.locationsList == null) {
       this.locationsList = new ArrayList();
    }
     return this.locationsList;
  }

  public void setLocationsList(List locationsList) {
    this.locationsList = locationsList;
  }
}
