package br.com.medina.tests.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.medina.tests.domain.Planet;
import br.com.medina.tests.domain.PlanetService;

@RestController
@RequestMapping(path = "/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        return ResponseEntity.ok().body(planetService.create(planet));
    }
}
