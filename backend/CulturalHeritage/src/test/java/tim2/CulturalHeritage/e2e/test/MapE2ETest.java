package tim2.CulturalHeritage.e2e.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.MapPage;

public class MapE2ETest {
  private WebDriver driver;

  private MapPage mapPage;

  private LoginPage loginPage;

  @Before
  public void setUp() {

    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();

    driver.manage().window().maximize();
    mapPage = PageFactory.initElements(driver, MapPage.class);
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
  public void ensureMarkerIsPresent() {
    driver.get("http://localhost:4200/");
    mapPage.ensureIsPresentMarker();
    mapPage.getChMarker().click();
  }

  @Test
  public void mapIsPresentNotLoggedIn() {
    driver.get("http://localhost:4200/");
    mapPage.ensureMapIsPresent();
    mapPage.getMap().click();
  }

  @Test
  public void mapIsPresentAdminLoggedIn() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/");
    mapPage.ensureMapIsPresent();
    mapPage.getMap().click();
  }

  @Test
  public void mapIsPresentUserLoggedIn() throws InterruptedException{
    logInUser();
    driver.get("http://localhost:4200/");
    mapPage.ensureMapIsPresent();
    mapPage.getMap().click();
  }
}
