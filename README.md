# JavaAutoFramework

---
[Java - Maven - Selenium WebDriver - TestNG - Atlas Web Elements - RestAssured - Allure reports]
---

#### Summary:

This a basic example of automation testing framework on Java.
It includes both UI and API testing and provides examples of test cases implementation and classes architecture.

Tests are implemented for https://demoqa.com website Book Store Application section.
You can create your own users with you own password on this site and update constants in the **TestUsers** class.

Framework specially made expandable and improvable, with some 'open' preparations and developments,
so could be used for the purpose of learning and performing further automation tasks based on it.
It already includes some useful helper classes, matchers and other examples of staff,
which may come in handy to automate user scenarios, to checking elements display and make interactions,
to group and run test cases and getting the tests reports.

Also, it includes a simple GitHub workflow example to run tests on schedule and manually,
and to post Allure test report on the public GitHub pages site of the current repo.
Please don't forget the YAGNI principle and before start using this example for you real automation projects,
make sure you removed all not needed staff and added suitable place where to publish test reports.

#### System Requirements:

* JDK 1.8 or above
* Maven 3.6.3 or above
* IntelliJ IDEA or any Java IDE

#### Available test groups to run tests

* Smoke
* BookStoreUi
* ApiRegression
