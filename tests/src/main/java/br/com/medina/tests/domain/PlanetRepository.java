package br.com.medina.tests.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {

}
