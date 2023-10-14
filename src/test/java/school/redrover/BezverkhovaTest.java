package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import static org.testng.Assert.assertEquals;

@Ignore
public class BezverkhovaTest extends BaseTest {
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
        getDriver().get(URL);

        WebElement myaccount = getDriver().findElement(By.className("authorization-link"));
        myaccount.click();
        WebElement enterEmail = getDriver().findElement(By.name("login[username]"));
        enterEmail.sendKeys(EMAIL);
        WebElement enterPass = getDriver().findElement(By.name("login[password]"));
        enterPass.sendKeys(PASS);
        WebElement submitButton = getDriver().findElement(By.cssSelector("#send2 > span"));
        submitButton.click();
        Thread.sleep(2000);
        String greeting = getDriver().findElement(By.cssSelector("body > div.page-wrapper > header > div.panel.wrapper > div > ul > li.greet.welcome > span")).getText();
        assertEquals(greeting, "Welcome, Test Redrov!");
        getDriver().quit();
    }

    @Test
    public void testForgetPass() throws InterruptedException {
        getDriver().get(URL);
        WebElement myAccount = getDriver().findElement(By.className("authorization-link"));
        myAccount.click();
        WebElement forgetPass = getDriver().findElement(By.cssSelector("#login-form > fieldset > div.actions-toolbar > div.secondary > a > span"));
        forgetPass.click();
        WebElement enterEmail = getDriver().findElement(By.name("email"));
        enterEmail.sendKeys(EMAIL);
        WebElement submitButton = getDriver().findElement(By.cssSelector("#form-validate > div > div.primary > button > span"));
        submitButton.click();
        Thread.sleep(2000);
        String greeting = getDriver().findElement(By.cssSelector("#maincontent > div.page.messages > div:nth-child(2) > div > div")).getText();
        assertEquals(greeting, "If there is an account associated with test_redrov@yahoo.com you " +
                "will receive an email with a link to reset your password.");
        getDriver().quit();
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

            String greeting = getDriver().findElement(By.cssSelector(ADDED_MESSAGE)).getText();
            assertEquals(greeting, "You added " + sellingItemName + " to your shopping cart.");
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
        getDriver().quit();
    }

    @Test
    public void testJenkinsStatus(){
        JenkinsUtils.login(getDriver());
        getDriver().findElement(By.className("model-link")).click();
        String iconText = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
        assertEquals(iconText, "admin");
        String statusIconText = getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText();
        assertEquals(statusIconText, "Jenkins User ID: admin");
    }
}
