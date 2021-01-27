package tim2.CulturalHeritage.e2e.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.net.URISyntaxException;

import tim2.CulturalHeritage.e2e.page.CHDetailsPage;
import tim2.CulturalHeritage.e2e.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CHDetailsE2ETest {
    
    private WebDriver driver;

    private LoginPage loginPage;

    private CHDetailsPage chDetailsPage;

    private JavascriptExecutor js;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        chDetailsPage = PageFactory.initElements(driver, CHDetailsPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        js = (JavascriptExecutor) driver;
    }

    public void clickOnMap() {
        driver.get("http://localhost:4200/");
        chDetailsPage.ensureIsPresentMarker();
        chDetailsPage.getChMarker().click();
        chDetailsPage.ensureIsPresentDetailSection();
    }

    public void goToLastPage() {
        boolean reachedLastPage = false;
        while(!reachedLastPage) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chDetailsPage.getNextPage());
                chDetailsPage.ensureIsClickableLastPage();
            } catch (Exception e) {
                reachedLastPage = true;
            }
        }
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

    public void logInUserHelen() throws InterruptedException {
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
    public void openCHDetailsTest() throws InterruptedException {

        clickOnMap();
        chDetailsPage.ensureIsPresentTitle();
        chDetailsPage.ensureIsPresentRateSection();
        chDetailsPage.ensureIsPresentImage();
        chDetailsPage.ensureIsPresentDescSection();
        chDetailsPage.ensureIsPresentCommentsSection();
        chDetailsPage.ensureIsPresentNewsSection();
        justWait(1000);

    }

    @Test
    public void openCHDetailsAdminTest() throws InterruptedException {
        logInAdmin();
        clickOnMap();
        chDetailsPage.ensureIsPresentTitle();
        chDetailsPage.ensureIsPresentRateSection();
        chDetailsPage.ensureIsPresentImage();
        chDetailsPage.ensureIsPresentDescSection();
        chDetailsPage.ensureIsPresentCommentsSection();
        chDetailsPage.ensureIsPresentNewsSection();

        chDetailsPage.ensureIsNotPresentAddRating();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getCommentSectionButton());
        Thread.sleep(500);
        chDetailsPage.getCommentSectionButton().click();
        chDetailsPage.ensureIsNotPresentAddComment();

        justWait(1000);
    }

    @Test
    public void openCHDetailsUserTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentTitle();
        chDetailsPage.ensureIsPresentRateSection();
        chDetailsPage.ensureIsPresentImage();
        chDetailsPage.ensureIsPresentDescSection();
        chDetailsPage.ensureIsPresentCommentsSection();
        chDetailsPage.ensureIsPresentNewsSection();

        chDetailsPage.ensureIsPresentAddRating();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getCommentSectionButton());
        Thread.sleep(500);
        chDetailsPage.getCommentSectionButton().click();
        chDetailsPage.ensureIsPresentAddComment();

        justWait(1000);
    }

    @Test
    public void subscribeTest() throws InterruptedException {

        logInUser();

        driver.get("http://localhost:4200/");

        justWait(700);

        chDetailsPage.ensureIsPresentMarker();
        chDetailsPage.getChMarkerId2().click();
        chDetailsPage.ensureIsPresentDetailSection();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getSubscribeButton2());
        Thread.sleep(1000);
        chDetailsPage.ensureIsPresentSubscribeButton2();
        justWait(2000);
        chDetailsPage.getSubscribeButton2().click();
        justWait(2000);

        chDetailsPage.ensureIsPresentUnsubscribeButton2();

        String snackBarText = chDetailsPage.getSnackBar().getText();

        assertEquals("Successfuly subscribed!\nDismiss", snackBarText);

        //ROLLBACK
        chDetailsPage.getUnsubscribeButton2().click();
        justWait(3000);

        snackBarText = chDetailsPage.getSnackBar().getText();

        assertEquals("Successfuly unsubscribed!\nDismiss", snackBarText);
        justWait(2000);
    }

    @Test
    public void addOrRemoveRatingTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentRateSection();
        chDetailsPage.ensureIsPresentAddRating();

        //rate with grade 1
       chDetailsPage.getRatingStarFirst().click();
       justWait(1000);
       assertEquals(("★"), chDetailsPage.getRatingStarFirst().getText());

        //ROLLBACK
        //clicking again on first star will remove rating
        chDetailsPage.getRatingStarFirst().click();
        justWait(1000);
        assertEquals(("☆"), chDetailsPage.getRatingStarFirst().getText());

    }

    @Test
    public void addCommentTest() throws InterruptedException {
        logInUser();
        clickOnMap();

        chDetailsPage.ensureIsPresentCommentsSection();
        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getCommentSectionButton());
        Thread.sleep(500);
        chDetailsPage.ensureIsClickableBtn();
        chDetailsPage.getCommentSectionButton().click();
        chDetailsPage.ensureIsClickableLastPage();

        chDetailsPage.ensureIsPresentAddComment();
        chDetailsPage.getAddComModalBtn().click();

        chDetailsPage.ensureIsPresentAddCommentModal();
        chDetailsPage.getComInput().sendKeys("This is new comment!");

        chDetailsPage.getAddCommentBtn().click();

        justWait(1000);
        goToLastPage();
        assertEquals("This is new comment!", chDetailsPage.getLastComment().getText());

        //ROLLBACK
        chDetailsPage.ensureIsPresentDeleteIcon();
        chDetailsPage.getDeleteCommentIcon().click();
        chDetailsPage.getConfirmDeleteComm().click();
        justWait(1000);
        String lastCommText = "";
        try {
            lastCommText = chDetailsPage.getLastComment().getText();
        } catch (Exception e) {

        }
        assertNotEquals("This is new comment!", lastCommText);
    }

    @Test
    public void seeNewsForCh() throws InterruptedException {

        logInUserHelen();
        clickOnMap();

        chDetailsPage.ensureIsPresentNewsSection();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getNewsSectionButton());
        Thread.sleep(6000);

        chDetailsPage.ensureIsClickableNewsBtn();
        chDetailsPage.getNewsSectionButton().click();
        justWait(2000);
    }

    @Test
    public void seeNewsForChNoNews() throws InterruptedException {

        logInUserHelen();
        
        driver.get("http://localhost:4200/");
        chDetailsPage.ensureIsPresentMarker();
        chDetailsPage.getChMarkerId6().click();
        chDetailsPage.ensureIsPresentDetailSection();

        chDetailsPage.ensureIsPresentNewsSection();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getNewsSectionButton());
        justWait(1000);
        chDetailsPage.ensureIsClickableNewsBtn();
        chDetailsPage.getNewsSectionButton().click();
    }

    @Test
    public void seeNewsForChUnauthenticatedUser() throws InterruptedException {

        clickOnMap();

        chDetailsPage.ensureIsPresentNewsSection();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getNewsSectionButton());
        Thread.sleep(6000);
        chDetailsPage.ensureIsClickableNewsBtn();
        chDetailsPage.getNewsSectionButton().click();
        justWait(2000);

    }

    @Test
    public void addCommentWithImageTest() throws InterruptedException, URISyntaxException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentCommentsSection();
        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getCommentSectionButton());
        Thread.sleep(4000);
        chDetailsPage.ensureIsClickableBtn();
        chDetailsPage.getCommentSectionButton().click();

        chDetailsPage.ensureIsPresentAddComment();
        chDetailsPage.ensureIsClickableLastPage();

        chDetailsPage.getAddComModalBtn().click();

        chDetailsPage.ensureIsPresentAddCommentModal();
        chDetailsPage.getComInput().sendKeys("This is new comment!");

        File file = new File("./src/test/resources/tree.jpg");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        chDetailsPage.getFileInput().sendKeys(absolutePath);

        chDetailsPage.getAddCommentBtn().click();

        justWait(1000);

        goToLastPage();
        assertEquals("This is new comment!", chDetailsPage.getLastComment().getText());
        chDetailsPage.ensureIsPresentLastImage();


        //ROLLBACK
        chDetailsPage.ensureIsPresentDeleteIcon();
        chDetailsPage.getDeleteCommentIcon().click();
        chDetailsPage.getConfirmDeleteComm().click();
        justWait(1000);
        String lastCommText = "";
        try {
            lastCommText = chDetailsPage.getLastComment().getText();
        } catch (Exception e) {

        }
        assertNotEquals("This is new comment!", lastCommText);
    }

    @Test
    public void deleteCommentTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentCommentsSection();

        js.executeScript("arguments[0].scrollIntoView(true);", chDetailsPage.getCommentSectionButton());
        Thread.sleep(4000);
        chDetailsPage.ensureIsClickableBtn();
        chDetailsPage.getCommentSectionButton().click();

        chDetailsPage.ensureIsPresentAddComment();
        chDetailsPage.getAddComModalBtn().click();

        chDetailsPage.ensureIsPresentAddCommentModal();
        chDetailsPage.getComInput().sendKeys("This is last comment!");
        chDetailsPage.getAddCommentBtn().click();

        justWait(1000);
        goToLastPage();
        chDetailsPage.ensureIsPresentDeleteIcon();
        chDetailsPage.getDeleteCommentIcon().click();
        chDetailsPage.getConfirmDeleteComm().click();
        justWait(1000);
        String lastCommText = "";
        try {
            lastCommText = chDetailsPage.getLastComment().getText();
        } catch (Exception e) {

        }
        assertNotEquals("This is last comment!", lastCommText);
    }

}
