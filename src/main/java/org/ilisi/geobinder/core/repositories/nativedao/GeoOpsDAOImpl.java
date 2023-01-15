package org.ilisi.geobinder.core.repositories.nativedao;

import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GeoOpsDAOImpl implements GeoOpsDAO {

  JdbcTemplate jdbcTemplate;

  private final int SRID_REF = 4326;
  private final int METRAGE = 900913;

  private final String SQL_GET_LAST_GEOM_STRING =
      "SELECT the_geom FROM public.points where profile_id = ? order by created_at desc limit 1";

  private final String SQL_MAKE_CIRCLE_BUFFER_WITH_RADIUS =
      "select ST_Buffer(ST_SetSRID(ST_MakePoint(?, ?), ?), ?)";

  private final String SQL_CIRCLE_CONTAINS_POINT =
      "SELECT st_intersects(st_transform(st_buffer(st_transform(ST_SetSRID(ST_MakePoint(?, ?), ?), ?),?),?), ST_SetSRID(ST_MakePoint(?, ?), ?))";

  public GeoOpsDAOImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public String getLastGeomStringByProfileId(String profileId) {
    return jdbcTemplate.queryForObject(
        SQL_GET_LAST_GEOM_STRING, new Object[] {UUID.fromString(profileId)}, String.class);
  }

  @Override
  public String makeCircleWithRadiusAndCurrentPosition(double lon, double lat, double radius) {
    return jdbcTemplate.queryForObject(
        SQL_MAKE_CIRCLE_BUFFER_WITH_RADIUS,
        new Object[] {lon, lat, SRID_REF, radius},
        String.class);
  }
  // lon, lat, ref, met, rad, ref, lon, lat, ref
  @Override
  public boolean circleContainsPoint(
      double centerLon, double centerLat, double radius, double pointLon, double pointLat) {
    return Boolean.TRUE.equals(
        jdbcTemplate.queryForObject(
            SQL_CIRCLE_CONTAINS_POINT,
            new Object[] {
              centerLon, centerLat, SRID_REF, METRAGE, radius, SRID_REF, pointLon, pointLat,
              SRID_REF
            },
            Boolean.class));
  }
}
