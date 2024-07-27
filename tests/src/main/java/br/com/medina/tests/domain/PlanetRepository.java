package br.com.medina.tests.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    public Optional<Planet> findPlanetByName(String name);
}
