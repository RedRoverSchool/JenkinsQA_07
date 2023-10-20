package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class VladTest extends BaseTest {

    private void deleteText(WebElement deleteText){

        deleteText.sendKeys(Keys.CONTROL + "a");
        deleteText.sendKeys(Keys.DELETE);
    }
    @Test
    public void testJenkinsVersion() {

        getDriver().findElement(By.xpath("//button[contains(@class,'jenkins_ver')]")).click();
        getDriver().findElement(By.xpath("//a[@href=\"/manage/about\"]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//p[.=\"Version 2.414.2\"]")).getText(),
                "Version 2.414.2");
    }
    @Test
    public void testJenkinsManage() {

        getDriver().findElement(By.xpath("//a[@href=\"/asynchPeople/\"]")).click();
        getDriver().findElement(By.xpath("//a[@class=\"jenkins-table__link\"]")).click();
        getDriver().findElement(By.id("description-link")).click();

        WebElement setText = getDriver().findElement(By.xpath("//textarea[@name=\"description\"]"));
        deleteText(setText);
        setText.sendKeys("test123");

        getDriver().findElement(By.xpath("//button[@formnovalidate]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id=\"description\"]/div")).getText(), "test123");
    }
}
