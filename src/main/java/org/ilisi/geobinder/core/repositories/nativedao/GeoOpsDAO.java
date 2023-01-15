package org.ilisi.geobinder.core.repositories.nativedao;

public interface GeoOpsDAO {

  String getLastGeomStringByProfileId(String profileId);

  String makeCircleWithRadiusAndCurrentPosition(double lon, double lat, double radius);

  boolean circleContainsPoint(
      double centerLon, double centerLat, double radius, double pointLon, double pointLat);
}
