package old;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class LifantsovaTest extends BaseTest {

    @Test
    public void testJenkins() {

        Assert.assertEquals(getDriver().findElement(By.id("description-link")).getText(),
                "Add description");
    }

    @Test
    public void testCreateNewProject() {

        getDriver().findElement(By.xpath("//a [@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input [@name='name']")).sendKeys("NewJenkinsProject");
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText(),
                "Project NewJenkinsProject");
    }
}
