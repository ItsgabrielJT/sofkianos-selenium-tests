package com.sofkianos.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.sofkianos.config.TestConfig;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object para la página de listado de Kudos (/kudos/list).
 * Encapsula las interacciones con la búsqueda, filtros de categoría,
 * filtros de fecha, ordenamiento y la tabla de resultados.
 */
public class KudosListPage extends BasePage {

    // ─── Título y encabezado ────────────────────────────────────────────

    /** Título "Explorar Kudos" de la página. */
    @FindBy(css = "h1")
    private WebElement pageTitle;

    /** Subtítulo descriptivo bajo el h1. */
    @FindBy(xpath = "//p[contains(@class,'text-lg')]")
    private WebElement pageSubtitle;

    // ─── Filtros ────────────────────────────────────────────────────────

    /** Campo de búsqueda por texto (de, para, mensaje). */
    @FindBy(xpath = "//input[@aria-label='Buscar kudos']")
    private WebElement searchInput;

    /** Dropdown de categorías. */
    @FindBy(xpath = "//select[@aria-label='Filtrar por categoría']")
    private WebElement categorySelect;

    /** Input de fecha "desde". */
    @FindBy(xpath = "//input[@aria-label='Fecha desde']")
    private WebElement dateFromInput;

    /** Input de fecha "hasta". */
    @FindBy(xpath = "//input[@aria-label='Fecha hasta']")
    private WebElement dateToInput;

    /** Botón para aplicar los filtros. */
    @FindBy(xpath = "//button[text()='Aplicar Filtros']")
    private WebElement applyFiltersButton;

    // ─── Ordenamiento ───────────────────────────────────────────────────

    /** Botón para cambiar el orden (Más recientes / Más antiguos). */
    @FindBy(xpath = "//button[contains(@aria-label,'Ordenar por fecha')]")
    private WebElement sortButton;

    // ─── Resultados ─────────────────────────────────────────────────────

    // Reference removed to avoid Timeouts, text lookup will be dynamic

    /** Tabla de resultados. */
    @FindBy(css = "table")
    private WebElement resultsTable;

    /** Encabezados de la tabla (th). */
    @FindBy(css = "thead th")
    private List<WebElement> tableHeaders;

    /** Filas del cuerpo de la tabla. */
    @FindBy(css = "tbody tr")
    private List<WebElement> tableRows;

    /** Formulario de filtros. */
    @FindBy(css = "form[role='search']")
    private WebElement filterForm;

    // ─── Mensaje vacío ──────────────────────────────────────────────────

    /** Mensaje que aparece cuando no hay resultados. */
    private static final By EMPTY_MESSAGE = By.xpath(
            "//p[contains(text(),'No se encontraron kudos')]");

    public KudosListPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega directamente a la página de listado de Kudos.
     *
     * @return la misma instancia
     */
    public KudosListPage navigateTo() {
        driver.get(TestConfig.KUDOS_LIST_URL);
        waitForVisibility(pageTitle);
        return this;
    }

    // ─────────────────── Búsqueda ─────────────────────────────────────

    /**
     * Escribe un término en el campo de búsqueda.
     *
     * @param text texto a buscar
     * @return la misma instancia
     */
    public KudosListPage searchFor(String text) {
        waitForVisibility(searchInput).clear();
        searchInput.sendKeys(text);
        return this;
    }

    /**
     * Obtiene el texto actual del campo de búsqueda.
     *
     * @return valor del input de búsqueda
     */
    public String getSearchValue() {
        return searchInput.getAttribute("value");
    }

    /**
     * Limpia el campo de búsqueda.
     *
     * @return la misma instancia
     */
    public KudosListPage clearSearch() {
        searchInput.clear();
        return this;
    }

    // ─────────────────── Categoría ────────────────────────────────────

    /**
     * Selecciona una categoría del dropdown por texto visible.
     *
     * @param category texto de la opción (ej: "Teamwork", "Innovation")
     * @return la misma instancia
     */
    public KudosListPage selectCategory(String category) {
        Select select = new Select(waitForVisibility(categorySelect));
        select.selectByVisibleText(category);
        return this;
    }

