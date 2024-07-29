package br.com.medina.tests.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    public Optional<Planet> findPlanetByName(String name);

    @Override
    <S extends Planet> List<S> findAll(Example<S> example);
}
