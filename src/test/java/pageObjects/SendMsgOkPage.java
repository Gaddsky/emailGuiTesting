package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SendMsgOkPage {
    private final WebDriver driver;
    private By messageSentTitleLocator = By.className("message-sent__title");
    private By returnToInboxButtonLocator = By.linkText("Перейти во Входящие");
    private By loggedAccountNameLocator = By.id("PH_user-email");
    private By sentTitleLocator = By.className("message-sent__title");

    public SendMsgOkPage(WebDriver driver) {
        this.driver = driver;

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(messageSentTitleLocator));
    }

    public InboxPage returnToInbox() {
        driver.findElement(returnToInboxButtonLocator).click();
        return new InboxPage(driver);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getLoggedAccountName() {
        return driver.findElement(loggedAccountNameLocator).getText();
    }

    public String getSentTitle() {
        return driver.findElement(sentTitleLocator).getText();
    }
}
