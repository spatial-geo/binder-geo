package org.ilisi.geobinder.core.services.models;

import lombok.Data;

@Data
public class ProfileSession {
  private String idUser;
  private GeoPoint currentPosition;
}
