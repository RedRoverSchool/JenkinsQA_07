package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DisableMultibranchPipelineTest extends BaseTest {

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
    public void testDisableMultibranchPipeline() throws InterruptedException {
        createMultibranchPipeline("Test_Folder");

        getDriver().findElement(By.xpath("//span[@id='toggle-switch-enable-disable-project']/label")).click();
        Thread.sleep(2000);

        WebElement elementPage = getDriver().findElement(By.xpath("//span[@id='toggle-switch-enable-disable-project']/label/span[text()='Disabled']"));
        String nameToggle = elementPage.getText();

        String expectedResult = "Disabled";

        Assert.assertEquals(nameToggle, expectedResult);
    }
}