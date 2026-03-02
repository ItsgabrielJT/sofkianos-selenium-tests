package com.sofkianos.steps;

import com.sofkianos.driver.DriverManager;
import com.sofkianos.pages.KudosListPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions para los escenarios de filtrado por fecha y ordenamiento de Kudos.
 * Cubre establecimiento de fechas y toggle de ordenamiento.
 */
public class KudosDateSteps {

    private KudosListPage kudosListPage;

    private KudosListPage getPage() {
        if (kudosListPage == null) {
            kudosListPage = new KudosListPage(DriverManager.getDriver());
        }
        return kudosListPage;
    }

    @Cuando("establezco la fecha desde {string}")
    public void establezcoLaFechaDesde(String date) {
        getPage().setDateFrom(date);
    }

    @Cuando("establezco la fecha hasta {string}")
    public void establezcoLaFechaHasta(String date) {
        getPage().setDateTo(date);
    }

    @Entonces("el botón de ordenamiento debe mostrar {string}")
    public void elBotonDeOrdenamientoDebeMostrar(String expectedText) {
        assertThat(getPage().getSortButtonText())
                .as("El texto del botón de ordenamiento")
                .isEqualTo(expectedText);
    }

    @Cuando("hago clic en el botón de ordenamiento")
    public void hagoClicEnElBotonDeOrdenamiento() {
        getPage().toggleSort();
    }
}
