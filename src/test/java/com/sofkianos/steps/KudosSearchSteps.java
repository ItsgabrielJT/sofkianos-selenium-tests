package com.sofkianos.steps;

import com.sofkianos.driver.DriverManager;
import com.sofkianos.pages.KudosListPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions para los escenarios de búsqueda de Kudos.
 * Cubre escritura en el campo de búsqueda, limpieza y resultados.
 */
public class KudosSearchSteps {

    private KudosListPage kudosListPage;

    private KudosListPage getPage() {
        if (kudosListPage == null) {
            kudosListPage = new KudosListPage(DriverManager.getDriver());
        }
        return kudosListPage;
    }

    @Cuando("escribo {string} en el campo de búsqueda")
    public void escriboEnElCampoDeBusqueda(String text) {
        getPage().searchFor(text);
    }

    @Cuando("aplico los filtros")
    public void aplicoLosFiltros() {
        getPage().applyFilters();
    }

    @Entonces("el contador debe reflejar los resultados de la búsqueda")
    public void elContadorDebeReflejarLosResultadosDeLaBusqueda() {
        String countText = getPage().getKudosCountText();
        assertThat(countText)
                .as("El contador debe mostrar resultados de la búsqueda")
                .matches("\\d+ kudos encontrados");
    }

    @Entonces("el contador debe indicar {int} kudos encontrados")
    public void elContadorDebeIndicarNKudosEncontrados(int expectedCount) {
        assertThat(getPage().getKudosCount())
                .as("El número de kudos encontrados")
                .isEqualTo(expectedCount);
    }

    @Cuando("limpio el campo de búsqueda")
    public void limpioElCampoDeBusqueda() {
        getPage().clearSearch();
    }
}
