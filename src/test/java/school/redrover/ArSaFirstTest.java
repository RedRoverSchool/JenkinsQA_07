package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ArSaFirstTest extends BaseTest {

    private final static String folderName = "Artur Sabanadze";
    private final static String expectedVersion = "Jenkins 2.414.2";

    @Test
    public void testCreateFolder() {

        final String actualVersion = getDriver().findElement(By.className("jenkins_ver")).getText();
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item a.model-link")).click();
        getDriver().findElement(By.linkText(folderName)).click();

        getDriver().findElement(By.linkText("Configure")).click();
        getDriver().findElement(By.name("_.description")).sendKeys("Organization File of Artur Sabanadze. Student of Redrover School (7)");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertNotNull(getDriver().findElement(By.id("job_Artur Sabanadze")), "Artur Sabanadze folder not found on the Jenkins home page");
        Assert.assertEquals(actualVersion, expectedVersion, "Jenkins version mismatch");

    }
}