package com.laurentiuspilca.liveproject.controllers;

import com.laurentiuspilca.liveproject.entities.HealthMetric;
import com.laurentiuspilca.liveproject.services.HealthMetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metric")
public class HealthMetricController {

  private static final Logger log = LoggerFactory.getLogger(HealthMetricController.class);
  private final HealthMetricService healthMetricService;

  public HealthMetricController(HealthMetricService healthMetricService) {
    this.healthMetricService = healthMetricService;
  }

  @PostMapping
  public void addHealthMetric(@RequestBody HealthMetric healthMetric) {
    healthMetricService.addHealthMetric(healthMetric);
  }

  @GetMapping("/{username}")
  public List<HealthMetric> findHealthMetrics(@PathVariable String username, Authentication principal) {
    JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
    log.info("The principal is " + token.toString());
    Map<String, Object> attributes = token.getTokenAttributes();
    log.info("attributes " + attributes);

    Map<String, Object> claims = token.getToken().getClaims();
    log.info("claims " + claims);
    return healthMetricService.findHealthMetricHistory(token.getToken().getClaims().get("user_name").toString());
  }

  @DeleteMapping("/{username}")
  public void deleteHealthMetricForUser(@PathVariable String username) {
    healthMetricService.deleteHealthMetricForUser(username);
  }
}
