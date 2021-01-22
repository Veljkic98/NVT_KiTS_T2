package tim2.CulturalHeritage.e2e.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import tim2.CulturalHeritage.e2e.page.CHTypePage;
import tim2.CulturalHeritage.e2e.page.DashboardE2EPage;
import tim2.CulturalHeritage.e2e.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DashboardE2E {

    private WebDriver driver;

    private DashboardE2EPage dashboardPage;

    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        dashboardPage = PageFactory.initElements(driver, DashboardE2EPage.class);
    }

    @After
    public void destroy(){
        driver.quit();
    }

    private void justWait(Integer howLong) throws InterruptedException {
        synchronized (driver) {
            driver.wait(howLong);
        }
    }

    @Test
    public void filterByName() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeName().click();

        dashboardPage.getFilterValueInput().sendKeys("venice carnival");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertEquals("Found 1 result.\nDismiss", dashboardPage.getSnackbar().getText());

    }


    @Test
    public void filterByNameNoResults() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeName().click();

        dashboardPage.getFilterValueInput().sendKeys("xgasdsadsafkfjxcvjsdjfdsfkdskfkdskfds");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertEquals("Found 0 results.\nDismiss", dashboardPage.getSnackbar().getText());

    }


    @Test
    public void filterBySubtype() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeSubtype().click();

        dashboardPage.getFilterValueInput().sendKeys("fair");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertTrue(dashboardPage.getSnackbar().getText().contains("results"));
    }

    @Test
    public void filterBySubtypeNoResults() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeSubtype().click();

        dashboardPage.getFilterValueInput().sendKeys("hsddgdsfdssd");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertEquals("Found 0 results.\nDismiss", dashboardPage.getSnackbar().getText());
    }



    @Test
    public void filterByCity() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeCity().click();

        dashboardPage.getFilterValueInput().sendKeys("belgrade");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertTrue(dashboardPage.getSnackbar().getText().contains("result"));
    }

    @Test
    public void filterByCityNoResults() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeCity().click();

        dashboardPage.getFilterValueInput().sendKeys("hsddgdsfdssd");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertEquals("Found 0 results.\nDismiss", dashboardPage.getSnackbar().getText());
    }



    @Test
    public void filterByCountry() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeCountry().click();

        dashboardPage.getFilterValueInput().sendKeys("serbia");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertTrue(dashboardPage.getSnackbar().getText().contains("result"));
    }

    @Test
    public void filterByCountryNoResults() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeCountry().click();

        dashboardPage.getFilterValueInput().sendKeys("hsddgdsfdssd");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertEquals("Found 0 results.\nDismiss", dashboardPage.getSnackbar().getText());
    }




    @Test
    public void resetButton() throws InterruptedException {
        driver.get("http://localhost:4200/");
        justWait(1500);

        dashboardPage.ensureIsPresentInputField();
        dashboardPage.ensureIsClickableGoButton();

        dashboardPage.getFilterTypeSelect().click();
        justWait(500);
        dashboardPage.getFilterTypeName().click();

        dashboardPage.getFilterValueInput().sendKeys("venice");
        dashboardPage.getGoButton().click();
        justWait(2000);

        assertEquals("Found 1 result.\nDismiss", dashboardPage.getSnackbar().getText());

        dashboardPage.getResetButton().click();
        assertEquals("", dashboardPage.getFilterValueInput().getText());

    }


}
