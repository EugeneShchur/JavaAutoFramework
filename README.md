# JavaAutoFramework

---
[Java - Maven - Selenium WebDriver - TestNG - Atlas Web Elements - RestAssured - Allure reports]
---

#### Summary:

This a basic example of automation testing framework on Java.
It includes both UI and API testing and provides examples of test cases implementation and classes architecture.

Tests are implemented for https://demoqa.com website Book Store Application section.
You can create your own users with you own password on this site and update constants in the **TestUsers** class.

Framework specially made expandable and improvable, with some 'open' preparations and developments.
It could be used for the purpose of learning and training, so one could perform further automation on demoqa.com website.
There are some useful helper classes, matchers and other examples of staff, which may come in handy to automate user scenarios, to check elements display and to make interactions, to group and run test cases and modify the tests reports.

Also, it includes a simple GitHub workflow example to run tests on schedule and manually, and to post Allure test report on the public [GitHub pages site](https://eugeneshchur.github.io/JavaAutoFramework/) of the current repo.
Please don't forget the YAGNI principle and before start using this example for you real automation projects,
make sure you removed all not needed staff and added suitable place where to publish test reports.

#### System Requirements:

* JDK 17 or above
* Maven 3.6.3 or above
* IntelliJ IDEA or any Java IDE

#### How to run the test cases
To run all the available tests you could use maven commands like 'mvn clean test' with needed parameters.
As soon as it is recommended not to store some 'sensitive' properties like passwords in the repo,
usually they are saved into GitHub secrets (Jenkins Vault secrets, etc.) and then provided via maven command parameters.

Here are some basic examples, with the ADMIN_PASSWORD secret from GitHub env vars, but if you run tests locally you could just enter the password value manually:

###### Example 1: run the tests from 'Smoke' test group:

    mvn clean test -Dgroups=Smoke -DisHeadless=true -DadminPassword=$ADMIN_PASSWORD

###### Example 2: run the tests only from 'ViewBooksTest' class:

    mvn -Dtest=ViewBooksTest -DadminPassword=$ADMIN_PASSWORD clean test  

for more information please check:
* Maven Surefire Plugin TestNG docs https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html
* Allure TestNG docs https://docs.qameta.io/allure/#_testng

#### Tests Results Files:
The test execution results will appear in the build folder (./target folder) once the tests have been completed.

Surefire Plugin generates reports in 2 different file formats (plain text files *.txt and XML files *.xml) in the ./target/surefire-reports folder.

The 'raw' Allure results in .json format will appear in ./target/allure-results folder.
To just generate Allure HTML Report from that raw files run the following command:

    mvn allure:report
Report will be generated tо directory: ./target/site/allure-maven/index.html
To generate Allure HTML report and automatically open it in your web browser run:

    mvn allure:serve

#### Available test groups to run tests:

* Smoke
* BookStoreUi
* ApiRegression
