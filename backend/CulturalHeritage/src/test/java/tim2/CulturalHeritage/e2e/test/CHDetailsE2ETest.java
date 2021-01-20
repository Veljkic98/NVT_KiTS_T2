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

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        chDetailsPage = PageFactory.initElements(driver, CHDetailsPage.class);
        loginPage = PageFactory.initElements(driver, LoginPage.class);


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

        chDetailsPage.getCommentSectionButton().click();
        chDetailsPage.ensureIsPresentAddComment();

        justWait(1000);
    }

    @Test
    public void addOrRemoveRatingTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentRateSection();
        chDetailsPage.ensureIsPresentAddRating();

        String secondStarState =   chDetailsPage.getRatingStarSecond().getText();
        String firstStarState =   chDetailsPage.getRatingStarFirst().getText();
        String expectedStateFirstStar;
        //if user already grated ch with rate 1 after click he/she will remove his/hers rate
        if (firstStarState.equals("★") && secondStarState.equals("☆")) {
            expectedStateFirstStar = ("☆");
        } else {
            //in all other cases grade will be 1
            expectedStateFirstStar = ("★");
        }

       chDetailsPage.getRatingStarFirst().click();
       justWait(1000);
       assertEquals(expectedStateFirstStar, chDetailsPage.getRatingStarFirst().getText());
    }

    @Test
    public void changeRatingTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentRateSection();
        chDetailsPage.ensureIsPresentAddRating();

        String secondStarState =   chDetailsPage.getRatingStarSecond().getText();
        String firstStarState =   chDetailsPage.getRatingStarFirst().getText();
        String expectedFullstate = ("★");

        //if ch is not graded with grade 1 make required conditions
        if (!(firstStarState.equals("★") && secondStarState.equals("☆"))) {
            chDetailsPage.getRatingStarFirst().click();
            justWait(1000);
        }
        chDetailsPage.getRatingStarSecond().click();
        justWait(1000);
        assertEquals(expectedFullstate, chDetailsPage.getRatingStarFirst().getText());
        assertEquals(expectedFullstate, chDetailsPage.getRatingStarSecond().getText());
    }


    @Test
    public void addCommentTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentCommentsSection();
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
    }

    @Test
    public void addCommentWithImageTest() throws InterruptedException, URISyntaxException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentCommentsSection();
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
    }

    @Test
    public void deleteCommentTest() throws InterruptedException {
        logInUser();
        clickOnMap();
        chDetailsPage.ensureIsPresentCommentsSection();
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
