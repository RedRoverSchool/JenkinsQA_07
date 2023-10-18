package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Ignore
public class BezverkhovaTest extends BaseTest {
    public static final String EMAIL = "test_redrov@yahoo.com";
    public static final String PASS = "te5t_redr0v";
    public static final String URL = "https://magento.softwaretestingboard.com/";
    public static final By WELCOME_MESSAGE_LOCATOR = By.xpath("//div[@class='panel header']//li[@class='greet welcome']/span");
    public static final By FORGET_PASS_LOCATOR = By.cssSelector("#login-form > fieldset > div.actions-toolbar > div.secondary > a > span");
    public static final By RESET_PASS_MESSAGE_LOCATOR = By.cssSelector("#maincontent > div.page.messages > div:nth-child(2) > div > div");
    public static final String ANYITEM = "#maincontent > div.columns > div.column.main > div.products.wrapper.grid.products-grid > ol > li:nth-child(1) > div > div > strong > a";
    public static final String SIZE = "#option-label-size-143-item-168";
    public static final String COLOR = "option-label-color-93-item-56";
    public static final String SALE4FOR3 = "#maincontent > div.columns > div > div.widget.block.block-static-block > div.blocks-promo > div > a.block-promo.home-t-shirts > span.content > span.info";
    public static final String ADDED_MESSAGE = "#maincontent > div.page.messages > div:nth-child(2) > div > div > div";
    public static final String DISCOUNT = "#cart-totals > div > table > tbody > tr:nth-child(2) > td > span > span";
    public static final String PRICE = "#shopping-cart-table > tbody > tr.item-info > td.col.price > span > span > span";
    public static final String DISCOUNT_TITLE = "#cart-totals > div > table > tbody > tr:nth-child(2) > th > span.title";

    @Test
    public void testLogIn() {
        getDriver().get(URL);

        getDriver().findElement(By.className("authorization-link")).click();
        getDriver().findElement(By.name("login[username]")).sendKeys(EMAIL);
        getDriver().findElement(By.name("login[password]")).sendKeys(PASS);
        getDriver().findElement(By.cssSelector("#send2 > span")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        String expectedWelcomeMessage = "Welcome, Test Redrov!";
        wait.until(ExpectedConditions.textToBePresentInElementLocated(WELCOME_MESSAGE_LOCATOR, expectedWelcomeMessage));
        String actualWelcomeMessage = getDriver().findElement(WELCOME_MESSAGE_LOCATOR).getText();
        assertEquals(actualWelcomeMessage, expectedWelcomeMessage);
    }

    @Test
    public void testForgetPass() {
        getDriver().get(URL);
        getDriver().findElement(By.className("authorization-link")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(FORGET_PASS_LOCATOR)).click();
        getDriver().findElement(By.name("email")).sendKeys(EMAIL);
        getDriver().findElement(By.cssSelector("#form-validate > div > div.primary > button > span")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("message-success")));
        String actualMessage = getDriver().findElement(RESET_PASS_MESSAGE_LOCATOR).getText();
        String expectedMessage = String.format("If there is an account associated with %1$s you will receive an email with a link to reset your password.", EMAIL );
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testCheck4TeesForPriceOf3() {
        getDriver().get(URL);

        WebElement saleBanner = getDriver().findElement(By.cssSelector(SALE4FOR3));
        if (saleBanner.isDisplayed()) {
            saleBanner.click();
            WebElement sellingItem = getDriver().findElement(By.cssSelector(ANYITEM));
            String sellingItemName = sellingItem.getText();
            sellingItem.click();

            getDriver().findElement(By.cssSelector(SIZE)).click();
            getDriver().findElement(By.id(COLOR)).click();
            WebElement qty = getDriver().findElement(By.name("qty"));
            qty.clear();
            qty.sendKeys("4");
            getDriver().findElement(By.id("product-addtocart-button")).click();

            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("message-success")));
            String actualAddedMessage = getDriver().findElement(By.cssSelector(ADDED_MESSAGE)).getText();
            String expectedAddedMessage = "You added " + sellingItemName + " to your shopping cart.";
            assertEquals(actualAddedMessage, expectedAddedMessage);

            getDriver().findElement(By.cssSelector(ADDED_MESSAGE + " > a")).click();
            if (getDriver().findElement(By.cssSelector(DISCOUNT_TITLE)).isDisplayed()) {
                String discount = getDriver().findElement(By.cssSelector(DISCOUNT)).getText();
                String price = getDriver().findElement(By.cssSelector(PRICE)).getText();
                assertEquals(discount, "-" + price, "Sale 4for3 doesn't works");
            } else {
                System.out.println("Sale 4for3 doesn't work");
            }
        } else {
            System.out.println("Sale finished");
        }
    }

    @Test
    public void testJenkinsStatus(){
        getDriver().findElement(By.className("model-link")).click();
        String iconText = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
        assertEquals(iconText, "admin");
        String statusIconText = getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText();
        assertEquals(statusIconText, "Jenkins User ID: admin");
    }
}
