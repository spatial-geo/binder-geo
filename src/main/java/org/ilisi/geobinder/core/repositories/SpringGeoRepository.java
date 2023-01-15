package org.ilisi.geobinder.core.repositories;

import org.ilisi.geobinder.core.repositories.entities.PointC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringGeoRepository extends JpaRepository<PointC, Long> {

  //    @Transactional
  //    @Modifying(clearAutomatically = true)
  //    @Query("SELECT c.id, c.point, c. FROM PointC c WHERE profile.id=:profile_id ORDER BY
  // createdAt DESC")
  //    PointC findTopByOrderByCreatedAt(UUID profileId);
}
