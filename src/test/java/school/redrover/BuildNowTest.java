package school.redrover;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class BuildNowTest extends BaseTest {

    final String pipelineName = "PipelineName";
    private void createPipeline() {

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/parent::a")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//img[@src = '/plugin/workflow-job/images/pipelinejob.svg']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        String xpathOpenProject = "//span[contains(text(), '%s')]/parent::a".formatted(pipelineName);
        getDriver().findElement(By.xpath(xpathOpenProject)).click();
    }

    @Test
    public void testBuildNew() throws InterruptedException {

        createPipeline();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        String xpathOpenProject = "//span[contains(text(), '%s')]/parent::a".formatted(pipelineName);
        getDriver().findElement(By.xpath(xpathOpenProject)).click();

        getDriver().findElement(By.xpath("//span[contains(text(), 'Build Now')]/parent::a")).click();

        Thread.sleep(8000);
        String buildNumber = getDriver().findElement(By.className("pane-content")).getText();

        Assert.assertTrue(buildNumber.contains("#1"));
    }
}
