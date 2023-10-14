package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import static org.testng.Assert.assertEquals;

public class GroupBrainBuildersTest extends BaseTest {

    @Test
    public void testAskentLogIn() throws InterruptedException {
        getDriver().get("https://www.askent.ru/cabinet/order/?show_all=Y");

        String title = getDriver().getTitle();
        assertEquals(title, "ASKENT - российский бренд аксессуаров из натуральной кожи");

        WebElement emailField = getDriver().findElement(By.xpath("//form[@id='loginform']/div[3]/div/input"));
        emailField.click();
        emailField.sendKeys("testaccaskenttest@gmail.com");

        WebElement passwordField = getDriver().findElement(By.xpath("//form[@id='loginform']/div[3]/div[2]/input"));
        passwordField.click();
        passwordField.sendKeys("testpasswordaskent123!");

        WebElement submitButtonLogIn = getDriver().findElement(By.xpath("//form[@id='loginform']/a"));
        submitButtonLogIn.click();
        Thread.sleep(1000);

        WebElement personalAccountTitle = getDriver().findElement(By.cssSelector("h1"));
        String resultTitle = personalAccountTitle.getText();
        Assert.assertEquals(resultTitle, "Личный кабинет");
    }

    @Test
    public void testAskentAddToCart() throws InterruptedException {
        getDriver().get("https://www.askent.ru/cat/bumazhniki/portmone_308/");

        String title = getDriver().getTitle();
        assertEquals(title, "МИНИ ПОРТМОНЕ MODULE , пол: Женский, цвет: breen, размер: 100х080х035. Купить в интернет-магазине ASKENT. Цена 4 490 руб.");

        if (getDriver().findElement(By.xpath("//*[@id = 'cookie_accept']")).isDisplayed()) {
            WebElement cookieButton = getDriver().findElement(By.xpath("//*[@id = 'cookie_accept']"));
            cookieButton.click();
        }

        WebElement addToCartButton = getDriver().findElement(By.xpath("//*[@class = 'optionsBlock__add add-cart-statistics']"));
        addToCartButton.click();

        WebElement cartIcon = getDriver().findElement(By.xpath("//*[@class = 'cart_icon']"));
        cartIcon.click();
        Thread.sleep(2000);

        WebElement itemName = getDriver().findElement(By.xpath("//*[@href = '/cat/bumazhniki/portmone_308/']"));
        String resultName = itemName.getText();
        Assert.assertEquals(resultName, "МИНИ ПОРТМОНЕ MODULE");
    }

    @Test
    public void testCreatingDoubleRoom() throws InterruptedException {

        getDriver().get("https://automationintesting.online");
        getDriver().findElement(By.linkText("admin panel")).click();

        if (getDriver().findElement(By.id("username")).isDisplayed()) {
            WebElement inputName = getDriver().findElement(By.id("username"));
            WebElement inputPassword = getDriver().findElement(By.id("password"));
                inputName.click();
                inputName.sendKeys("admin");
                inputPassword.click();
                inputPassword.sendKeys("password");
                getDriver().findElement(By.id("doLogin")).click();
        }

        Thread.sleep(2000);
        getDriver().findElement(By.id("roomName")).click();
        getDriver().findElement(By.id("roomName")).sendKeys("111");
        getDriver().findElement(By.id("type")).click();
        {
            WebElement dropdown = getDriver().findElement(By.id("type"));
            dropdown.findElement(By.xpath("//option[. = 'Double']")).click();
        }
        getDriver().findElement(By.id("accessible")).click();
        {
            WebElement dropdown = getDriver().findElement(By.id("accessible"));
            dropdown.findElement(By.xpath("//option[. = 'true']")).click();
        }

        getDriver().findElement(By.id("roomPrice")).click();
        getDriver().findElement(By.id("roomPrice")).sendKeys("150");
        getDriver().findElement(By.id("wifiCheckbox")).click();
        getDriver().findElement(By.id("refreshCheckbox")).click();
        getDriver().findElement(By.id("tvCheckbox")).click();
        getDriver().findElement(By.id("safeCheckbox")).click();
        getDriver().findElement(By.id("createRoom")).click();
        getDriver().findElement(By.linkText("home page")).click();

        Thread.sleep(2000);
        Assert.assertTrue(getDriver().getPageSource().contains("Double"));
    }

    @Test
    public void testJenkinsAdminStatus() throws InterruptedException {

        JenkinsUtils.login(getDriver());

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(2) > span > a")).click();
        // From the list of users I would like to get name of the particular user and click on it
        WebElement recordInTheList = getDriver().findElement(By.className("jenkins-table__link"));
        String userName = recordInTheList.getText();
        recordInTheList.click();
        // And to verify that on the next page userID match with the name
        Assert.assertTrue(getDriver().getPageSource().contains(userName));
    }

}
