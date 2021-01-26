package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CHTypePage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"editTypeInput\"]")
    private WebElement editTypeNameInput;

    @FindBy(tagName = "simple-snack-bar")
    private WebElement snackBar;

    @FindBy(xpath = "//*[@id=\"delete4\"]")
    private WebElement deleteButtonSuccess;

    @FindBy(xpath = "//*[@id=\"delete1\"]")
    private WebElement deleteButtonFail;

    @FindBy(xpath = "//*[@id=\"deleteTypeBtnConfirm\"]")
    private WebElement deleteButtonConfirm;

    @FindBy(xpath = "//*[@id=\"edit1\"]")
    private WebElement editTypeButton;

    @FindBy(xpath = "//*[@id=\"saveChangesTypeBtn\"]")
    private WebElement saveChangesBtn;

    @FindBy(xpath = "//*[@id=\"show1\"]")
    private WebElement showSubtypesButton;

    @FindBy(xpath = "//*[@id=\"editSubtype1\"]")
    private WebElement editSubtypesButton;

    @FindBy(xpath = "//*[@id=\"inner-table\"]/tbody/tr[1]/td[3]/div/button")
    private WebElement deleteSubtypesButton;

    @FindBy(xpath = "//*[@id=\"inner-table\"]/tbody/tr[2]/td[3]/div/button")
    private WebElement deleteSubtypesButtonFail;

    @FindBy(xpath = "//*[@id=\"mat-dialog-0\"]/app-dialog-content-example-dialog/mat-dialog-actions/button[2]")
    private WebElement confirmSubtypeDeleteButton;

    @FindBy(xpath = "//*[@id=\"add-new-subtype-btn-1\"]")
    private WebElement addNewSubtypeBtn;

    @FindBy(xpath = "//*[@id=\"post-subtype-btn\"]")
    private WebElement postSubtypeBtn;

    @FindBy(xpath = "//*[@id=\"subtype name\"]")
    private WebElement subtypeInput;

    public CHTypePage(){}


    public CHTypePage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public void ensureAreDisplayedDeleteButtons() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,\"delete\")]")));
    }

    public void ensureAreDisplayedEditButtons() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,\"edit\")]")));
    }

    public void ensureAreDisplayedShowButtons() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,\"show\")]")));
    }

    public void ensureNotVisibleDeletedType() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("type4")));
    }

    public void ensureVisibleNotDeletedType() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("type1")));
    }

    public void ensureVisibleErrorModal() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("errorModal")));
    }

    public void ensureIsVisibleEditModal() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("app-ch-type-edit-form")));
    }

    public void ensurePostSubtypeButtonIsClickable() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(postSubtypeBtn));
      }
    
      public void ensurePostSubtypeButtonIsNotClickable() {
        (new WebDriverWait(webDriver, 30))
            .until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(postSubtypeBtn)));
      }

      public void ensureSnackBarIsPresent() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(snackBar));
      }


    public WebElement getEditTypeNameInput() { return editTypeNameInput; }

    public WebElement getEditTypeButton() { return editTypeButton; }

    public WebElement getSnackBar() { return snackBar; }

    public WebElement getDeleteButtonSuccess() { return deleteButtonSuccess; }

    public WebElement getDeleteButtonFail() { return deleteButtonFail; }

    public WebElement getDeleteButtonConfirm() { return deleteButtonConfirm; }

    public WebElement getSaveChangesBtn() { return saveChangesBtn; }

    public WebElement getShowSubtypesButton() { return showSubtypesButton; }

    public WebElement getEditSubtypesButton() { return editSubtypesButton; }

    public WebElement getDeleteSubtypesButton() { return deleteSubtypesButton; }

    public WebElement getDeleteSubtypesButtonFail() { return deleteSubtypesButtonFail; }

    public WebElement getConfirmSubtypeDeleteButton() { return confirmSubtypeDeleteButton; }

    public WebElement getAddNewSubtypeBtn(){ return addNewSubtypeBtn; }

    public WebElement getPostSubtypeBtn(){ return postSubtypeBtn; }
    
    public WebElement getSubtypeInput(){ return subtypeInput; }
}