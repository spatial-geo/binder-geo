package org.ilisi.geobinder.core.repositories.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "points")
public class PointC {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "the_geom", columnDefinition = "GEOMETRY")
  @JsonSerialize(using = GeometrySerializer.class)
  @JsonDeserialize(contentUsing = GeometryDeserializer.class)
  private Geometry point;

  @ManyToOne
  @JoinColumn(name = "profile_id", nullable = false)
  private Profile profile;
}
