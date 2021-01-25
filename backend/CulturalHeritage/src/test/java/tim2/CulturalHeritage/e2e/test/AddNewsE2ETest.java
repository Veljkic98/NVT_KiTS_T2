package tim2.CulturalHeritage.e2e.test;

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
    addNewsPage.ensureEditButtonIsPresent();
    justWait(1000);
    addNewsPage.getAddNewsButton().click();
    justWait(1000);
  }

  @Test
  public void addNewsOK() throws InterruptedException{
    logInAdmin();
    driver.get("http://localhost:4200/add/news/2");
    
  }

}
