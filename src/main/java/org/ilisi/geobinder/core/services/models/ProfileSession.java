package org.ilisi.geobinder.core.services.models;

import lombok.Data;

@Data
public class ProfileSession {
  private String idUser;
  private String fullName;
  private String profession;
  private double lon;
  private double lat;
}
