package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Pipeline3Test extends BaseTest {
    @Test
    public void testCreatePipeline() {
        final String pipelineName = "newPipelineName";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/parent::a")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//img[@src = '/plugin/workflow-job/images/pipelinejob.svg']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String xpathOpenProject = "//span[contains(text(), '%s')]/parent::a".formatted(pipelineName);

        getDriver().findElement(By.xpath(xpathOpenProject)).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Pipeline " + pipelineName
        );
    }

    @Test
    public void testPipelineEmptyNameHandling() {

        final String errorTextExpected = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div[@id ='j-add-item-type-standalone-projects']//span[contains(text(), 'Pipeline')]")).click();

        WebElement error = getDriver().findElement(By.id("itemname-required"));
        String errorTextActual =  error.getText();
        String errorTextColor = error.getCssValue("color");

        Assert.assertEquals(errorTextActual, errorTextExpected);
        Assert.assertEquals(errorTextColor, "rgba(255, 0, 0, 1)");
        Assert.assertEquals(getDriver().findElement(By.id("ok-button")).getAttribute("disabled"), "true");
    }

    @Test
    public void testPipelineDeleteProject() {

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("newPipelineName");
        getDriver().findElement(By.xpath("//span[contains(text(), 'Pipeline')]")).click();

        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//span[contains(text(), 'newPipelineName')]")).click();
        getDriver().findElement(By.xpath("//span[contains(text(), 'Delete Pipeline')]")).click();

        getDriver().switchTo().alert().accept();

        String actualText = getDriver().findElement(By.xpath("//h1[contains(text(), 'Welcome to Jenkins!')]")).getText();

        Assert.assertEquals(
                actualText,
                "Welcome to Jenkins!",
                "Pipeline not deleted");
    }
}
