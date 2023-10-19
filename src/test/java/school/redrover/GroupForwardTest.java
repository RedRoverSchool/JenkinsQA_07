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


public class GroupForwardTest extends BaseTest {

    @Ignore
    @Test
    public void test_URL_WhenClickingOnMyViewsButton() {

        String expectedResult = "http://localhost:8080/me/my-views/view/all/";

        WebElement myViewsButton = getDriver().findElement(By.xpath(
                "//a[@href='/me/my-views']"));

        myViewsButton.click();

        String actualResult = getDriver().getCurrentUrl();

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testClickLogoToMainPage() {

        WebElement myViewsButton = getDriver().findElement(By.xpath(
                "//a[@href='/me/my-views']"));
        myViewsButton.click();

        WebElement logoJenkins = getDriver().findElement(By.id("jenkins-head-icon"));
        logoJenkins.click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testAddDescription() {

        String addDescription = "GroupForward #1";

        WebElement description = getDriver().findElement(By.id("description-link"));
        description.click();
        WebElement textField = getDriver().findElement(By.name("description"));
        textField.clear();
        textField.sendKeys(addDescription);
        getDriver().findElement(By.name("Submit")).click();
        String newDescriprtion = getDriver().findElement(By.id("description")).getText();

        Assert.assertTrue(newDescriprtion.contains(addDescription));

    }

    @Test
    public void testHomeDirectory() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        WebElement directory = getDriver().findElement(By.xpath("//div[@class= 'jenkins-quote jenkins-quote--monospace']"));
        String innerText = directory.getAttribute("innerText");
        WebElement copyText = getDriver().findElement(By.xpath("//button[@tooltip = 'Copy home directory']"));
        String copyInnerText = copyText.getAttribute("text");

        Assert.assertEquals(innerText, copyInnerText);

    }
}



