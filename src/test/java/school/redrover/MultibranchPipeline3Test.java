package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline3Test extends BaseTest {
    @Test
    public void testMultibranchPipelineCreation() {

        final var expectedPipelineName = "AutoPipelineName";

        WebElement newJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        newJobButton.click();
        WebElement nameFieldInput = getDriver().findElement(By.xpath("//input[@id='name']"));
        nameFieldInput.sendKeys(expectedPipelineName);
        WebElement multiBranchPipelineButton = getDriver().findElement(By.xpath("//span[contains(text(),'Multibranch Pipeline')]"));
        multiBranchPipelineButton.click();
        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        WebElement submitPipelineButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        submitPipelineButton.click();
        WebElement createdPipeLineName = getDriver().findElement(By.xpath("//a[contains(text(),'" + expectedPipelineName + "')]"));
        String actualPipelineName = createdPipeLineName.getText();

        Assert.assertEquals(actualPipelineName, expectedPipelineName);

    }
}
