package org.ilisi.geobinder.core.services.models;

import org.ilisi.geobinder.core.repositories.nativedao.GeoOpsDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// singleton class to store connected sessions
public class GeoCatalogue {

  private static GeoCatalogue catalogueInstance = null;
  private HashMap<String, ProfileSession> profileSessions;

  private GeoCatalogue() {
    this.profileSessions = new HashMap();
  }

  public static GeoCatalogue getCatalogueInstance() {
    if (catalogueInstance == null) catalogueInstance = new GeoCatalogue();

    return catalogueInstance;
  }

  public void addGeoSession(String sessionId, ProfileSession profileSession) {
    this.profileSessions.put(sessionId, profileSession);
  }

  public void updateCurrentPosition(String sessionId, String profileId, GeoPoint newPosition) {
    ProfileSession profileSession = this.profileSessions.get(sessionId);
    if (profileSession == null) {
      profileSession = new ProfileSession();
      profileSession.setIdUser(profileId);
    }
    profileSession.setCurrentPosition(newPosition);
    this.profileSessions.put(sessionId, profileSession);
  }

  public void destroySession(String sessionId) {
    this.profileSessions.remove(sessionId);
  }

  public List<String> getSessionNeighboursByRadius(GeoOpsDAO geoOpsDAO, double centerLon, double centerLat, double radius)
  {
    return this
            .profileSessions
            .entrySet()
            .stream()
            .filter(session ->
                    geoOpsDAO
                            .circleContainsPoint(
                                    centerLon,
                                    centerLat,
                                    radius,
                                    session.getValue().getCurrentPosition().getLon(),
                                    session.getValue().getCurrentPosition().getLat()))
            .map(Map.Entry::getKey).collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return this.profileSessions.toString();
  }
}
