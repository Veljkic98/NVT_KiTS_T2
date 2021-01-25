package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddNewsPage {
  private WebDriver webDriver;

  @FindBy(xpath = "//*[@id=\"action-buttons\"]/button[4]")
  private WebElement addNewsButton;

  @FindBy(xpath = "//*[@id=\"add-news-btn\"]")
  private WebElement addButton;

  @FindBy(tagName = "simple-snack-bar")
  private WebElement snackBar;

  @FindBy(xpath = "//input[@id=\"news heading\"]")
  private WebElement headingInput;

  @FindBy(xpath = "//textarea[@id=\"news content\"]")
  private WebElement contentInput;

  @FindBy(xpath = "//input[@id=\"news-file-input\"]")
  private WebElement fileInput;

  public AddNewsPage() {

  }

  public AddNewsPage(WebDriver driver) {
    this.webDriver = driver;
  }

  public void ensureAddNewsButtonIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(addNewsButton));
  }

  public void ensureAddButtonIsClickable() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(addButton));
  }
  
  public void ensureAddButtonIsNotClickable() {
    (new WebDriverWait(webDriver, 30))
        .until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(addButton)));
  }
  public void ensureSnackBarIsPresent() {
    (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(snackBar));
  }

  public WebElement getAddNewsButton() {
    return addNewsButton;
  }

  public WebElement getAddButton() {
    return addButton;
  }

  public WebElement getSnackBar() {
    return snackBar;
  }

  public WebElement getHeadingInput(){
    return headingInput;
  }

  public WebElement getContentInput(){
    return contentInput;
  }

  public WebElement getFileInput() {
    return fileInput;
  }

}
