package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupUnderdogsTest {

    @Test
    public void tereshenkov99BottlesTitleTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement title = driver.findElement(By.xpath("//*[@id=\"header\"]/h1"));
        String titleValue = title.getText();

        Assert.assertEquals(titleValue, "99 Bottles of Beer");

        driver.quit();
    }

    @Test
    public void tereshenkov99BottlesLastMenuLinkTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.99-bottles-of-beer.net/");

        WebElement lastMenuLink = driver.findElement(By.xpath("//*[@id=\"menu\"]/li[6]/a"));

//        if you want to compare the value inside the tag regardless of the case - use this code
        String lastMenuLinkValue = lastMenuLink.getAttribute("textContent");
        Assert.assertEquals(lastMenuLinkValue, "Submit new Language");

//        if you want to check the value that is displayed on the site (all CAPS) - use this code
//         String lastMenuLinkValue = lastMenuLink.getText();
//         Assert.assertEquals(lastMenuLinkValue, "SUBMIT NEW LANGUAGE");

        driver.quit();
    }

}
