package br.com.medina.tests.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

/*
 * Dependencias usadas para carregar testes usando o spring, mas nesse caso não precisamos
 * o Spring carrega muitas coisas que podem se tornar pesadas caso o projeto escale, logo iremos
 * passar a usar somente o mockito.
 */
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.medina.tests.common.PlanetConstant.PLANET;
import static br.com.medina.tests.common.PlanetConstant.INVALID_PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
// A annotaion abaixo indica que o sprint precisa escanear o projeto a procura
// de beans
// No caso eu indiquei que preciso somente do bean PlanetService
// @SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    // @Autowired
    @InjectMocks
    private PlanetService planetService;

    // @MockBean
    @Mock
    private PlanetRepository planetRepository;

    // operacao_estado_retorno
    @Test
    public void createPlanet_withValidData_returnsPlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet planet = planetService.create(PLANET);
        assertThat(planet).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_withInvalidData_ThrowsException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET));

        Optional<Planet> planet = planetService.get(1L);
        assertThat(planet).isNotEmpty();
        assertThat(planet.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Planet> planet = planetService.get(1L);
        assertThat(planet).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findPlanetByName(anyString())).thenReturn(Optional.of(PLANET));
        Optional<Planet> planet = planetService.findPlanetByName("name");
        assertThat(planet).isNotEmpty();
        assertThat(planet.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        when(planetRepository.findPlanetByName(anyString())).thenReturn(Optional.empty());
        Optional<Planet> planet = planetService.findPlanetByName("name");
        assertThat(planet).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        List<Planet> planets = new ArrayList<>() {
            {
                add(PLANET);
            }
        };
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void listPlanets_ReturnsNoPlanets() {
        when(planetRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException() {
        assertThatCode(() -> planetService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        doThrow(new RuntimeException()).when(planetRepository).deleteById(10L);
        assertThatThrownBy(() -> planetService.delete(1L)).isInstanceOf(RuntimeException.class);
    }
}
