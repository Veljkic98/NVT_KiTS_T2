package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyProfilePage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"myTabContent\"]/div/div[1]/div[2]/button")
    private WebElement unsubscribeButton;

    @FindBy(xpath = "//*[@id=\"mat-tab-label-0-1\"]")
    private WebElement subscriptionsButton;

    @FindBy(xpath = "//*[@id=\"myTabContent\"]/div/div[4]/div[2]/p")
    private WebElement email;




}
