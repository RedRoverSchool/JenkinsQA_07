package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

@Ignore
public class BezverkhovaTest {
    public static final String ANYITEM = "#maincontent > div.columns > div.column.main > div.products.wrapper.grid.products-grid > ol > li:nth-child(1) > div > div > strong > a";
    public static final String SIZE = "#option-label-size-143-item-168";
    public static final String COLOR = "option-label-color-93-item-56";
    public static final String SALE4FOR3 = "#maincontent > div.columns > div > div.widget.block.block-static-block > div.blocks-promo > div > a.block-promo.home-t-shirts > span.content > span.info";
    public static final String ADDED_MESSAGE = "#maincontent > div.page.messages > div:nth-child(2) > div > div > div";
    public static final String DISCOUNT = "#cart-totals > div > table > tbody > tr:nth-child(2) > td > span > span";
    public static final String PRICE = "#shopping-cart-table > tbody > tr.item-info > td.col.price > span > span > span";
    public static final String DISCOUNT_TITLE = "#cart-totals > div > table > tbody > tr:nth-child(2) > th > span.title";
    public static final String EMAIL = "test_redrov@yahoo.com";
    public static final String PASS = "te5t_redr0v";
    public static final String URL = "https://magento.softwaretestingboard.com/";

    @Test
    public void testLogIn() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(URL);

        WebElement myaccount = driver.findElement(By.className("authorization-link"));
        myaccount.click();
        WebElement enterEmail = driver.findElement(By.name("login[username]"));
        enterEmail.sendKeys(EMAIL);
        WebElement enterPass = driver.findElement(By.name("login[password]"));
        enterPass.sendKeys(PASS);
        WebElement submitButton = driver.findElement(By.cssSelector("#send2 > span"));
        submitButton.click();
        Thread.sleep(2000);
        String greeting = driver.findElement(By.cssSelector("body > div.page-wrapper > header > div.panel.wrapper > div > ul > li.greet.welcome > span")).getText();
        assertEquals(greeting, "Welcome, Test Redrov!");
        driver.quit();
    }

    @Test
    public void forgetPass() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(URL);
        WebElement myAccount = driver.findElement(By.className("authorization-link"));
        myAccount.click();
        WebElement forgetPass = driver.findElement(By.cssSelector("#login-form > fieldset > div.actions-toolbar > div.secondary > a > span"));
        forgetPass.click();
        WebElement enterEmail = driver.findElement(By.name("email"));
        enterEmail.sendKeys(EMAIL);
        WebElement submitButton = driver.findElement(By.cssSelector("#form-validate > div > div.primary > button > span"));
        submitButton.click();
        Thread.sleep(2000);
        String greeting = driver.findElement(By.cssSelector("#maincontent > div.page.messages > div:nth-child(2) > div > div")).getText();
        assertEquals(greeting, "If there is an account associated with test_redrov@yahoo.com you " +
                "will receive an email with a link to reset your password.");
        driver.quit();
    }

    @Test
    public void check4TeesForPriceOf3() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(URL);

        WebElement saleBanner = driver.findElement(By.cssSelector(SALE4FOR3));
        if (saleBanner.isDisplayed()) {
            saleBanner.click();
            WebElement sellingItem = driver.findElement(By.cssSelector(ANYITEM));
            String sellingItemName = sellingItem.getText();
            sellingItem.click();

            driver.findElement(By.cssSelector(SIZE)).click();
            driver.findElement(By.id(COLOR)).click();
            WebElement qty = driver.findElement(By.name("qty"));
            qty.clear();
            qty.sendKeys("4");
            driver.findElement(By.id("product-addtocart-button")).click();

            String greeting = driver.findElement(By.cssSelector(ADDED_MESSAGE)).getText();
            assertEquals(greeting, "You added " + sellingItemName + " to your shopping cart.");
            driver.findElement(By.cssSelector(ADDED_MESSAGE + " > a")).click();

            if (driver.findElement(By.cssSelector(DISCOUNT_TITLE)).isDisplayed()) {
                String discount = driver.findElement(By.cssSelector(DISCOUNT)).getText();
                String price = driver.findElement(By.cssSelector(PRICE)).getText();
                assertEquals(discount, "-" + price, "Sale 4for3 doesn't works");
            } else {
                System.out.println("Sale 4for3 doesn't work");
            }
        } else {
            System.out.println("Sale finished");
        }
        driver.quit();
    }
}
