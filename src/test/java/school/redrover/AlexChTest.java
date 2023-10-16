package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

public class AlexChTest extends BaseTest {

    public void createNewPipeline(String pipelineName){
        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
    
    @Test
    public void testJenkinsVersion(){
        JenkinsUtils.login(getDriver());

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'page-footer__links']/button"))
                .getText().trim(), "Jenkins 2.414.2");
    }

    @Test
    public void testCreatePipelineWithCorrectName(){
        String jobName = "correctName";
        JenkinsUtils.login(getDriver());

        createNewPipeline(jobName);
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#projectstatus tbody tr"))
                .getAttribute("id").contains("job_".concat(jobName)));
    }

    @Test
    public void testCreatePipelineWithEmptyName(){
        JenkinsUtils.login(getDriver());

        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineWithDuplicateName(){
        String jobName = "duplicateName";
        JenkinsUtils.login(getDriver());

        createNewPipeline(jobName);
        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'newJob')]")).click();
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[contains(@class, 'FreeStyleProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Error");
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'main-panel']/p")).getText(),
                "A job already exists with the name ‘" + jobName + "’");
    }
}
