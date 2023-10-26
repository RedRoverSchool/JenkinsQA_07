package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject3Test extends BaseTest {

    @Test
    public void createFreestyleProject() {
        final String projectName = "Test Project";
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//tr[@id= 'job_" + projectName + "' ] //td[3]/a")).click();


        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                "Project " + projectName);







    }

}
