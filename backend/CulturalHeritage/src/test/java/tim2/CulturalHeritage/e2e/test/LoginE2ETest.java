package tim2.CulturalHeritage.e2e.test;

import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginE2ETest {


    private WebDriver driver;

    private LoginPage loginPage;


    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
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


    @Test
    public void loginTestSuccess() throws InterruptedException {

        driver.get("http://localhost:4200/login");

        justWait(1000);

        loginPage.ensureIsDisplayedEmail();
        loginPage.ensureIsDisplayedPassword();

        loginPage.ensureIsNotVisibleSuccessDiv();

        loginPage.getEmail().sendKeys("sima12@hotmail.com");

        loginPage.getPassword().sendKeys("123");

        loginPage.getLoginBtn().click();

        justWait(1000);

        loginPage.ensureIsNotVisibleErrorDiv();
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    @Test
    public void loginGoToRegisterTest() throws InterruptedException {

        driver.get("http://localhost:4200/login");

        String loginLinkText = loginPage.getRegisterLink().getText();
        assertEquals("Don't have an account? Register here!", loginLinkText);
        loginPage.getRegisterLink().click();

        justWait(1000);

        assertEquals("http://localhost:4200/register", driver.getCurrentUrl());
    }

    @Test
    public void loginBadCredentialsTest() throws InterruptedException {

        driver.get("http://localhost:4200/login");

        justWait(1000);

        loginPage.ensureIsDisplayedEmail();
        loginPage.ensureIsDisplayedPassword();

        loginPage.ensureIsNotVisibleSuccessDiv();

        loginPage.getEmail().sendKeys("sima12@hotmail.com");

        loginPage.getPassword().sendKeys("not valid password");

        loginPage.getLoginBtn().click();

        justWait(1000);

        loginPage.ensureIsVisibleErrorDiv();
        loginPage.ensureBadCredentialsErrorText();
        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }

    @Test
    public void loginNotVerifiedUserTest() throws InterruptedException {

        driver.get("http://localhost:4200/login");

        justWait(1000);

        loginPage.ensureIsDisplayedEmail();
        loginPage.ensureIsDisplayedPassword();

        loginPage.ensureIsNotVisibleSuccessDiv();

        loginPage.getEmail().sendKeys("elliorhilario@hotmail.com");

        loginPage.getPassword().sendKeys("123");

        loginPage.getLoginBtn().click();

        justWait(1000);

        loginPage.ensureIsVisibleErrorDiv();
        loginPage.ensureNotVerifiedErrorText();
        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }

    @Test
    public void logOutTest() throws InterruptedException {
        driver.get("http://localhost:4200/login");

        loginPage.getEmail().sendKeys("helen@gmail.com");
        loginPage.getPassword().sendKeys("123");
        loginPage.getLoginBtn().click();
        justWait(4000);

        loginPage.getMenu().click();
        justWait(2000);
        loginPage.getSignOutButton().click();
        justWait(500);

        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }

}