package com.sofkianos.steps;

import com.sofkianos.driver.DriverManager;
import com.sofkianos.pages.KudosListPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions para los escenarios de listado de Kudos.
 * Cubre navegación, visibilidad de elementos y estructura de la tabla.
 */
public class KudosListSteps {

    private KudosListPage kudosListPage;

    @Dado("que navego a la página de listado de kudos")
    public void navegoALaPaginaDeListadoDeKudos() {
        kudosListPage = new KudosListPage(DriverManager.getDriver());
        kudosListPage.navigateTo();
    }

    @Dado("verifico que hay kudos disponibles")
    public void verificoQueHayKudosDisponibles() {
        assertThat(kudosListPage.getKudosCount())
                .as("Debe haber al menos un kudo disponible para ejecutar los tests")
                .isGreaterThan(0);
    }

    @Entonces("el título de la página debe ser {string}")
    public void elTituloDeLaPaginaDebeSer(String expectedTitle) {
        assertThat(kudosListPage.getTitleText())
                .as("El título de la página")
                .contains(expectedTitle.split(" "));
    }

    @Entonces("el formulario de filtros debe estar visible")
    public void elFormularioDeFiltrosDebeEstarVisible() {
        assertThat(kudosListPage.isFilterFormDisplayed())
                .as("El formulario de filtros debe ser visible")
                .isTrue();
    }

    @Entonces("la tabla de resultados debe estar visible")
    public void laTablaDeResultadosDebeEstarVisible() {
        assertThat(kudosListPage.isTableDisplayed())
                .as("La tabla de resultados debe ser visible")
                .isTrue();
    }

    @Entonces("la tabla debe tener las columnas {string}")
    public void laTablaDebeTenerLasColumnas(String expectedColumns) {
        List<String> headers = kudosListPage.getTableHeaderTexts().stream().map(String::toUpperCase).toList();
        String[] expected = java.util.Arrays.stream(expectedColumns.split(",\\s*")).map(String::toUpperCase)
                .toArray(String[]::new);
        assertThat(headers)
                .as("Los encabezados de la tabla")
                .containsExactlyInAnyOrder(expected);
    }

    @Entonces("el contador debe indicar que hay kudos disponibles")
    public void elContadorDebeIndicarQueHayKudosDisponibles() {
        assertThat(kudosListPage.getKudosCount())
                .as("El número de kudos encontrados")
                .isGreaterThan(0);
    }

    @Entonces("la tabla debe tener al menos {int} fila")
    public void laTablaDebeTenerAlMenosNFilas(int minRows) {
        assertThat(kudosListPage.getTableRowCount())
                .as("El número de filas de la tabla")
                .isGreaterThanOrEqualTo(minRows);
    }

    @Entonces("el placeholder del campo de búsqueda debe ser {string}")
    public void elPlaceholderDelCampoDeBusquedaDebeSer(String expectedPlaceholder) {
        assertThat(kudosListPage.getSearchPlaceholder())
                .as("El placeholder del campo de búsqueda")
                .isEqualTo(expectedPlaceholder);
    }

    @Cuando("navego nuevamente a la página de listado")
    public void navegoNuevamenteALaPaginaDeListado() {
        kudosListPage.navigateTo();
    }
}
