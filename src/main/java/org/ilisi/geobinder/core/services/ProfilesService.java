package org.ilisi.geobinder.core.services;

import java.util.UUID;
import org.ilisi.geobinder.core.controllers.dtos.ProfileRespDTO;
import org.ilisi.geobinder.core.repositories.SpringGeoRepository;
import org.ilisi.geobinder.core.repositories.SpringProfessionsRepository;
import org.ilisi.geobinder.core.repositories.SpringProfilesRepository;
import org.ilisi.geobinder.core.repositories.entities.Profession;
import org.ilisi.geobinder.core.repositories.entities.Profile;
import org.ilisi.geobinder.core.repositories.nativedao.GeoOpsDAO;

public class ProfilesService implements IProfilesService {

  private final SpringProfilesRepository springProfilesRepository;
  private final SpringProfessionsRepository springProfessionsRepository;
  private final SpringGeoRepository springGeoRepository;
  private final GeoOpsDAO geoOpsDAO;

  public ProfilesService(
      SpringProfilesRepository springProfilesRepository,
      SpringProfessionsRepository springProfessionsRepository,
      SpringGeoRepository springGeoRepository,
      GeoOpsDAO geoOpsDAO) {
    this.springProfilesRepository = springProfilesRepository;
    this.springProfessionsRepository = springProfessionsRepository;
    this.springGeoRepository = springGeoRepository;
    this.geoOpsDAO = geoOpsDAO;
  }

  @Override
  public ProfileRespDTO createProfile(String fullName, String profession, float radius) {
    Profession profession1 = this.springProfessionsRepository.save(new Profession(profession));
    Profile _profile =
        this.springProfilesRepository.save(new Profile(fullName, profession1, radius));
    return new ProfileRespDTO(
        _profile.getId().toString(),
        _profile.getFullName(),
        _profile.getProfession().getName(),
        _profile.getRadius());
  }

  @Override
  public Iterable<Profile> getAllProfiles() {
    return this.springProfilesRepository.findAll();
  }

  @Override
  public void insertPoint(double lon, double lat, String profile_id) {

    this.springProfilesRepository.insertPoint(lon, lat, UUID.fromString(profile_id));
    String geom = geoOpsDAO.getLastGeomStringByProfileId(profile_id);
    String circleGeom = geoOpsDAO.makeCircleWithRadiusAndCurrentPosition(lon, lat, 6);
    boolean isContains = geoOpsDAO.circleContainsPoint(lon, lat, 6, lon, lat);

    System.out.println("The point geom : " + circleGeom);
    System.out.println("The circle contains point : " + isContains);
  }
}
