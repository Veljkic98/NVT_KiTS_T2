package tim2.CulturalHeritage.e2e.test;

import tim2.CulturalHeritage.e2e.page.LoginPage;
import tim2.CulturalHeritage.e2e.page.CHTypePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CHTypeE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private CHTypePage chTypePage;

    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        chTypePage = PageFactory.initElements(driver, CHTypePage.class);
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
    public void redirectNotLoggedIn() throws InterruptedException {

        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
    }

    @Test
    public void redirectNotAdminLoggedIn() throws InterruptedException {
        logInUser();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    @Test
    public void testDeleteTypeSuccess() throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        chTypePage.ensureAreDisplayedDeleteButtons();

        chTypePage.getDeleteButtonSuccess().click();

        chTypePage.getDeleteButtonConfirm().click();
        justWait(500);

        String snackBarText = chTypePage.getSnackBar().getText();

        assertEquals("Successfuly deleted the type!\nDismiss", snackBarText);
        chTypePage.ensureNotVisibleDeletedType();
    }

    @Test
    public void testDeleteTypeFail() throws InterruptedException {

        logInAdmin();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        chTypePage.ensureAreDisplayedDeleteButtons();

        chTypePage.getDeleteButtonFail().click();

        chTypePage.getDeleteButtonConfirm().click();
        justWait(500);

        chTypePage.ensureVisibleNotDeletedType();
        chTypePage.ensureVisibleErrorModal();
    }

    @Test
    public void testEditTypeSuccess() throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        chTypePage.ensureAreDisplayedEditButtons();

        chTypePage.getEditTypeButton().click();

        chTypePage.ensureIsVisibleEditModal();
        justWait(500);
        chTypePage.getEditTypeNameInput().clear();
        chTypePage.getEditTypeNameInput().sendKeys("New name");
        justWait(1000);
        chTypePage.getSaveChangesBtn().click();

        justWait(1000);

        String snackBarText = chTypePage.getSnackBar().getText();

        assertEquals("Successfuly changed name of type!\nDismiss", snackBarText);
    }

    @Test
    public void testEditTypeError() throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        chTypePage.ensureAreDisplayedEditButtons();
        chTypePage.getEditTypeButton().click();

        chTypePage.ensureIsVisibleEditModal();
        chTypePage.getEditTypeNameInput().clear();
        //Type name already exist
        chTypePage.getEditTypeNameInput().sendKeys("institution");
        justWait(1000);
        chTypePage.getSaveChangesBtn().click();

        justWait(1000);

        String snackBarText = chTypePage.getSnackBar().getText();

        assertEquals("Name already exist!\nDismiss", snackBarText);
    }

    @Test
    public void testEditSubtypeSuccess() throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        chTypePage.ensureAreDisplayedShowButtons();

        chTypePage.getShowSubtypesButton().click();

        justWait(200);
        chTypePage.getEditSubtypesButton().click();
        chTypePage.getEditTypeNameInput().clear();
        chTypePage.getEditTypeNameInput().sendKeys("New subtype name");
        justWait(1000);
        chTypePage.getSaveChangesBtn().click();

        justWait(1000);

        String snackBarText = chTypePage.getSnackBar().getText();

        assertEquals("Successfuly changed name of subtype!\nDismiss", snackBarText);
    }

    @Test
    public void testEditSubtypeError() throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");

        justWait(1000);

        chTypePage.ensureAreDisplayedShowButtons();

        chTypePage.getShowSubtypesButton().click();

        justWait(200);
        chTypePage.getEditSubtypesButton().click();
        chTypePage.getEditTypeNameInput().clear();
        //subtype name already exist for chosen type
        chTypePage.getEditTypeNameInput().sendKeys("fair");
        justWait(1000);
        chTypePage.getSaveChangesBtn().click();

        justWait(1000);

        String snackBarText = chTypePage.getSnackBar().getText();

        assertEquals("Name already exist!\nDismiss", snackBarText);
    }

    @Test
    public void testDeleteSubtypeSuccess() throws InterruptedException {
        logInAdmin();
        justWait(1000);
        driver.get("http://localhost:4200/manage/types");
        justWait(1000);

        chTypePage.ensureAreDisplayedShowButtons();
        chTypePage.getShowSubtypesButton().click();
        justWait(1000);

        chTypePage.getDeleteSubtypesButton().click();
        justWait(1000);

        chTypePage.getConfirmSubtypeDeleteButton().click();

        justWait(1000);

        String snackBarText = chTypePage.getSnackBar().getText();

        assertEquals("Successfuly deleted the subtype!\nDismiss", snackBarText);

    }

    @Test
    public void testDeleteSubtypeFail() throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");
        justWait(1000);

        chTypePage.ensureAreDisplayedShowButtons();
        chTypePage.getShowSubtypesButton().click();
        justWait(1000);

        chTypePage.getDeleteSubtypesButtonFail().click();
        justWait(1000);

        chTypePage.getConfirmSubtypeDeleteButton().click();

        justWait(1000);

        String snackBarText = chTypePage.getSnackBar().getText();


        assertEquals("Can\'t delete that subtype. There are cultural heritages of that subtype.\nDismiss", snackBarText);

    }

    @Test
    public void addSubtypeBtnCheck()throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");    
        chTypePage.ensureAreDisplayedShowButtons();
        chTypePage.getAddNewSubtypeBtn().click();
        assertEquals("http://localhost:4200/add/subtype/1", driver.getCurrentUrl());
    }

    @Test
    public void addSubtypeOk()throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/add/subtype/1");    
        chTypePage.getSubtypeInput().clear();
        chTypePage.getSubtypeInput().sendKeys("new subtype HHHHHH");
        chTypePage.ensurePostSubtypeButtonIsClickable();
        chTypePage.getPostSubtypeBtn().click();
        chTypePage.ensureSnackBarIsPresent();
        String snackBarText = chTypePage.getSnackBar().getText();
        assertEquals("Successfuly added the subtype!\nDismiss", snackBarText);
    }

    @Test
    public void addSubtypeFail()throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/add/subtype/1");    
        chTypePage.getSubtypeInput().clear();
        chTypePage.getSubtypeInput().sendKeys("festival");
        chTypePage.ensurePostSubtypeButtonIsNotClickable();
    }

    @Test
    public void addTypeBtnCheck() throws InterruptedException{
        logInAdmin();
        driver.get("http://localhost:4200/manage/types");    
        chTypePage.ensureAddTypeButtonIsClickable();
        chTypePage.getAddNewTypeBtn().click();
        assertEquals("http://localhost:4200/new-type", driver.getCurrentUrl());
    }

    @Test
    public void addTypeOk()throws InterruptedException {
        logInAdmin();
        driver.get("http://localhost:4200/new-type");    
        chTypePage.getTypeInput().clear();
        chTypePage.getTypeInput().sendKeys("Moj Novi Tip");
        chTypePage.ensurePostTypeButtonIsClickable();
        chTypePage.getPostTypeBtn().click();

        chTypePage.ensureSnackBarIsPresent();
        String snackBarText = chTypePage.getSnackBar().getText();
        assertEquals("Successfuly added Moj Novi Tip type.\nDismiss", snackBarText);
    }

    @Test
    public void addTypeFail() throws InterruptedException{
        logInAdmin();
        driver.get("http://localhost:4200/new-type");    
        chTypePage.getTypeInput().clear();
        chTypePage.getTypeInput().sendKeys("Manifestation");
        chTypePage.ensurePostTypeButtonIsNotClickable();
    }

}