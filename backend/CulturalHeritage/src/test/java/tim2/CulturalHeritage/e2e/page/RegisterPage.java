package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstName;

    @FindBy(xpath = "//*[@id=\"lastName\"]")
    private WebElement lastName;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement email;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"confirmPass\"]")
    private WebElement passwordConfirm;

    @FindBy(xpath = "//*[@id=\"registerBtn\"]")
    private WebElement signUpBtn;

    @FindBy(tagName = "mat-error")
    private WebElement formError;

    @FindBy(xpath = "//a[@routerLink=\"/login\"]")
    private WebElement loginLink;


    public RegisterPage() {
    }

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedFirstName() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("firstName")));
    }

    public void ensureIsDisplayedLastName() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("lastName")));
    }

    public void ensureIsDisplayedEmail() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
    }

    public void ensureIsDisplayedPassword() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("password")));
    }

    public void ensureIsDisplayedPasswordConfirm() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("confirmPass")));
    }

    public void ensureIsNotVisibleSuccessDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("success")));
    }

    public void ensureIsVisibleSuccessDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.textToBe(By.className("success"), "You have successfully created account! Please check your email for verification link."));
    }

    public void ensureIsNotVisibleErrorDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.className("error")));
    }

    public void ensureIsVisibleErrorDiv() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.className("error")));
    }


    public WebElement getEmail() {
        return email;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getPasswordConfirm() {
        return passwordConfirm;
    }

    public WebElement getSignUpBtn() {
        return signUpBtn;
    }

    public WebElement getFirstName() { return firstName; }

    public WebElement getLastName() { return lastName; }

    public WebElement getFormError() { return formError; }

    public WebElement getLoginLink() { return loginLink; }
}
