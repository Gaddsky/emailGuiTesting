package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Representation of 'Send message ok' page, used in tests
 */
public class SendMsgOkPage {
    private final WebDriver driver;
    /** Locator for the title of 'Message Sent' report */
    private By messageSentTitleLocator = By.className("message-sent__title");
    /** Locator for 'Return to Inbox' button */
    private By returnToInboxButtonLocator = By.linkText("Перейти во Входящие");
    /** Locator for the current account name */
    private By loggedAccountNameLocator = By.id("PH_user-email");

    public SendMsgOkPage(WebDriver driver) {
        this.driver = driver;
        // waiting for page is loaded
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(messageSentTitleLocator));
    }

    /**
     * Click on 'Return to Inbox' button
     * @return instance of the InboxPage object
     */
    public InboxPage returnToInbox() {
        driver.findElement(returnToInboxButtonLocator).click();
        return new InboxPage(driver);
    }

    /**
     * @return title of the page
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * @return current url of the page
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
     * @return title of 'Message Sent' report
     */
    public String getSentTitle() {
        return driver.findElement(messageSentTitleLocator).getText();
    }
}
