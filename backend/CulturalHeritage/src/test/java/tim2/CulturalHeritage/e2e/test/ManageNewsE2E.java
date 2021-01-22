package tim2.CulturalHeritage.e2e.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.ManageNewsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManageNewsE2E {
    private WebDriver driver;
    private ManageNewsPage manageNewsPage;
    private LoginPage loginPage;


    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        manageNewsPage = PageFactory.initElements(driver, ManageNewsPage.class);
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

        driver.get("http://localhost:4200/manage/news/1");

        justWait(1000);

        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }


    @Test
    public void redirectNotAdmin() throws InterruptedException {
        logInUser();
        justWait(1000);

        driver.get("http://localhost:4200/manage/news/1");

        justWait(1000);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    @Test
    public void deleteNews() throws InterruptedException {
        logInAdmin();
        justWait(1000);

        driver.get("http://localhost:4200/manage/news/1");

        justWait(1000);

        manageNewsPage.getDeleteButton().click();
        justWait(500);
        manageNewsPage.getConfirmDeleteButton().click();
        justWait(500);

        assertEquals("Successfuly deleted the news!\nDismiss", manageNewsPage.getSnackBar().getText());

    }


    @Test
    public void emptyNewsList() throws InterruptedException {
        logInAdmin();
        justWait(1000);

        driver.get("http://localhost:4200/manage/news/5");
        justWait(1000);

        assertEquals("There aren't any news for this cultural heritage :(",manageNewsPage.getHeadingEmpty().getText());
    }

}
