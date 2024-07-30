package br.com.medina.tests.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.medina.tests.domain.Planet;
import br.com.medina.tests.domain.PlanetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody @Valid Planet planet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.create(planet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable("id") Long id) {
        return planetService
                .get(id)
                .map(planet -> ResponseEntity.ok().body(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> findPlanetByName(@PathVariable String name) {
        return planetService
                .findPlanetByName(name)
                .map(planet -> ResponseEntity.ok().body(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Planet>> list(@RequestParam(required = false) String terrain,
            @RequestParam(required = false) String climate) {
        List<Planet> planets = planetService.list(terrain, climate);
        return ResponseEntity.ok(planets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        planetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
