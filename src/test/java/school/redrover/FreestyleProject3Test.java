package school.redrover;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertFalse;

public class FreestyleProject3Test extends BaseTest {


    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.xpath("//a[starts-with(@href,'/view/all/newJob') and contains (@class,'task-link')]")).click();
        getDriver().findElement(By.xpath("//*[starts-with(@name,'name') and contains(@id,'name')]")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testDeleteFreestyleProject() {
        final String projectName = "Starlight";
        createFreestyleProject(projectName);
        getDriver().findElement(By.xpath("//*[@class = 'jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.xpath("//a[@class='task-link  confirmation-link']")).click();
        getDriver().switchTo().alert().accept();
        boolean isElementPresent = isElementPresent(getDriver(), By.xpath("//a[starts-with(@href,'job/" + projectName + "/') and contains(@class,'jenkins-table__link')]"));

        Assert.assertFalse(isElementPresent);

    }
}