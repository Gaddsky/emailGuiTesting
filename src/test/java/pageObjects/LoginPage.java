package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Representation of Login page, used in tests
 */
public class LoginPage {
    /** Locator for the username input field */
    private By usernameLocator = By.id("mailbox__login");
    /** Locator for the password input field */
    private By passwordLocator = By.id("mailbox__password");
    /** Locator for the login button */
    private By loginButtonLocator = By.id("mailbox__auth__button");
    /** Locator for the 'Remember' checkbox */
    private By rememberCheckboxLocator = By.id("mailbox__auth__remember__checkbox");
    /** Locator for the page's footer. It used to check if the page is loaded */
    private By footerLocator = By.className("portal-footer");

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // waiting for page is loaded
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(footerLocator));
    }

    /**
     * Enter username into username input field
     * @param username
     * @return this
     */
    public LoginPage typeUsername(String username) {
        driver.findElement(usernameLocator).sendKeys(username);
        return this;
    }

    /**
     * Enter password into password input field
     * @param password
     * @return this
     */
    public LoginPage typePassword(String password) {
        driver.findElement(passwordLocator).sendKeys(password);
        return this;
    }

    /**
     * Click on 'Login' button
     * @return this
     */
    public InboxPage submitLogin() {
        driver.findElement(loginButtonLocator).submit();
        return new InboxPage(driver);
    }

    /**
     * Uncheck 'Remember' checkbox, if it in 'check' state
     * @return this
     */
    public LoginPage uncheckRememberCheckbox() {
        WebElement checkbox = driver.findElement(rememberCheckboxLocator);
        if (checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }
}
