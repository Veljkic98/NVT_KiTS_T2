package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MapPage {
  private WebDriver webDriver;

  @FindBy(xpath = "//div[@id=\"ch_1\"]/button")
  private WebElement chMarker;

  @FindBy(xpath = "//div[@class=\"mapboxgl-map\"]")
  private WebElement map;

  public MapPage() {

  }

  public MapPage(WebDriver driver) {
    this.webDriver = driver;
  }

  public void ensureIsPresentMarker() {
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(chMarker));
  }

  public void ensureMapIsPresent(){
    (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(map));
  }

  public WebElement getChMarker() {
    return chMarker;
  }

  public WebElement getMap(){
    return map;
  }
}
