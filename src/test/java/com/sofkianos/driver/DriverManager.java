package com.sofkianos.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.sofkianos.config.TestConfig;

import java.time.Duration;

/**
 * Administra la instancia del WebDriver utilizando ThreadLocal
 * para soportar ejecución paralela de tests.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverManager() {
        // Utilidad, no instanciar
    }

    /**
     * Inicializa el WebDriver de Chrome con opciones predeterminadas.
     *
     * @return instancia de WebDriver configurada
     */
    public static WebDriver getDriver() {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");

            String headless = System.getProperty("headless", "false");
            if ("true".equalsIgnoreCase(headless)) {
                options.addArguments("--headless=new");
            }

            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(TestConfig.IMPLICIT_WAIT_SECONDS)
            );
            DRIVER_THREAD_LOCAL.set(driver);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    /**
     * Cierra el navegador y limpia la referencia ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            DRIVER_THREAD_LOCAL.remove();
        }
    }
}
