package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


public class GroupTestscriptCollaboratoriumTest extends BaseTest {

    @Ignore
    @Test
    public void testGetGuru() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.guru99.com/");

        String title = driver.getTitle();
        Assert.assertEquals("Meet Guru99 – Free Training Tutorials & Video for IT Courses", title);

        WebElement JUnitButton = driver.findElement(By.xpath("//*[@data-lasso-id='147439']"));
        JUnitButton.click();

        Thread.sleep(900);

        WebElement textButton = driver.findElement(By.xpath("//*[@id='post-862']/div/div/h2[2]"));
        Assert.assertEquals(textButton.getText(), "JUnit Tutorial Syllabus");

        driver.quit();
    }

    @Test
    public void testSubscription() {

        getDriver().get("https://murzilka.org/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Журнал \"Мурзилка\"");

        WebElement textButton = getDriver().findElement(By.xpath("//*[@class='mrb-btn-item-text']"));
        String valueButton = textButton.getText();
        Assert.assertEquals(valueButton, "Подписаться на журнал");

        textButton.click();
        WebElement message = getDriver().findElement(By.xpath("//h1[@class='category-name']"));
        String valueH1 = message.getText();
        Assert.assertEquals(valueH1, "РЕДАКЦИОННАЯ ПОДПИСКА");
    }

    @Test
    public void testAddToBasket() throws InterruptedException {

        getDriver().get("https://murzilka.org/products/category/redaktsionnaya-podpiska");
        WebElement addButton = getDriver().findElement(By.xpath("//button[@class='button product-item__button button_for_product-card cart-btn js-order-product js-cart-btn']"));
        addButton.click();
        Thread.sleep(1000);

        WebElement inBasket = getDriver().findElement(By.xpath("//*[@class='quantity-items top-cart__quantity']"));
        String valueBasket = inBasket.getText();
        Assert.assertEquals(valueBasket, "1");
    }

    @Test
    public void testSearch() {

        JenkinsUtils.login(getDriver());
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testVersion() {

        JenkinsUtils.login(getDriver());

        WebElement buttonVersion = getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"));
        buttonVersion.click();

        WebElement buttonVersionNext = getDriver().findElement(By.xpath("//a[@href='/manage/about']"));
        buttonVersionNext.click();

        WebElement version = getDriver().findElement(By.xpath("//p[@class='app-about-version']"));
        String valueVersion = version.getText();
        Assert.assertEquals(valueVersion, "Version 2.414.2");
    }

    @Test
    public void testEssayAppGetSite() {

        getDriver().get("https://essay.app/");

        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Essay - Write better.");
    }

    @Test
    public void testEssayAppSwitchWebsiteColorLayout() {

        getDriver().get("https://essay.app/");

        WebElement upperSitePanel = getDriver().findElement(By.className("css-s5xdrg"));

        WebElement colorLayoutSwitcher = upperSitePanel.findElement(By.tagName("button"));
        String colorLayoutSwitcherCurrentState = colorLayoutSwitcher.getAttribute("aria-checked");

        Assert.assertEquals(colorLayoutSwitcherCurrentState, "false",
                "Current layout is Light");

        WebElement colorLayoutSwitcherHandle = colorLayoutSwitcher.findElement(By.className("ant-switch-handle"));
        colorLayoutSwitcherHandle.click();

        colorLayoutSwitcherCurrentState = colorLayoutSwitcher.getAttribute("aria-checked");
        Assert.assertEquals(colorLayoutSwitcherCurrentState, "true",
                "Current layout is Dark");
    }
}
