package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

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
}
