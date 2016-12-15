package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ComposeNewPage {
    private final WebDriver driver;
    private By toLocator = By.cssSelector("textarea[data-original-name='To']");
    private By subjectLocator = By.name("Subject");
    private By sendButtonLocator = By.cssSelector("div[data-name='send']");
    private By textIFrameLocator = By.cssSelector("iframe[id^=toolkit-]");
    private By textLocator = By.id("tinymce");

    public ComposeNewPage(WebDriver driver) {
        this.driver = driver;

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(textIFrameLocator));
    }

    public ComposeNewPage typeTo(String to) {
        driver.findElement(toLocator).sendKeys(to);
        return this;
    }

    public ComposeNewPage typeSubject(String subject) {
        driver.findElement(subjectLocator).sendKeys(subject);
        return this;
    }

    public ComposeNewPage typeText(String text) {
        driver.switchTo().frame(driver.findElement(textIFrameLocator));
        driver.findElement(textLocator).sendKeys(text);
        driver.switchTo().defaultContent();
        return this;
    }

    public SendMsgOkPage sendButtonClick() {
        driver.findElement(sendButtonLocator).click();
        return new SendMsgOkPage(driver);
    }

    private void waitLoading() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.className("b-prefoot")));
    }
}
