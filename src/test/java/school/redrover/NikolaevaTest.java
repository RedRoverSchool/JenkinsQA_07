package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NikolaevaTest extends BaseTest {
    @Test
    public void  testSearch() throws InterruptedException {


        getDriver().get("https://www.w3schools.com/");

        WebElement textBox = getDriver().findElement(By.xpath("//input[@id='search2']"));
        Thread.sleep(500);
        textBox.sendKeys("SQL Tutorial");

        WebElement searchButton = getDriver().findElement(By.xpath("//button[@id='learntocode_searchbtn']"));
        Thread.sleep(500);
        searchButton.click();

        WebElement title = getDriver().findElement(By.cssSelector("h1"));
        String value = title.getText();
        Assert.assertEquals(value, "SQL Tutorial");
    }




}
