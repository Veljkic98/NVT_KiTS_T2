package tim2.CulturalHeritage.e2e.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import tim2.CulturalHeritage.e2e.page.AddNewsPage;
import tim2.CulturalHeritage.e2e.page.LoginPage;

public class AddNewsE2ETest {
  private WebDriver driver;

  private AddNewsPage addNewsPage;

  private LoginPage loginPage;

  @Before
  public void setUp() {

    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();

    driver.manage().window().maximize();
    addNewsPage = PageFactory.initElements(driver, AddNewsPage.class);
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

  public void logInAdmin() throws InterruptedException {
    driver.get("http://localhost:4200/login");

    loginPage.getEmail().sendKeys("admin@gmail.com");
    loginPage.getPassword().sendKeys("123");
    loginPage.getLoginBtn().click();
    justWait(1500);
  }

  @Test
  public void clickAddNewsButton() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/cultural-heritages");
    justWait(1000);
    addNewsPage.ensureAddNewsButtonIsPresent();
    justWait(1000);
    addNewsPage.getAddNewsButton().click();
    justWait(1000);
  }

  @Test
  public void addNewsOK() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/add/news/2");
    addNewsPage.getHeadingInput().clear();
    addNewsPage.getHeadingInput().sendKeys("Visit World's Most Popular Museum");

    addNewsPage.getContentInput().clear();
    addNewsPage.getContentInput().sendKeys("content bla.");

    addNewsPage.ensureAddButtonIsClickable();
    addNewsPage.getAddButton().click();

    addNewsPage.ensureSnackBarIsPresent();
    String snackBarText = addNewsPage.getSnackBar().getText();
    assertEquals("Successfuly added Visit World's Most Popular Museum news.\nDismiss", snackBarText); 
  }

  @Test
  public void addNewsWithImageOK() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/add/news/2");
    addNewsPage.getHeadingInput().clear();
    addNewsPage.getHeadingInput().sendKeys("Visit World's Most Popular Museum");

    addNewsPage.getContentInput().clear();
    addNewsPage.getContentInput().sendKeys("content bla.");

    File file = new File("./src/test/resources/tree.jpg");
    String absolutePath = file.getAbsolutePath();
    addNewsPage.getFileInput().sendKeys(absolutePath);
    
    addNewsPage.ensureAddButtonIsClickable();
    addNewsPage.getAddButton().click();

    addNewsPage.ensureSnackBarIsPresent();
    String snackBarText = addNewsPage.getSnackBar().getText();
    assertEquals("Successfuly added Visit World's Most Popular Museum news.\nDismiss", snackBarText); 
  }

  @Test
  public void addNewsNoContent() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/add/news/2");
    addNewsPage.getHeadingInput().clear();
    addNewsPage.getHeadingInput().sendKeys("Visit World's Most Popular Museum");
    addNewsPage.ensureAddButtonIsNotClickable();
  }

  @Test
  public void addNewsNoHeading() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/add/news/2");
    addNewsPage.getContentInput().clear();
    addNewsPage.getContentInput().sendKeys("content bla.");
    addNewsPage.ensureAddButtonIsNotClickable();
  }
}
