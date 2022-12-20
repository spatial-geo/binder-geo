package org.ilisi.geobinder.core.services;

import java.util.UUID;
import org.ilisi.geobinder.core.repositories.SpringProfessionsRepository;
import org.ilisi.geobinder.core.repositories.SpringProfilesRepository;
import org.ilisi.geobinder.core.repositories.entities.Profession;
import org.ilisi.geobinder.core.repositories.entities.Profile;

public class ProfilesService implements IProfilesService {

  private final SpringProfilesRepository springProfilesRepository;
  private final SpringProfessionsRepository springProfessionsRepository;

  public ProfilesService(
      SpringProfilesRepository springProfilesRepository,
      SpringProfessionsRepository springProfessionsRepository) {
    this.springProfilesRepository = springProfilesRepository;
    this.springProfessionsRepository = springProfessionsRepository;
  }

  @Override
  public Profile createProfile(String fullName, String profession) {
    Profession profession1 = this.springProfessionsRepository.save(new Profession(profession));
    return this.springProfilesRepository.save(new Profile(fullName, profession1));
  }

  @Override
  public Iterable<Profile> getAllProfiles() {
    return this.springProfilesRepository.findAll();
  }

  @Override
  public void insertPoint(float lon, float lat, String profile_id) {
    this.springProfilesRepository.insertPoint(lat, lon, UUID.fromString(profile_id));
  }
}
