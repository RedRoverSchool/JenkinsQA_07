package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Ignore
public class FokichevTest {
    @Test
    public void searchTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.emu-land.net/");

        WebElement textBox = driver.findElement(By.name("q"));
        textBox.sendKeys("tekken");

        Thread.sleep(1000);

        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"row_right\"]/div[4]/div/form/div[2]/button"));
        submitButton.click();

        Thread.sleep(1000);

        WebElement textBox2 = driver.findElement(By.xpath("//*[@id=\"row_center\"]/div[3]/div[2]/p/a"));
        String value = textBox2.getText();
        assertEquals(value, "Tekken Card Challenge");

        driver.quit();
    }

    @Test
    public void selectConsolesTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.emu-land.net/");

        WebElement consolesButton = driver.findElement(By.xpath("//a[@href=\"/consoles\"]"));
        consolesButton.click();;

        WebElement textBox = driver.findElement(By.xpath("//div[@class=\"path\"]"));
        String value = textBox.getText();
        assertEquals(value, "Консоли");

        driver.quit();
    }


}
