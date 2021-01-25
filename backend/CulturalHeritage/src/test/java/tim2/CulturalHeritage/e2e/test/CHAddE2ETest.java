package tim2.CulturalHeritage.e2e.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import tim2.CulturalHeritage.e2e.page.CHAddPage;
import tim2.CulturalHeritage.e2e.page.LoginPage;

public class CHAddE2ETest {
  private WebDriver driver;

  private CHAddPage chAddPage;

  private LoginPage loginPage;

  @Before
  public void setUp() {

    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();

    driver.manage().window().maximize();
    chAddPage = PageFactory.initElements(driver, CHAddPage.class);
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
  public void clickAddButton() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/cultural-heritages");
    justWait(1000);
    chAddPage.ensureAddButtonIsPresent();
    justWait(1000);
    chAddPage.getAddButton().click();
    justWait(1000);
  }

  @Test
  public void addNameAndDescriptionShouldNotBeClickable() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/new-ch");
    justWait(1000);
    chAddPage.getNameInput().clear();
    chAddPage.getNameInput().sendKeys("New CH name");
    chAddPage.getDescriptionInput().clear();
    chAddPage.getDescriptionInput().sendKeys("New CH description");
    chAddPage.ensurePostButtonIsNotClickable();
    // assertEquals("http://localhost:4200/cultural-heritages",
    // driver.getCurrentUrl());
  }

  @Test
  public void postButtonShoudBeClickable() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/new-ch");
    justWait(1000);

    // name
    chAddPage.getNameInput().clear();
    chAddPage.getNameInput().sendKeys("New CH name");

    // description
    chAddPage.getDescriptionInput().clear();
    chAddPage.getDescriptionInput().sendKeys("New CH description");

    // subtype
    chAddPage.getSubtypeSelect().click();
    chAddPage.getSubtypeOption().click();

    // picture
    File file = new File("./src/test/resources/tree.jpg");
    String absolutePath = file.getAbsolutePath();
    chAddPage.getFileInput().sendKeys(absolutePath);

    // map
    chAddPage.ensureMapIsPresent();
    chAddPage.ensureGeocoderIsPresent();
    chAddPage.getGeocoder().sendKeys("Grazbachgasse, 8010 Graz, Austria");
    justWait(2000);
    chAddPage.getGeocoder().sendKeys(Keys.RETURN);
    justWait(1000);

    chAddPage.ensurePostButtonIsClickable();
    chAddPage.getPostButton().click();

    justWait(1000);
    assertEquals("http://localhost:4200/cultural-heritages", driver.getCurrentUrl());
  }

}
