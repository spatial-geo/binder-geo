package org.ilisi.geobinder.core;

import org.ilisi.geobinder.core.repositories.SpringGeoRepository;
import org.ilisi.geobinder.core.repositories.SpringProfessionsRepository;
import org.ilisi.geobinder.core.repositories.SpringProfilesRepository;
import org.ilisi.geobinder.core.repositories.nativedao.GeoOpsDAO;
import org.ilisi.geobinder.core.services.IProfilesService;
import org.ilisi.geobinder.core.services.ProfilesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {
  @Bean
  IProfilesService profilesService(
      SpringProfilesRepository springProfilesRepository,
      SpringProfessionsRepository springProfessionsRepository,
      SpringGeoRepository springGeoRepository,
      GeoOpsDAO geoOpsDAO) {
    return new ProfilesService(
        springProfilesRepository, springProfessionsRepository, springGeoRepository, geoOpsDAO);
  }
}
