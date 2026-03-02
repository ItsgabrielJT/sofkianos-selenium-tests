package com.sofkianos.steps;

import com.sofkianos.driver.DriverManager;
import com.sofkianos.pages.KudosListPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions para los escenarios de filtrado por categoría de Kudos.
 * Cubre selección del dropdown, verificación de opciones y resultados.
 */
public class KudosCategorySteps {

    private KudosListPage kudosListPage;

    private KudosListPage getPage() {
        if (kudosListPage == null) {
            kudosListPage = new KudosListPage(DriverManager.getDriver());
        }
        return kudosListPage;
    }

    @Entonces("el dropdown de categoría debe contener las opciones:")
    public void elDropdownDeCategoriadebeContenerLasOpciones(DataTable dataTable) {
        List<String> expectedOptions = dataTable.asList(String.class);
        List<String> actualOptions = getPage().getCategoryOptions();
        assertThat(actualOptions)
                .as("Las opciones del dropdown de categoría")
                .containsExactlyElementsOf(expectedOptions);
    }

    @Entonces("la categoría seleccionada debe ser {string}")
    public void laCategoriaSeleccionadaDebeSer(String expectedCategory) {
        assertThat(getPage().getSelectedCategory())
                .as("La categoría actualmente seleccionada")
                .isEqualTo(expectedCategory);
    }

    @Cuando("selecciono la categoría {string}")
    public void seleccionoLaCategoria(String category) {
        getPage().selectCategory(category);
    }
}
