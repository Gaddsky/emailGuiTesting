import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.ComposeNewPage;
import pageObjects.InboxPage;
import pageObjects.LoginPage;
import pageObjects.SendMsgOkPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RunWith(Parameterized.class)
public class EmailGuiTest {
    private WebDriver driver;
    private DriverBuilder driverB;
    private String domain = "mail.ru";
    private String username = System.getenv("MAILTESTLOGIN");
    private String password = System.getenv("MAILTESTPASSWORD");

    public interface DriverBuilder {
        WebDriver create();
    }

    public EmailGuiTest(DriverBuilder driverB) {
        this.driverB = driverB;
    }

    @Parameterized.Parameters
    public static List<DriverBuilder> getTestDrivers() {
        return new ArrayList<>(Arrays.asList(
                ChromeDriver::new,
                FirefoxDriver::new
        ));
    }

    @Before
    public void setUpWebDriver() {
        driver = driverB.create();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void loginTest() {
        InboxPage inboxPage = loginProcedure();

        Assert.assertTrue(inboxPage.getTitle().endsWith("Почта Mail.Ru"));
        Assert.assertTrue(inboxPage.getUrl().endsWith("e.mail.ru/messages/inbox/?back=1"));
        Assert.assertEquals(inboxPage.getLoggedAccountName(), username + "@" + domain);
        Assert.assertTrue(inboxPage.getActiveFolderName().contains("Входящие"));
    }

    @Test
    public void newLetter() {
        InboxPage inboxPage = loginProcedure();

        ComposeNewPage composeNew = inboxPage.clickNewLetter();
        composeNew.typeTo(username +"@" + domain);
        composeNew.typeSubject("Test subject");
        composeNew.typeText("This is test text\nThat is");
        SendMsgOkPage sendMsgOkPage = composeNew.sendButtonClick();

        Assert.assertTrue(sendMsgOkPage.getTitle().endsWith(" - Почта Mail.Ru"));
        Assert.assertTrue(sendMsgOkPage.getUrl().contains("e.mail.ru/sendmsgok?id="));
        Assert.assertEquals(sendMsgOkPage.getLoggedAccountName(), username + "@" + domain);
        Assert.assertEquals(sendMsgOkPage.getSentTitle(), "Ваше письмо отправлено. Перейти во Входящие");
    }


    @Test
    public void deleteFirstLetter() {
        InboxPage inboxPage = loginProcedure();

        if (!inboxPage.isInboxLetters()) {
            ComposeNewPage composeNew = inboxPage.clickNewLetter();
            composeNew.typeTo(username +"@" + domain);
            composeNew.typeSubject("For delete");
            composeNew.typeText("This letter will be delete\nThat is");
            inboxPage = composeNew.sendButtonClick().returnToInbox().waitForUnread();
        }
        int lettersCount = inboxPage.getLettersCount();
        inboxPage.chooseFirstLetter().deleteFirstLetter().waitForDelete();

        Assert.assertEquals(lettersCount - 1, inboxPage.getLettersCount());
    }

    private InboxPage loginProcedure() {
        driver.get("https://" + domain);
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.typeUsername(username)
                .typePassword(password)
                .uncheckRememberCheckbox()
                .submitLogin();
    }

    @After
    public void quit() {
        driver.quit();
    }
}
