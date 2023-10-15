package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;

public class FokichevTest extends BaseTest {

    private static final String PAGE_URL = "https://www.emu-land.net/";
    @Test
    public void searchTest() throws InterruptedException {
        getDriver().get(PAGE_URL);

        WebElement textBox = getDriver().findElement(By.name("q"));
        textBox.sendKeys("tekken");

        Thread.sleep(1000);

        WebElement submitButton = getDriver().findElement(By.xpath("//div[@class='gmenu']//button"));
        submitButton.click();

        Thread.sleep(1000);

        WebElement textBox2 = getDriver().findElement(By.xpath("//div[@class='fcontainer'][1]//p/a"));
        String value = textBox2.getText();
        assertEquals(value, "Tekken Card Challenge");
    }

    @Test
    public void selectConsolesTest() {
        getDriver().get(PAGE_URL);

        WebElement consolesButton = getDriver().findElement(By.xpath("//li/a[@href='/consoles']"));
        consolesButton.click();

        WebElement textBox = getDriver().findElement(By.xpath("//div[@class='path']"));
        String value = textBox.getText();
        assertEquals(value, "Консоли");
    }


}
