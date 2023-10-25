package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationTest extends BaseTest {

    @Test
    public void testCreateWithValidName() {
        final String validName = "MyOrganization";

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob'")).click();
        getDriver().findElement(By.id("name")).sendKeys(validName);
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("td a[href='job/" + validName + "/'] span")).getText(),
                validName,
                "Created organization name is incorrect");
    }
}
