package br.com.medina.tests.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    @Autowired
    PlanetRepository planetRepository;

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }
}
