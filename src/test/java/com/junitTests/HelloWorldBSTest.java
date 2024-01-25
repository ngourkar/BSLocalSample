package com.junitTests;

import com.browserstack.local.Local;
import org.junit.jupiter.api.*;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

class HelloWorldBSTest {

    private WebDriver driver;
    private Local bsLocal;
    String username = "ashishkhamari_7fRXzd";
    String key = "wyYUttrFz1baGTduGJ52";

    @BeforeEach
    public void beforeMethod(TestInfo testInfo) {
        System.out.println("BeforeMethod: Test: " + testInfo.getDisplayName());
        String access_key = key;
        String browserStackLocalIdentifier = String.valueOf(new Random().nextInt(100000));
        startBrowserStackLocal(access_key, browserStackLocalIdentifier);
        driver = createBSRemoteDriver();
    }

    private WebDriver createBSRemoteDriver() {
        WebDriver innerDriver;
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> bstackOptions = new HashMap<String, Object>();
        capabilities.setCapability("browserName", "Chrome");
        bstackOptions.put("os", "Windows");
        bstackOptions.put("osVersion", "11");
        bstackOptions.put("browserVersion", "latest");
        bstackOptions.put("consoleLogs", "info");
        bstackOptions.put("projectName", "bs local test");
        bstackOptions.put("buildName", "not-set");
        bstackOptions.put("sessionName", "test");
        bstackOptions.put("local", "true");
        bstackOptions.put("debug", "true");
        bstackOptions.put("networkLogs", "true");
        capabilities.setCapability("bstack:options", bstackOptions);
        ChromeOptions chromeOptions = new ChromeOptions().merge(capabilities);
        System.out.println(chromeOptions.toJson());

        try {
            innerDriver = new RemoteWebDriver(new URL("https://" + username + ":" + key + "@hub.browserstack.com/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error creating a new RemoteWebDriver for BS: ", e);
        }
        return innerDriver;
    }

    @Test
    void seleniumBaseTest() throws InterruptedException {
//        driver.get("https://google.com");
//        driver.get("http://localhost:9000/api/getUsers/102");
//        driver.get("http://bs-local.com:9000/api/getUsers/103");
        driver.get("http://localhost:3000/");
    }

    @AfterEach
    public void afterMethod(TestInfo testInfo) {
        System.out.println("BeforeMethod: Test: " + testInfo.getDisplayName());
        driver.quit();
        stopBrowserStackLocal();
    }

    private void startBrowserStackLocal(String authenticationKey, String id) {
        bsLocal = new Local();

        HashMap<String, String> bsLocalArgs = new HashMap<>();
        bsLocalArgs.put("key", authenticationKey);
        bsLocalArgs.put("v", "true");
        bsLocalArgs.put("localIdentifier", id);
        bsLocalArgs.put("forcelocal", "true");
        bsLocalArgs.put("verbose", "3");
        bsLocalArgs.put("browserstack.local", "true");

        try {
            System.out.println("Is BrowserStackLocal running? - " + bsLocal.isRunning());
            System.out.println(String.format("Start BrowserStackLocal using: %s", bsLocalArgs));
            bsLocal.start(bsLocalArgs);
            System.out.println(String.format("Is BrowserStackLocal started? - %s", bsLocal.isRunning()));
        } catch (Exception e) {
            throw new RuntimeException("Error starting BrowserStackLocal" + e.getLocalizedMessage());
        }
    }

    private void stopBrowserStackLocal() {
        try {
            System.out.println(String.format("Is BrowserStackLocal running? - %s", bsLocal.isRunning()));
            if (bsLocal.isRunning()) {
                System.out.println("Stopping BrowserStackLocal");
                bsLocal.stop();
                System.out.println(String.format("Is BrowserStackLocal stopped? - %s", !bsLocal.isRunning()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in stopping BrowserStackLocal", e);
        }
    }
}
