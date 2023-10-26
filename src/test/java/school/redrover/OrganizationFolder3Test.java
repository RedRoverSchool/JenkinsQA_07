package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder3Test extends BaseTest {

    private static final String PROJECT_NAME = "Organization Folder";

    private void createProject() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    @Test
    public void testCreateProject() {

        createProject();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(),"Organization Folder");
    }
}
