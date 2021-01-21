package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageNewsPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"btn-div\"]/button[2] ")
    private WebElement deleteButton;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/div[3]/button[2]")
    private WebElement confirmDeleteButton;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(xpath = "/html/body/app-root/app-home-page/div/app-news/div/h1")
    private WebElement headingEmpty;


    public ManageNewsPage() {}

    public ManageNewsPage(WebDriver driver) {this.driver = driver;}

    public WebElement getDeleteButton() {
        return deleteButton;
    }

    public WebElement getConfirmDeleteButton() {
        return confirmDeleteButton;
    }

    public WebElement getSnackBar() {
        return snackBar;
    }

    public WebElement getHeadingEmpty() {
        return headingEmpty;
    }
}
