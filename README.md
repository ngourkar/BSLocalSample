# BSLocalSample
This repo is intended to have a sample junit test to check the browserstack is able to establish connection for running test script for local web application.

Issue :
The RemoteWebDriver is unable to crete a seesion and giving an error when new instance of RemoteWebDriver is created.
Eventhough the correct URL with proper capabilities are passed.
This issue is happening for the web platform.

Error StackTrace:
org.openqa.selenium.SessionNotCreatedException: Could not start a new session. Possible causes are invalid address of the remote server or browser start-up failure. 
Caused by: org.openqa.selenium.WebDriverException: [browserstack.local] is set to true but local testing through BrowserStack is not connected.

Files Present:
A sample gradle project with junit test for checking driver is able to get the url for localhost:3000 on which our web application is running up.
Have added proper browserstack capabilities.
Have incorporated the start of browserstack local.
File Name : HelloWorldBSTest.java 
