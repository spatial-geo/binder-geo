package org.ilisi.geobinder.core.controllers;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.ilisi.geobinder.core.controllers.dtos.CreateProfileDTO;
import org.ilisi.geobinder.core.controllers.dtos.PointDTO;
import org.ilisi.geobinder.core.controllers.dtos.ProfileRespDTO;
import org.ilisi.geobinder.core.repositories.entities.Profile;
import org.ilisi.geobinder.core.services.IProfilesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/profile")
public class GeoBinderController {

  private final IProfilesService profilesService;

  public GeoBinderController(final IProfilesService profilesService) {
    this.profilesService = profilesService;
  }

  @PostMapping
  public ResponseEntity<ProfileRespDTO> createProfile(
      @RequestBody CreateProfileDTO createProfileDTO) {
    return new ResponseEntity<ProfileRespDTO>(
        profilesService.createProfile(
            createProfileDTO.fullName(), createProfileDTO.profession(), createProfileDTO.radius()),
        HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Profile>> getAllProfiles() {
    List<Profile> _profiles = new ArrayList<>();
    this.profilesService.getAllProfiles().forEach(profile -> _profiles.add(profile));
    return new ResponseEntity<>(_profiles, HttpStatus.OK);
  }

  @PostMapping("/position/{id}")
  public void insertCurrentPosition(@RequestBody PointDTO pointDTO, @PathVariable("id") String id) {
    this.profilesService.insertPoint(pointDTO.lon(), pointDTO.lat(), id);
  }
}
