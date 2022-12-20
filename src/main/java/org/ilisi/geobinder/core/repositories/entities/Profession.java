package org.ilisi.geobinder.core.repositories.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "professions")
public class Profession {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String name;

  @OneToMany(mappedBy = "profession")
  private Set<Profile> profiles;

  public Profession(String name) {
    this.name = name;
  }
}
