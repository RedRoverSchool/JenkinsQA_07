package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.sql.DriverManager.getDriver;

public class FreestyleProject1Test extends BaseTest {
    private final static String PROJECT_NAME = "FreestyleProject";
    private final static String HOME_PAGE = "jenkins-home-link";
    private final static String NAME_SEARCH = "//span[text()='FreestyleProject']";
    @Test
    public void testCreateFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        String actualName = getDriver()
                .findElement(By.xpath(NAME_SEARCH)).getText();
        Assert.assertEquals(actualName, PROJECT_NAME);
    }

    @Test
    public void testDeleteFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("(//span[@class='label'])[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        getDriver().findElement(By.id(HOME_PAGE)).click();
        getDriver().findElement(By.xpath(NAME_SEARCH)).click();
        getDriver().findElement(By.xpath("//span[text()='Delete Project']")).click();
        getDriver().switchTo().alert().accept();
    }
}
