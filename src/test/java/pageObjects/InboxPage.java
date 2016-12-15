package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class InboxPage {
    private final WebDriver driver;
    private By newLetterButtonLocator = By.linkText("Написать письмо");
    private By lettersListCheckboxLocator = By.className("js-item-checkbox");
    private By LettersListLocator = By.className("b-datalist__item__subj");
    private By removeButtonLocator = By.cssSelector("div[data-name='remove'");
    private By loggedAccountNameLocator = By.id("PH_user-email");
    private By activeFolderLocator = By.className("b-nav__item_active");
    private By unreadLocator = By.className("b-datalist__item_unread");


    public InboxPage(WebDriver driver) {
        this.driver = driver;

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(activeFolderLocator));
    }

    public ComposeNewPage clickNewLetter() {
        driver.findElement(newLetterButtonLocator).click();
        return new ComposeNewPage(driver);
    }

    public InboxPage chooseFirstLetter() {
        List<WebElement> lettersCheckboxList = driver.findElements(lettersListCheckboxLocator);
        WebElement letterCheckBox = lettersCheckboxList.get(0);
        letterCheckBox.click();
        return this;
    }

    public String getFirstLetterHeader() {
        return driver.findElements(LettersListLocator).get(0).getText();
    }

    public int getLettersCount() {
        return driver.findElements(lettersListCheckboxLocator).size();
    }

    public InboxPage deleteFirstLetter() {
        driver.findElement(removeButtonLocator).click();
        return this;
    }

    public Boolean isInboxLetters() {
        return driver.findElements(lettersListCheckboxLocator).size() > 0;
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

    public String getActiveFolderName() {
        return driver.findElement(activeFolderLocator).getText();
    }

    public InboxPage waitForUnread() {
        if (!isInboxLetters()) driver.navigate().refresh();
        (new WebDriverWait(driver, 5))
                .until(ExpectedConditions
                        .presenceOfElementLocated(unreadLocator));
        return this;
    }

    public InboxPage waitForDelete() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//span[contains(@class, 'notify-message__title__text_ok') and contains (text(), 'Удалено 1 письмо')]")));
        return this;
    }
}
