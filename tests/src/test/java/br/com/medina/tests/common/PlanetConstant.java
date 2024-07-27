package br.com.medina.tests.common;

import br.com.medina.tests.domain.Planet;

public class PlanetConstant {
    public static final Planet PLANET = new Planet("name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");

}
