package org.ilisi.geobinder.core.services;

import org.ilisi.geobinder.core.controllers.dtos.ProfileRespDTO;
import org.ilisi.geobinder.core.repositories.entities.Profile;

public interface IProfilesService {

  ProfileRespDTO createProfile(String fullName, String profession, float radius);

  Iterable<Profile> getAllProfiles();

  void insertPoint(double lon, double lat, String profile_id);
}
