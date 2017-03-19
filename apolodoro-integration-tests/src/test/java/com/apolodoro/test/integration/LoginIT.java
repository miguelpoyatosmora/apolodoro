package com.apolodoro.test.integration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class LoginIT {

    @Test
    public void test1() throws MalformedURLException {

//        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://localhost:2376")
//                .withDockerTlsVerify(true)
//                .withDockerCertPath("/home/miguel/.docker/certs")
//                .withDockerConfig("/home/miguel/.docker")
//                .withApiVersion("1.23")
//                .withRegistryUrl("https://index.docker.io/v1/")
//                .withRegistryUsername("dockeruser")
//                .withRegistryPassword("ilovedocker")
//                .withRegistryEmail("dockeruser@github.com")
//                .build();
//        DockerClient docker = DockerClientBuilder.getInstance(config).build();

        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());

        // And now use this to visit home page
        driver.get("http://nginx/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Check the title of the page
        assertEquals("React App", driver.getTitle());

        // Find the text input element by its name
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("miguel");

        // Find the text input element by its name
        WebElement passwordField = driver.findElement(By.name("password"));

        // Enter something to search for
        passwordField.sendKeys("123456");

        // Now submit the form. WebDriver will find the form for us from the element
        passwordField.submit();


        // Login response is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        waitAndCheck(driver, 10, d ->
                d.getTitle().toLowerCase().startsWith("Apolodoro"));

        // Should see: "cheese! - Google Search"
//        System.out.println("Page title is: " + driver.getTitle());

        //Close the browser
        driver.quit();
    }

    private void waitAndCheck(WebDriver driver, int timeOutInSeconds, ExpectedCondition<Boolean> booleanExpectedCondition) {

        System.out.println("Waiting " + timeOutInSeconds + " for " + driver.getTitle());

        new WebDriverWait(driver, timeOutInSeconds).until(booleanExpectedCondition);
    }
}
