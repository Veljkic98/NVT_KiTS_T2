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

  public AddNewsPage() {

  }

  public AddNewsPage(WebDriver driver) {
    this.webDriver = driver;
  }

  public void ensureEditButtonIsPresent() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(addNewsButton));
  }

  public WebElement getAddNewsButton() {
    return addNewsButton;
  }

}
