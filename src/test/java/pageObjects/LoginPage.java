package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private By usernameLocator = By.id("mailbox__login");
    private By passwordLocator = By.id("mailbox__password");
    private By loginButtonLocator = By.id("mailbox__auth__button");
    private By rememberCheckboxLocator = By.id("mailbox__auth__remember__checkbox");
    private By footerLocator = By.className("portal-footer");

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions
                        .presenceOfElementLocated(footerLocator));
    }

    public LoginPage typeUsername(String username) {
        driver.findElement(usernameLocator).sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        driver.findElement(passwordLocator).sendKeys(password);
        return this;
    }

    public InboxPage submitLogin() {
        driver.findElement(loginButtonLocator).submit();
        return new InboxPage(driver);
    }

    public LoginPage uncheckRememberCheckbox() {
        WebElement checkbox = driver.findElement(rememberCheckboxLocator);
        if (checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }
}
