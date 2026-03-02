package com.sofkianos.config;

/**
 * Configuración centralizada para los tests de Selenium.
 * Define URLs base y timeouts utilizados por las Page Objects.
 */
public final class TestConfig {

    private TestConfig() {
        // Utilidad, no instanciar
    }

    /** URL base de la aplicación bajo test. */
    public static final String BASE_URL = System.getProperty("app.url", "http://0.0.0.0:5173");

    /** URL de la página de listado de Kudos. */
    public static final String KUDOS_LIST_URL = BASE_URL + "/kudos/list";

    /** Timeout implícito en segundos para esperas de Selenium. */
    public static final int IMPLICIT_WAIT_SECONDS = 10;

    /** Timeout explícito en segundos para esperas condicionales. */
    public static final int EXPLICIT_WAIT_SECONDS = 15;
}
