package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CHUpdatePage {
  private WebDriver webDriver;

  @FindBy(xpath = "//*[@id=\"action-buttons\"]/button[2]")
  private WebElement editButton;

  @FindBy(xpath = "//div[@class=\"mapboxgl-map\"]")
  private WebElement map;

  @FindBy(xpath = "//input[@class=\"mapboxgl-ctrl-geocoder--input\"]")
  private WebElement geocoder;

  @FindBy(xpath = "//button[@id=\"update-btn\"]")
  private WebElement updateButton;

  @FindBy(xpath = "//input[@id=\"name\"]")
  private WebElement nameInput;

  @FindBy(xpath = "//textarea[@id=\"description\"]")
  private WebElement descriptionInput;

  @FindBy(xpath = "//*[@id=\"select-subtype\"]")
  private WebElement subtypeSelect;

  @FindBy(xpath = "//mat-option/span[contains(.,'festival')]")
  private WebElement subtypeOption;

  @FindBy(xpath = "//input[@id=\"ch-file-input\"]")
  private WebElement fileInput;

  @FindBy(tagName = "simple-snack-bar")
  private WebElement snackBar;

  public CHUpdatePage() {

  }

  public CHUpdatePage(WebDriver driver) {
    this.webDriver = driver;
  }

  public void ensureEditButtonIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(editButton));
  }

  public void ensureMapIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(map));
  }

  public void ensureGeocoderIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(geocoder));
  }

  public void ensureUpdateButtonIsClickable() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(updateButton));
  }


  public void ensureUpdateButtonIsNotClickable() {
    (new WebDriverWait(webDriver, 30))
        .until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(updateButton)));
  }

  public void ensureSnackBarIsPresent() {
    (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(snackBar));
  }

  public WebElement getEditButton() {
    return editButton;
  }

  public WebElement getMap() {
    return map;
  }

  public WebElement getGeocoder() {
    return geocoder;
  }

  public WebElement getUpdateButton() {
    return updateButton;
  }

  public WebElement getNameInput() {
    return nameInput;
  }

  public WebElement getSnackBar() {
    return snackBar;
  }

  public WebElement getDescriptionInput() {
    return descriptionInput;
  }

  public WebElement getSubtypeSelect() {
    return subtypeSelect;
  }

  public WebElement getSubtypeOption() {
    return subtypeOption;
  }

  public WebElement getFileInput() {
    return fileInput;
  }
}
