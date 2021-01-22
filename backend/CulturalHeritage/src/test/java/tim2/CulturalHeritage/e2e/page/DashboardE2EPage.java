package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardE2EPage {
    private WebDriver driver;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackbar;

    @FindBy(xpath = "//*[@id=\"filter-buttons\"]/button[1]")
    private WebElement goButton;

    @FindBy(xpath = "//*[@id=\"filter-buttons\"]/button[2]")
    private WebElement resetButton;

    @FindBy(xpath = "//*[@id=\"mat-select-0\"]")
    private WebElement filterTypeSelect;

    @FindBy(xpath = "//*[@id=\"mat-option-0\"]")
    private WebElement filterTypeName;

    @FindBy(xpath = "//*[@id=\"mat-option-1\"]")
    private WebElement filterTypeSubtype;

    @FindBy(xpath = "//*[@id=\"mat-option-2\"]")
    private WebElement filterTypeCity;

    @FindBy(xpath = "//*[@id=\"mat-option-3\"]")
    private WebElement filterTypeCountry;

    @FindBy(xpath = "//*[@id=\"filter-value\"]")
    private WebElement filterValueInput;

    @FindBy(xpath = "//div[@id=\"ch_1\"]/button")
    private WebElement chMarker;

    public DashboardE2EPage() {}

    public DashboardE2EPage(WebDriver driver) { this.driver = driver; }

    public void ensureIsPresentMarker() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(chMarker));
    }

    public void ensureIsClickableGoButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(goButton));
    }

    public void ensureIsClickableResetButton() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(resetButton));
    }

    public void ensureIsPresentInputField() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(filterValueInput));
    }

    public void ensureIsClickableTypeSelect() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(filterTypeSelect));
    }

    public void ensureIsPresentMap() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("app-maps")));
    }


    public WebElement getSnackbar() {
        return snackbar;
    }

    public WebElement getGoButton() {
        return goButton;
    }

    public WebElement getResetButton() {
        return resetButton;
    }

    public WebElement getFilterTypeSelect() {
        return filterTypeSelect;
    }

    public WebElement getFilterTypeName() {
        return filterTypeName;
    }

    public WebElement getFilterTypeSubtype() {
        return filterTypeSubtype;
    }

    public WebElement getFilterTypeCity() {
        return filterTypeCity;
    }

    public WebElement getFilterTypeCountry() {
        return filterTypeCountry;
    }

    public WebElement getFilterValueInput() {
        return filterValueInput;
    }
}
