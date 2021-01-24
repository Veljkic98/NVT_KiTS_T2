package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CHTablePage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"all-ch-table\"]")
    private WebElement chTable;

    @FindBy(xpath = "//*[@id=\"ch4\"]")
    private WebElement chRow;

    @FindBy(xpath = "//*[@id=\"delete-button4\"]")
    private WebElement deleteButton;
    
    @FindBy(xpath = "//*[@id=\"delete-button1\"]")
    private WebElement deleteButtonFail;

    @FindBy(xpath = "//*[@id=\"delete-button-confirm\"]")
    private WebElement deleteButtonConfirm;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    // region constructors

    public CHTablePage() {
    }

    public CHTablePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    // endregion

    // region ensure

    public void ensureCHTableIsDisplayed() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(chTable));
    }

    public void ensureAreDisplayedDeleteButtons() {
        (new WebDriverWait(webDriver, 30)).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,\"delete-button\")]")));
    }

    public void ensureNotVisibleDeletedCH() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("ch4")));
    }

    public void ensureVisibleNotDeletedCH() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.id("ch1")));
	}

	public void ensureVisibleErrorModal() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("errorModal")));
	}

    // endregion

    // region get

    public WebElement getCHTable() {
        return chTable;
    }

    public WebElement getDeleteButtonSuccess() {
        return deleteButton;
    }

    public WebElement getDeleteButtonConfirm() {
        return deleteButtonConfirm;
    }

    public WebElement getSnackBar() {
        return snackBar;
    }

	public WebElement getDeleteButtonFail() {
		return deleteButtonFail;
    }
    
    //endregion

}
