package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class MultibranchPipeline2Test extends BaseTest {

    private void returnToJenkinsHomePage() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();
    }

    private void createMultibranchPipeline(String name) {
        returnToJenkinsHomePage();

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @Test
    public void testDisableMultibranchPipeline() {
        createMultibranchPipeline("Test_Folder");
        String expectedResult = "Disabled";

        getDriver().findElement(By.xpath("//span[@id='toggle-switch-enable-disable-project']/label")).click();

        WebElement elementPage = getWait2().until(ExpectedConditions.visibilityOfElementLocated((By.xpath(
                "//span[@id='toggle-switch-enable-disable-project']/label/span[text()='Disabled']"))));
        String nameToggle = elementPage.getText();

        Assert.assertEquals(nameToggle, expectedResult);
    }
}
