package org.ilisi.geobinder.core.services.models;

import java.util.*;
import org.ilisi.geobinder.core.controllers.dtos.ProfileRespDTO;
import org.ilisi.geobinder.core.repositories.nativedao.GeoOpsDAO;
import org.ilisi.geobinder.core.services.IProfilesService;

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

  public void updateCurrentPosition(
      String sessionId, IProfilesService profilesService, String profileId, GeoPoint newPosition) {
    ProfileSession profileSession = this.profileSessions.get(sessionId);
    if (profileSession == null) {

      ProfileRespDTO profileRespDTO = profilesService.getProfileById(UUID.fromString(profileId));

      profileSession = new ProfileSession();
      profileSession.setIdUser(profileId);
      profileSession.setFullName(profileRespDTO.fullName());
      profileSession.setProfession(profileRespDTO.profession());
    }
    profileSession.setLon(newPosition.lon);
    profileSession.setLat(newPosition.lat);
    this.profileSessions.put(sessionId, profileSession);
  }

  public void destroySession(String sessionId) {
    this.profileSessions.remove(sessionId);
  }

  public List<ProfileSession> getSessionNeighboursByRadius(
      String currentSession,
      GeoOpsDAO geoOpsDAO,
      double centerLon,
      double centerLat,
      double radius) {

    List<ProfileSession> neighbours = new ArrayList<>();
    for (Map.Entry<String, ProfileSession> profileSessionPair : this.profileSessions.entrySet()) {
      if (!profileSessionPair.getKey().equals(currentSession)
          && profileSessionPair.getValue() != null
          && geoOpsDAO.circleContainsPoint(
              centerLon,
              centerLat,
              radius,
              profileSessionPair.getValue().getLon(),
              profileSessionPair.getValue().getLat()))
        neighbours.add(profileSessionPair.getValue());
    }
    return neighbours;
  }

  @Override
  public String toString() {
    return this.profileSessions.toString();
  }
}
