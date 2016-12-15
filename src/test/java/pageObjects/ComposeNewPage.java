package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Representation of 'Compose New Letter' page, used in tests
 */
public class ComposeNewPage {
    private final WebDriver driver;
    /** Locator for the 'To' input field */
    private By toLocator = By.cssSelector("textarea[data-original-name='To']");
    /** Locator for the 'Subject' input field */
    private By subjectLocator = By.name("Subject");
    /** Locator for the 'Send' button */
    private By sendButtonLocator = By.cssSelector("div[data-name='send']");
    /** Locator for the frame with "Text" input field */
    private By textIFrameLocator = By.cssSelector("iframe[id^=toolkit-]");
    /** Locator for the "Text" input field */
    private By textLocator = By.id("tinymce");

    public ComposeNewPage(WebDriver driver) {
        this.driver = driver;
        // waiting for page is loaded
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(textIFrameLocator));
    }

    /**
     * Enter the destination email address into 'To' input field
     * @param to destination email address
     * @return this
     */
    public ComposeNewPage typeTo(String to) {
        driver.findElement(toLocator).sendKeys(to);
        return this;
    }

    /**
     * Enter the subject into 'Subject' input field
     * @param subject subject of the email
     * @return this
     */
    public ComposeNewPage typeSubject(String subject) {
        driver.findElement(subjectLocator).sendKeys(subject);
        return this;
    }

    /**
     * Enter the text of the email into 'Text' input field
     * @param text text of the email
     * @return this
     */
    public ComposeNewPage typeText(String text) {
        driver.switchTo().frame(driver.findElement(textIFrameLocator));
        driver.findElement(textLocator).sendKeys(text);
        driver.switchTo().defaultContent();
        return this;
    }

    /**
     * Click on the 'Send' button
     * @return instance of the SendMsgOkPage object
     */
    public SendMsgOkPage sendButtonClick() {
        driver.findElement(sendButtonLocator).click();
        return new SendMsgOkPage(driver);
    }
}
