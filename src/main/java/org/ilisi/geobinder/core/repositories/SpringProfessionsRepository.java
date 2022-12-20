package org.ilisi.geobinder.core.repositories;

import java.util.UUID;
import org.ilisi.geobinder.core.repositories.entities.Profession;
import org.springframework.data.repository.CrudRepository;

public interface SpringProfessionsRepository extends CrudRepository<Profession, UUID> {}
