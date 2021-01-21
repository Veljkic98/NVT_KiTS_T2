package tim2.CulturalHeritage.e2e.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import tim2.CulturalHeritage.e2e.page.CHTypePage;
import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.MyProfilePage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyProfileE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private MyProfilePage myProfilePage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        myProfilePage = PageFactory.initElements(driver, MyProfilePage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void justWait(Integer howLong) throws InterruptedException {
        synchronized (driver) {
            driver.wait(howLong);
        }
    }

    public void logInUser() throws InterruptedException {
        driver.get("http://localhost:4200/login");

        loginPage.getEmail().sendKeys("helen@gmail.com");
        loginPage.getPassword().sendKeys("123");
        loginPage.getLoginBtn().click();
        justWait(1500);
    }

    public void logInAdmin() throws InterruptedException {
        driver.get("http://localhost:4200/login");

        loginPage.getEmail().sendKeys("admin@gmail.com");
        loginPage.getPassword().sendKeys("123");
        loginPage.getLoginBtn().click();
        justWait(1500);
    }

    @Test
    public void redirectNotLoggedIn() throws InterruptedException {

        driver.get("http://localhost:4200/me/0");

        justWait(1000);

        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }

}
