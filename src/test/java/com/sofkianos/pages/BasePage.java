package com.sofkianos.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sofkianos.config.TestConfig;

import java.time.Duration;

/**
 * Clase base para todas las Page Objects.
 * Provee funcionalidades compartidas como esperas explícitas y navegación.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.EXPLICIT_WAIT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    /**
     * Espera hasta que un elemento sea visible en la página.
     *
     * @param element el WebElement a esperar
     * @return el WebElement una vez visible
     */
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Espera hasta que un elemento sea clickeable.
     *
     * @param element el WebElement a esperar
     * @return el WebElement una vez clickeable
     */
    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Obtiene el título de la página actual.
     *
     * @return título del documento HTML
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Obtiene la URL actual del navegador.
     *
     * @return URL actual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
