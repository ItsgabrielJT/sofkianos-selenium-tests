package com.sofkianos.hooks;

import com.sofkianos.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Hooks de Cucumber que se ejecutan antes y después de cada escenario.
 * Gestionan el ciclo de vida del WebDriver y capturan screenshots en caso de fallo.
 */
public class CucumberHooks {

    /**
     * Inicializa el WebDriver antes de cada escenario.
     */
    @Before
    public void setUp() {
        DriverManager.getDriver();
    }

    /**
     * Captura screenshot en caso de falla y cierra el WebDriver.
     *
     * @param scenario el escenario Cucumber que acaba de ejecutarse
     */
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        DriverManager.quitDriver();
    }
}
