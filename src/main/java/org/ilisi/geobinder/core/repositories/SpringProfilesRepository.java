package org.ilisi.geobinder.core.repositories;

import jakarta.transaction.Transactional;
import java.util.UUID;
import org.ilisi.geobinder.core.repositories.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringProfilesRepository extends JpaRepository<Profile, UUID> {

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(
      value =
          "INSERT INTO points (the_geom, profile_id) VALUES\n"
              + "  (ST_SetSRID(ST_MakePoint(:lon,:lat),4326), :p_id);",
      nativeQuery = true)
  void insertPoint(@Param("lon") double lon, @Param("lat") double lat, @Param("p_id") UUID p_id);
}
