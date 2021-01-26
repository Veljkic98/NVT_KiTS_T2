package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpdateNewsPage {
  private WebDriver webDriver;

  @FindBy(xpath = "//button[@id=\"edit-news-btn-4\"]")
  private WebElement editButton;

  @FindBy(tagName = "simple-snack-bar")
  private WebElement snackBar;

  @FindBy(xpath = "//button[@id=\"update-btn\"]")
  private WebElement updateButton;

  @FindBy(xpath = "//input[@id=\"news heading\"]")
  private WebElement headingInput;

  @FindBy(xpath = "//textarea[@id=\"news content\"]")
  private WebElement contentInput;

  @FindBy(xpath = "//input[@id=\"news-file-input\"]")
  private WebElement fileInput;

  public UpdateNewsPage() {

  }

  public UpdateNewsPage(WebDriver driver) {
    this.webDriver = driver;
  }

  public void ensureEditButtonIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(editButton));
  }
  public void ensureUpdateButtonIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(updateButton));
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

  public WebElement getUpdateButton() {
    return updateButton;
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
