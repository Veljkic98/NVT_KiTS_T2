package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyProfilePage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"mat-menu-panel-0\"]/div/a[1]")
    private WebElement profileButton;

    @FindBy(xpath = "//*[@id=\"mat-menu-panel-0\"]/div/a[2]")
    private WebElement subscriptionsToolbarButton;

    @FindBy(xpath = "//*[@id=\"myTabContent\"]/div/div[1]/div[2]/button")
    private WebElement unsubscribeButton;

    @FindBy(xpath = "//*[@id=\"mat-tab-label-0-1\"]")
    private WebElement subscriptionsButton;

    @FindBy(xpath = "//*[@id=\"myTabContent\"]/div/div[4]/div[2]/p")
    private WebElement email;

    @FindBy(xpath="/html/body/app-root/app-home-page/app-toolbar/mat-toolbar/mat-toolbar-row/div[2]/a")
    private WebElement expandToolbarButton;

    @FindBy(xpath = "//*[@id=\"myTabContent\"]/div[contains(@class, 'about')]")
    private WebElement aboutContent;

    @FindBy(xpath = "//*[@id=\"myTabContent\"]/div[contains(@class, 'subs')]")
    private WebElement subscriptionsContent;



    public MyProfilePage() { }

    public MyProfilePage(WebDriver driver) { this.driver = driver; }

    public WebElement getProfileButton() {
        return profileButton;
    }

    public WebElement getUnsubscribeButton() {
        return unsubscribeButton;
    }

    public WebElement getSubscriptionsButton() {
        return subscriptionsButton;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getExpandToolbarButton() {
        return expandToolbarButton;
    }

    public WebElement getAboutContent() {
        return aboutContent;
    }

    public WebElement getSubscriptionsContent() {
        return subscriptionsContent;
    }

    public WebElement getSubscriptionsToolbarButton() {
        return subscriptionsToolbarButton;
    }
}
