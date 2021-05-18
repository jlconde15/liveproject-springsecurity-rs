package com.laurentiuspilca.liveproject.controllers;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.services.HealthProfileService;
// TODO import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/profile")
public class HealthProfileController {
  private static final Logger log = LoggerFactory.getLogger(HealthProfileController.class);

  private final HealthProfileService healthProfileService;

  public HealthProfileController(HealthProfileService healthProfileService) {
    this.healthProfileService = healthProfileService;
  }

  @PostMapping
  public void addHealthProfile(@RequestBody HealthProfile healthProfile) {
    healthProfileService.addHealthProfile(healthProfile);
  }

  @GetMapping()
  public HealthProfile findHealthProfile(Authentication a) {
    JwtAuthenticationToken token = (JwtAuthenticationToken) a;
    log.info("The principal is " + token.toString());
    Map<String, Object> attributes = token.getTokenAttributes();
    log.info("attributes " + attributes);

    Map<String, Object> claims = token.getToken().getClaims();
    log.info("claims " + claims);

    return healthProfileService.findHealthProfile(token.getToken().getClaims().get("user_name").toString());
  }

  @DeleteMapping("/{username}")
  // TODO re-enable after adding security
  // public void deleteHealthProfile(@PathVariable String username, Authentication a) {
  public void deleteHealthProfile(@PathVariable String username ) {
    healthProfileService.deleteHealthProfile(username);
  }
}
