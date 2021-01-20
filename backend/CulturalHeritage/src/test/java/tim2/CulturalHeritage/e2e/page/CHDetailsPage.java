package tim2.CulturalHeritage.e2e.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CHDetailsPage {

    private WebDriver webDriver;

    @FindBy(xpath = "//div[@id=\"ch_1\"]/button")
    private WebElement chMarker;

    @FindBy(xpath = "//mat-accordion//mat-expansion-panel[@id=\"comment-section\"]")
    private WebElement commentSectionButton;

    @FindBy(xpath = "//button[@id=\"add-com-modal-btn\"]")
    private WebElement addComModalBtn;

    @FindBy(xpath = "//ngb-rating/span[@class=\"ng-star-inserted\"][1]")
    private WebElement ratingStarFirst;

    @FindBy(xpath = "//ngb-rating/span[@class=\"ng-star-inserted\"][2]")
    private WebElement ratingStarSecond;

    @FindBy(xpath = "//button[@id=\"add-com-btn\"]")
    private WebElement addCommentBtn;

    @FindBy(xpath = "//*[@id=\"com-content-input\"]")
    private WebElement comInput;

    @FindBy(xpath = "//*[@id=\"com-file-input\"]")
    private WebElement fileInput;

    @FindBy(xpath = "//pagination-controls//*[@aria-label=\"Next page\"]")
    private WebElement nextPage;

    @FindBy(xpath = "//app-comments//*[@class=\"comment-content\"][last()]")
    private WebElement lastComment;

    @FindBy(xpath = "//*[@id=\"delete-comment-icon\"][last()]")
    private WebElement deleteCommentIcon;

    @FindBy(xpath = "//button[@id=\"delete-comment-btn\"]")
    private WebElement confirmDeleteComm;


    public CHDetailsPage() {
    }

    public CHDetailsPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public void ensureIsPresentMarker() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(chMarker));
    }

    public void ensureIsPresentDetailSection() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("app-cultural-heritage")));
    }

    public void ensureIsPresentTitle() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("mat-card-title")));
    }

    public void ensureIsPresentRateSection() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.className("rate-section")));
    }

    public void ensureIsPresentImage() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-card//img")));
    }

    public void ensureIsPresentDescSection() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-accordion//mat-expansion-panel[@id=\"desc-section\"]")));
    }

    public void ensureIsPresentCommentsSection() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOf(commentSectionButton));
    }

    public void ensureIsPresentNewsSection() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-accordion//mat-expansion-panel[@id=\"news-section\"]")));
    }

    public void ensureIsNotPresentAddRating() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.tagName("app-rating")));
    }

    public void ensureIsNotPresentAddComment() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@id=\"add-com-modal-btn\"]")));
    }

    public void ensureIsPresentAddRating() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("app-rating")));
    }

    public void ensureIsPresentAddComment() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id=\"add-com-modal-btn\"]")));
    }

    public void ensureIsPresentAddCommentModal() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("ngb-modal-window")));
    }

    public void ensureIsClickableLastPage() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(nextPage));
    }

    public void ensureIsPresentLastImage() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-comments//img[last()]")));
    }

    public void ensureIsPresentDeleteIcon() {
        (new WebDriverWait(webDriver, 30)).until(ExpectedConditions.elementToBeClickable(deleteCommentIcon));
    }


    public WebElement getChMarker() { return chMarker; }

    public WebElement getCommentSectionButton() { return commentSectionButton; }

    public WebElement getAddComModalBtn() { return addComModalBtn; }

    public WebElement getAddCommentBtn() { return addCommentBtn; }

    public WebElement getRatingStarFirst() { return ratingStarFirst; }

    public WebElement getRatingStarSecond() { return ratingStarSecond; }

    public WebElement getComInput() { return comInput; }

    public WebElement getFileInput() { return fileInput; }

    public WebElement getNextPage() { return nextPage; }

    public WebElement getLastComment() { return lastComment; }

    public WebElement getDeleteCommentIcon() { return deleteCommentIcon; }

    public WebElement getConfirmDeleteComm() { return confirmDeleteComm; }

}
