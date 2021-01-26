package tim2.CulturalHeritage.e2e.test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.UpdateNewsPage;

public class UpdateNewsE2ETest {
  private WebDriver driver;

  private UpdateNewsPage updateNewsPage;

  private LoginPage loginPage;

  private JavascriptExecutor js;

  @Before
  public void setUp() {

    System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    driver = new ChromeDriver();

    driver.manage().window().maximize();
    updateNewsPage = PageFactory.initElements(driver, UpdateNewsPage.class);
    loginPage = PageFactory.initElements(driver, LoginPage.class);
    js = (JavascriptExecutor) driver;
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
  public void clickEditButton() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/manage/news/1");
    updateNewsPage.ensureEditButtonIsPresent();
    js.executeScript("arguments[0].scrollIntoView(true);", updateNewsPage.getEditButton());
    justWait(1500);
    updateNewsPage.getEditButton().click();
    justWait(1000);
    assertEquals("http://localhost:4200/update/news/4", driver.getCurrentUrl());
  }

  @Test
  public void updateNewsOk() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/update/news/4");
    updateNewsPage.getHeadingInput().clear();
    updateNewsPage.getHeadingInput().sendKeys("Visit World's Most Popular Museum");

    updateNewsPage.ensureUpdateButtonIsClickable();
    updateNewsPage.getUpdateButton().click();

    updateNewsPage.ensureSnackBarIsPresent();
    String snackBarText = updateNewsPage.getSnackBar().getText();
    assertEquals("Successfuly updated Visit World's Most Popular Museum.\nDismiss", snackBarText); 
  }

  @Test
  public void updateNewsWithImageOk() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/update/news/4");
    updateNewsPage.getHeadingInput().clear();
    updateNewsPage.getHeadingInput().sendKeys("Visit World's Most Popular Museum");

    updateNewsPage.getContentInput().clear();
    updateNewsPage.getContentInput().sendKeys("content bla.");

    File file = new File("./src/test/resources/tree.jpg");
    String absolutePath = file.getAbsolutePath();
    updateNewsPage.getFileInput().sendKeys(absolutePath);

    updateNewsPage.ensureUpdateButtonIsClickable();
    updateNewsPage.getUpdateButton().click();

    updateNewsPage.ensureSnackBarIsPresent();
    String snackBarText = updateNewsPage.getSnackBar().getText();
    assertEquals("Successfuly updated Visit World's Most Popular Museum.\nDismiss", snackBarText); 
  }

  @Test
  public void updateHeadingButtonShouldNotBeClickable() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/update/news/4");
    justWait(1000);
    updateNewsPage.getHeadingInput().clear();
    updateNewsPage.getHeadingInput().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
    updateNewsPage.ensureUpdateButtonIsNotClickable();
  }

  @Test
  public void updateContentButtonShouldNotBeClickable() throws InterruptedException {
    logInAdmin();
    driver.get("http://localhost:4200/update/news/4");
    justWait(1000);
    updateNewsPage.getContentInput().clear();
    updateNewsPage.getContentInput().sendKeys(Keys.SPACE, Keys.BACK_SPACE);
    updateNewsPage.ensureUpdateButtonIsNotClickable();
  }

}
