package tim2.CulturalHeritage.e2e.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import tim2.CulturalHeritage.e2e.page.CHTablePage;
import tim2.CulturalHeritage.e2e.page.LoginPage;

public class CHTableE2E {

    private WebDriver driver;

    private LoginPage loginPage;

    private CHTablePage chTablePage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        chTablePage = PageFactory.initElements(driver, CHTablePage.class);
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

        loginPage.getEmail().sendKeys("sima12@hotmail.com");
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
    public void testTableOfAllCH() throws InterruptedException {

        logInAdmin();

        driver.get("http://localhost:4200/cultural-heritages");

        justWait(1000);

        chTablePage.ensureCHTableIsDisplayed();

    }

    @Test
    public void testDeleteCHSuccess() throws InterruptedException {

        logInAdmin();
        
        driver.get("http://localhost:4200/cultural-heritages");

        justWait(1000);

        chTablePage.ensureAreDisplayedDeleteButtons();

        chTablePage.getDeleteButtonSuccess().click();

        chTablePage.getDeleteButtonConfirm().click();

        justWait(500);

        String snackBarText = chTablePage.getSnackBar().getText();

        assertEquals("Successfuly deleted Sundance Film Festival.\nDismiss", snackBarText);

        chTablePage.ensureNotVisibleDeletedCH();
    }

    @Test
    public void testDeleteTypeFail() throws InterruptedException {

        logInAdmin();

        driver.get("http://localhost:4200/cultural-heritages");

        justWait(1000);

        chTablePage.ensureAreDisplayedDeleteButtons();

        chTablePage.getDeleteButtonFail().click();
        chTablePage.getDeleteButtonConfirm().click();

        justWait(500);

        String snackBarText = chTablePage.getSnackBar().getText();

        assertEquals("Can\'t delete Venice Carnival because there are subscribed users.\nDismiss", snackBarText);

        chTablePage.ensureVisibleNotDeletedCH();
    }

}
