package com.sofkianos.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sofkianos.config.TestConfig;

/**
 * Page Object para la Landing Page de SofkianOS.
 * Contiene la navegación principal hacia las secciones de la aplicación.
 */
public class LandingPage extends BasePage {

    /** Botón "Explorar Kudos" en el navbar. */
    @FindBy(xpath = "//button[contains(text(),'Explorar Kudos')]")
    private WebElement explorarKudosButton;

    /** Logo / título SofkianOS en el header. */
    @FindBy(xpath = "//button[contains(@class,'text-xl')]")
    private WebElement logoButton;

    /** Título principal h1 de la landing. */
    @FindBy(css = "h1")
    private WebElement mainTitle;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navega a la landing page de la aplicación.
     *
     * @return la misma instancia de LandingPage
     */
    public LandingPage navigateTo() {
        driver.get(TestConfig.BASE_URL);
        return this;
    }

    /**
     * Hace clic en "Explorar Kudos" para navegar al listado.
     *
     * @return instancia de KudosListPage
     */
    public KudosListPage goToExplorarKudos() {
        waitForClickable(explorarKudosButton).click();
        return new KudosListPage(driver);
    }

    /**
     * Verifica si el título principal es visible en la landing.
     *
     * @return true si el h1 es visible
     */
    public boolean isMainTitleDisplayed() {
        return waitForVisibility(mainTitle).isDisplayed();
    }

    /**
     * Verifica si el botón "Explorar Kudos" está visible en el navbar.
     *
     * @return true si el botón está visible
     */
    public boolean isExploreLinkVisible() {
        return waitForVisibility(explorarKudosButton).isDisplayed();
    }
}
