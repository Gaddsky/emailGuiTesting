package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


/**
 * Representation of Inbox page, used in tests
 */
public class InboxPage {
    private final WebDriver driver;
    /** Locator for the 'New letter' button */
    private By newLetterButtonLocator = By.linkText("Написать письмо");
    /** Locator for the list of the letter's checkboxes */
    private By lettersListCheckboxLocator = By.className("js-item-checkbox");
    /** Locator for the list of the letter's headers */
    private By LettersListLocator = By.className("b-datalist__item__subj");
    /** Locator for the 'Remove' button */
    private By removeButtonLocator = By.cssSelector("div[data-name='remove'");
    /** Locator for the current account name */
    private By loggedAccountNameLocator = By.id("PH_user-email");
    /** Locator for the active email folder */
    private By activeFolderLocator = By.className("b-nav__item_active");
    /** Locator for the unread letter */
    private By unreadLocator = By.className("b-datalist__item_unread");


    public InboxPage(WebDriver driver) {
        this.driver = driver;
        // waiting for page is loaded
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(activeFolderLocator));
    }

    /**
     * Click on 'New letter' button
     * @return instance of the ComposeNewPage object
     */
    public ComposeNewPage clickNewLetter() {
        driver.findElement(newLetterButtonLocator).click();
        return new ComposeNewPage(driver);
    }

    /**
     * Click on the checkbox of the first letter in inbox folder
     * @return this
     */
    public InboxPage chooseFirstLetter() {
        List<WebElement> lettersCheckboxList = driver.findElements(lettersListCheckboxLocator);
        WebElement letterCheckBox = lettersCheckboxList.get(0);
        letterCheckBox.click();
        return this;
    }

    /**
     * @return header of the first letter in inbox folder
     */
    public String getFirstLetterHeader() {
        return driver.findElements(LettersListLocator).get(0).getText();
    }

    /**
     * @return count of letters in inbox folder page
     */
    public int getLettersCount() {
        return driver.findElements(lettersListCheckboxLocator).size();
    }

    /**
     * Click on the 'Delete' button
     * @return this
     */
    public InboxPage deleteFirstLetter() {
        driver.findElement(removeButtonLocator).click();
        return this;
    }

    /**
     * @return true, if Inbox folder is not empty
     */
    public Boolean isInboxLetters() {
        return driver.findElements(lettersListCheckboxLocator).size() > 0;
    }

    /**
     * @return title of the page
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * @return url of the page
     */
    public String getUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * @return name of the current account
     */
    public String getLoggedAccountName() {
        return driver.findElement(loggedAccountNameLocator).getText();
    }

    /**
     * @return name of the active folder
     */
    public String getActiveFolderName() {
        return driver.findElement(activeFolderLocator).getText();
    }

    /**
     * Wait for unread message appears in inbox folder
     * @return this
     */
    public InboxPage waitForUnread() {
        if (!isInboxLetters()) driver.navigate().refresh();
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions
                        .presenceOfElementLocated(unreadLocator));
        return this;
    }

    /**
     * Wait for notification about message was deleted appears
     * @return this
     */
    public InboxPage waitForDelete() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//span[contains(@class, 'notify-message__title__text_ok') and contains (text(), 'Удалено 1 письмо')]")));
        return this;
    }
}
