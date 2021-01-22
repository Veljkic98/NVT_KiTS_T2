package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement email;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"loginBtn\"]")
    private WebElement loginBtn;

    @FindBy(tagName = "mat-error")
    private WebElement formError;

    @FindBy(xpath = "//a[@routerLink=\"/register\"]")
    private WebElement registerLink;

    @FindBy(xpath = "//*[@id=\"signOutButton\"]")
    private WebElement signOutButton;


    public LoginPage() {
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedEmail() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
    }

    public void ensureIsDisplayedPassword() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("password")));
    }

    public void ensureIsNotVisibleSuccessDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("success")));
    }

    public void ensureIsVisibleSuccessDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBe(By.className("success"), " You have successfully logged in!"));
    }

    public void ensureIsNotVisibleErrorDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("error")));
    }

    public void ensureIsVisibleErrorDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.className("error")));
    }

    public void ensureBadCredentialsErrorText() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBe(By.className("error"), "Bad credentials"));
    }

    public void ensureNotVerifiedErrorText() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBe(By.className("error"), "Your account is not verified"));
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getFormError() { return formError; }

    public WebElement getRegisterLink() { return registerLink; }

    public WebElement getLoginBtn() { return loginBtn; }

    public WebElement getSignOutButton() { return signOutButton; }
}
