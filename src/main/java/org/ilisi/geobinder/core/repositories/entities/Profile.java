package org.ilisi.geobinder.core.repositories.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String fullName;

  @ManyToOne
  @JoinColumn(name = "profession_id", nullable = false)
  private Profession profession;

  @OneToMany(mappedBy = "profile")
  private Set<PointC> trajectory;

  public Profile(String fullName, Profession profession) {
    this.fullName = fullName;
    this.profession = profession;
  }
}
