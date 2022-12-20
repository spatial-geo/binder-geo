package org.ilisi.geobinder.core.services;

import org.ilisi.geobinder.core.repositories.entities.Profile;

public interface IProfilesService {

  Profile createProfile(String fullName, String profession);

  Iterable<Profile> getAllProfiles();

  void insertPoint(float lon, float lat, String profile_id);
}
