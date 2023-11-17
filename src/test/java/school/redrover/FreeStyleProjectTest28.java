package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import school.redrover.runner.BaseTest;

public class FreeStyleProjectTest28 extends BaseTest {


    @Test
    public void testCreate() throws InterruptedException {
        //Create a freestyle project with valid name
        //See created project on a Dashboard
        //Can go to created project configuration page

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys("FreeStyleProject28");
        getDriver().findElement(By.xpath("//span[@class='label']")).click();
        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--primary jenkins-buttons-row--equal-width']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();
        getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).click();

        String NewMessage = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText();



       Assert.assertEquals(NewMessage ,"Projekt FreeStyleProject28");





}
}