    /**
     * Obtiene la categoría actualmente seleccionada.
     *
     * @return texto de la opción seleccionada
     */
    public String getSelectedCategory() {
        Select select = new Select(categorySelect);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Obtiene todas las opciones disponibles en el dropdown de categorías.
     *
     * @return lista de textos de las opciones
     */
    public List<String> getCategoryOptions() {
        Select select = new Select(categorySelect);
        return select.getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // ─────────────────── Fechas ───────────────────────────────────────

    /**
     * Establece la fecha "desde" en el filtro.
     *
     * @param date fecha en formato yyyy-MM-dd
     * @return la misma instancia
     */
    public KudosListPage setDateFrom(String date) {
        waitForVisibility(dateFromInput).clear();
        dateFromInput.sendKeys(date);
        return this;
    }

    /**
     * Establece la fecha "hasta" en el filtro.
     *
     * @param date fecha en formato yyyy-MM-dd
     * @return la misma instancia
     */
    public KudosListPage setDateTo(String date) {
        waitForVisibility(dateToInput).clear();
        dateToInput.sendKeys(date);
        return this;
    }

    /**
     * Obtiene el valor actual del campo de fecha "desde".
     *
     * @return valor del input en formato yyyy-MM-dd
     */
    public String getDateFromValue() {
        return dateFromInput.getAttribute("value");
    }

    /**
     * Obtiene el valor actual del campo de fecha "hasta".
     *
     * @return valor del input en formato yyyy-MM-dd
     */
    public String getDateToValue() {
        return dateToInput.getAttribute("value");
    }

    // ─────────────────── Aplicar filtros ──────────────────────────────

    /**
     * Hace clic en el botón "Aplicar Filtros".
     *
     * @return la misma instancia
     */
    public KudosListPage applyFilters() {
        waitForClickable(applyFiltersButton).click();
        // Esperar a que la tabla se actualice
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    /**
     * Verifica si el botón "Aplicar Filtros" está visible.
     *
     * @return true si está visible
     */
    public boolean isApplyFiltersButtonVisible() {
        return applyFiltersButton.isDisplayed();
    }

    // ─────────────────── Ordenamiento ─────────────────────────────────

    /**
     * Hace clic en el botón de ordenamiento para alternar el orden.
     *
     * @return la misma instancia
     */
    public KudosListPage toggleSort() {
        waitForClickable(sortButton).click();
        return this;
    }

    /**
     * Obtiene el texto actual del botón de ordenamiento.
     *
     * @return "Más recientes" o "Más antiguos"
     */
    public String getSortButtonText() {
        return sortButton.getText().trim();
    }

    // ─────────────────── Tabla de resultados ──────────────────────────

    /**
     * Obtiene el número de kudos indicado en el contador de resultados.
     * Busca la cadena en el texto del body para no depender del DOM inestable.
     *
     * @return cantidad de kudos encontrados
     */
    public int getKudosCount() {
        String bodyText = com.sofkianos.driver.DriverManager.getDriver()
                .findElement(org.openqa.selenium.By.tagName("body")).getText();
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)\\s+kudos encontrados").matcher(bodyText);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        if (bodyText.contains("No se encontraron kudos") || bodyText.contains("0 kudos encontrados")) {
            return 0;
        }
        return getTableRowCount();
    }

    /**
     * Obtiene el texto completo del contador de kudos.
     *
     * @return texto ej: "3 kudos encontrados"
     */
    public String getKudosCountText() {
        return getKudosCount() + " kudos encontrados";
    }

    /**
     * Verifica si la tabla de resultados está visible.
     *
     * @return true si la tabla es visible
     */
    public boolean isTableDisplayed() {
        try {
            return resultsTable.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el número de filas visibles en la tabla.
     *
     * @return cantidad de filas en el tbody
     */
    public int getTableRowCount() {
        return tableRows.size();
    }

    /**
     * Obtiene los textos de los encabezados de la tabla.
     *
     * @return lista con los textos de las columnas (De, Para, Categoría, Mensaje,
     *         Fecha)
     */
    public List<String> getTableHeaderTexts() {
        return tableHeaders.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el texto de una celda específica de la tabla.
     *
     * @param rowIndex índice de la fila (0-based)
     * @param colIndex índice de la columna (0-based)
     * @return texto de la celda
     */
    public String getCellText(int rowIndex, int colIndex) {
        WebElement row = tableRows.get(rowIndex);
        List<WebElement> cells = row.findElements(By.cssSelector("td"));
        return cells.get(colIndex).getText();
    }

    /**
     * Obtiene todos los valores de la columna "De" (índice 0).
     *
     * @return lista de remitentes
     */
    public List<String> getAllSenders() {
        return tableRows.stream()
                .map(row -> row.findElements(By.cssSelector("td")).get(0).getText())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los valores de la columna "Para" (índice 1).
     *
     * @return lista de destinatarios
     */
    public List<String> getAllReceivers() {
        return tableRows.stream()
                .map(row -> row.findElements(By.cssSelector("td")).get(1).getText())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los valores de la columna "Categoría" (índice 2).
     *
     * @return lista de categorías
     */
    public List<String> getAllCategories() {
        return tableRows.stream()
                .map(row -> row.findElements(By.cssSelector("td")).get(2).getText())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los valores de la columna "Fecha" (índice 4).
     *
     * @return lista de fechas
     */
    public List<String> getAllDates() {
        return tableRows.stream()
                .map(row -> row.findElements(By.cssSelector("td")).get(4).getText())
                .collect(Collectors.toList());
    }

    // ─────────────────── Verificaciones de visibilidad ────────────────

    /**
     * Verifica si el título de la página es visible.
     *
     * @return true si h1 es visible
     */
    public boolean isTitleDisplayed() {
        return waitForVisibility(pageTitle).isDisplayed();
    }

    /**
     * Obtiene el texto del título de la página.
     *
     * @return texto del h1
     */
    public String getTitleText() {
        return pageTitle.getText();
    }

    /**
     * Verifica si el formulario de filtros es visible.
     *
     * @return true si el form de búsqueda es visible
     */
    public boolean isFilterFormDisplayed() {
        return waitForVisibility(filterForm).isDisplayed();
    }

    /**
     * Verifica si el campo de búsqueda es visible.
     *
     * @return true si el input de búsqueda está visible
     */
    public boolean isSearchInputDisplayed() {
        return searchInput.isDisplayed();
    }

    /**
     * Verifica si el mensaje de "no hay resultados" es visible.
     *
     * @return true si no se encontraron kudos
     */
    public boolean isEmptyMessageDisplayed() {
        try {
            return driver.findElement(EMPTY_MESSAGE).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene el placeholder del campo de búsqueda.
     *
     * @return texto del placeholder
     */
    public String getSearchPlaceholder() {
        return searchInput.getAttribute("placeholder");
    }
}
