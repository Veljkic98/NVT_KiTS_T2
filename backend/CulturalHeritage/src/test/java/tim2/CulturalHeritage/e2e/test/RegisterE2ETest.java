package tim2.CulturalHeritage.e2e.test;

import tim2.CulturalHeritage.e2e.page.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterE2ETest {


    private WebDriver driver;

    private RegisterPage registerPage;


    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        registerPage = PageFactory.initElements(driver, RegisterPage.class);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void justWait(Integer howLong) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(howLong);
        }
    }


    @Test
    public void registerTestSuccess() throws InterruptedException {

        driver.get("http://localhost:4200/register");

        justWait(1000);

        registerPage.ensureIsDisplayedEmail();
        registerPage.ensureIsDisplayedFirstName();
        registerPage.ensureIsDisplayedLastName();
        registerPage.ensureIsDisplayedPassword();
        registerPage.ensureIsDisplayedPasswordConfirm();

        registerPage.ensureIsNotVisibleSuccessDiv();

        registerPage.getFirstName().sendKeys("Smith");

        registerPage.getLastName().sendKeys("Jerrod");

        registerPage.getEmail().sendKeys("some@example.com");

        registerPage.getPassword().sendKeys("123qweASD");
        registerPage.getPasswordConfirm().sendKeys("123qweASD");

        registerPage.getSignUpBtn().click();

        justWait(1000);

        registerPage.ensureIsVisibleSuccessDiv();
        registerPage.ensureIsNotVisibleErrorDiv();
    }

    @Test
    public void registerGoToLoginTest() throws InterruptedException {

        driver.get("http://localhost:4200/register");

        String loginLinkText = registerPage.getLoginLink().getText();
        assertEquals("Already have account? Sign in!", loginLinkText);
        registerPage.getLoginLink().click();

        justWait(1000);

        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }

    @Test
    public void registerTestExistEmailError() throws InterruptedException {

        driver.get("http://localhost:4200/register");

        justWait(1000);

        registerPage.ensureIsDisplayedEmail();

        //existing email
        registerPage.getEmail().sendKeys("sima12@hotmail.com");

        registerPage.getFirstName().sendKeys("Smith");

        registerPage.getLastName().sendKeys("Jerrod");

        registerPage.getPassword().sendKeys("123qweASD");
        registerPage.getPasswordConfirm().sendKeys("123qweASD");

        registerPage.getSignUpBtn().click();

        justWait(1000);

        registerPage.ensureIsNotVisibleSuccessDiv();
        registerPage.ensureIsVisibleErrorDiv();
    }

    @Test
    public void registerTestEmailError() throws InterruptedException {
        driver.get("http://localhost:4200/register");

        justWait(1000);

        registerPage.ensureIsDisplayedEmail();

        //existing email
        registerPage.getEmail().sendKeys("NOT VALID EMAIL");

        registerPage.getFirstName().sendKeys("Smith");

        registerPage.getLastName().sendKeys("Jerrod");

        registerPage.getPassword().sendKeys("123qweASD");
        registerPage.getPasswordConfirm().sendKeys("123qweASD");

        registerPage.getSignUpBtn().click();

        justWait(1000);
        String errorValue = registerPage.getFormError().getText();

        assertEquals("Email address must be valid email", errorValue);
        registerPage.ensureIsNotVisibleSuccessDiv();
    }


    @Test
    public void registerTestPasswordMatchError() throws InterruptedException {
        driver.get("http://localhost:4200/register");

        justWait(1000);

        registerPage.ensureIsDisplayedPassword();
        registerPage.ensureIsDisplayedPasswordConfirm();

        registerPage.getFirstName().sendKeys("Smith");

        registerPage.getLastName().sendKeys("Jerrod");

        registerPage.getEmail().sendKeys("some@example.com");

        registerPage.getPassword().sendKeys("12345678");
        registerPage.getPasswordConfirm().sendKeys("something different!");

        registerPage.getSignUpBtn().click();

        justWait(1000);
        String errorValue = registerPage.getFormError().getText();

        assertEquals("Password & Confirm Password do not match.", errorValue);
        registerPage.ensureIsNotVisibleSuccessDiv();
    }

    @Test
    public void registerTestPasswordShortError() throws InterruptedException {
        driver.get("http://localhost:4200/register");

        justWait(1000);

        registerPage.ensureIsDisplayedPassword();

        registerPage.getFirstName().sendKeys("Smith");

        registerPage.getLastName().sendKeys("Jerrod");

        registerPage.getEmail().sendKeys("some@example.com");

        registerPage.getPassword().sendKeys("12");
        registerPage.getPasswordConfirm().sendKeys("12");

        registerPage.getSignUpBtn().click();

        justWait(1000);
        String errorValue = registerPage.getFormError().getText();

        assertEquals("Password must be at least 8 characters", errorValue);
        registerPage.ensureIsNotVisibleSuccessDiv();
    }
}