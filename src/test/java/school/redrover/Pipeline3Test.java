package school.redrover;

import org.openqa.selenium.By;
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
}
