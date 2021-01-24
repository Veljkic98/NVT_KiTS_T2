package tim2.CulturalHeritage.e2e.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import tim2.CulturalHeritage.e2e.page.CHUpdatePage;
import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.MapPage;

public class CHUpdateE2ETest {
  private WebDriver driver;

  private CHUpdatePage chUpdatePage;

  private LoginPage loginPage;

  @Before
  public void setUp() {

    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();

    driver.manage().window().maximize();
    chUpdatePage = PageFactory.initElements(driver, CHUpdatePage.class);
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
  public void clickEditButton() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/cultural-heritages");
    justWait(1000);
    chUpdatePage.ensureEditButtonIsPresent();
    justWait(1000);
    chUpdatePage.getEditButton().click();
    justWait(1000);
  }

  @Test
  public void updateLocation() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/update-ch/1");
    chUpdatePage.ensureMapIsPresent();
    chUpdatePage.ensureGeocoderIsPresent();
    //Grazbachgasse, 8010 Graz, Austria
    chUpdatePage.getGeocoder().sendKeys("Grazbachgasse, 8010 Graz, Austria");
    justWait(2000);
    chUpdatePage.getGeocoder().sendKeys(Keys.RETURN);
    justWait(1000);
    chUpdatePage.ensureUpdateButtonIsClickable();
    chUpdatePage.getUpdateButton().click();
  }


}
