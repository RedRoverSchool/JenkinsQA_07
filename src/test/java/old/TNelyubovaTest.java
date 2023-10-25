package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TNelyubovaTest {

    @Test
    public void testSila() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://sila.by/");
        driver.manage().window().maximize();

        WebElement textBoxNew = driver.findElement(By.xpath("/html/body/div[8]/div/div[3]/form/input[@class='ui-autocomplete-input' and @value='Поиск товаров']"));
        textBoxNew.sendKeys("SAMSUNG GALAXY A12 3GB/32GB (ЧЕРНЫЙ)");

        WebElement searchButton = driver.findElement(By.xpath("/html/body/div[8]/div/div[3]/form/input[@type='submit' and @value='Найти']"));
        searchButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement titleNew = driver.findElement(By.xpath("//b[contains(text(), \"Samsung galaxy a12 3gb/32gb (черный)\")]//parent::h1"));
        String valueNew = titleNew.getText();
        Assert.assertEquals(valueNew, "Результаты поиска по запросу: Samsung galaxy a12 3gb/32gb (черный)");

        WebElement chosePhone = driver.findElement(By.xpath("//a[@href='https://sila.by/catalog/mobilnye_telefony/SAMSUNG/galaxy_a12_3gb32gb_chernyj']"));
        chosePhone.click();

        WebElement cashButton = driver.findElement(By.className("btn_zak"));
        cashButton.click();

        WebElement orderButton = driver.findElement(By.className("btn_zak_popup"));
        orderButton.click();

        WebElement orderTitle = driver.findElement(By.className("zakaz"));
        String value = orderTitle.getText();
        Assert.assertEquals(value, "Оформление заказа");

        driver.quit();

    }

}
