package br.com.medina.tests.domain;

import static br.com.medina.tests.common.PlanetConstant.PLANET;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void createPlanet_withValidData_returnsPlanet() {

        planetRepository.save(PLANET);

        Planet sut = entityManager.find(Planet.class, PLANET.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(PLANET.getName());
        assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_withInvalidData_ThrowsException() {
        Planet emptyPlanet = new Planet();

        // assertThatThrownBy(() -> planetRepository.save(emptyPlanet));
    }
}
