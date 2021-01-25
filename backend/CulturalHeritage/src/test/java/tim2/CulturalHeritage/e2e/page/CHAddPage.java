package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CHAddPage {
  private WebDriver webDriver;

  @FindBy(xpath = "//button[@id=\"add-ch-button\"]")
  private WebElement addButton;

  @FindBy(xpath = "//div[@class=\"mapboxgl-map\"]")
  private WebElement map;

  @FindBy(xpath = "//input[@class=\"mapboxgl-ctrl-geocoder--input\"]")
  private WebElement geocoder;

  @FindBy(xpath = "//button[@id=\"post-btn\"]")
  private WebElement postButton;

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

  public CHAddPage() {

  }

  public CHAddPage(WebDriver driver) {
    this.webDriver = driver;
  }

  public void ensureAddButtonIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(addButton));
  }

  public void ensureMapIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(map));
  }

  public void ensureGeocoderIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(geocoder));
  }

  public void ensurePostButtonIsClickable() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(postButton));
  }

  public void ensurePostButtonIsNotClickable() {
    (new WebDriverWait(webDriver, 30))
        .until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(postButton)));
  }

  public WebElement getAddButton() {
    return addButton;
  }

  public WebElement getMap() {
    return map;
  }

  public WebElement getGeocoder() {
    return geocoder;
  }

  public WebElement getPostButton() {
    return postButton;
  }

  public WebElement getNameInput() {
    return nameInput;
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
