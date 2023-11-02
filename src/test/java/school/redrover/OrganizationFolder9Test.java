package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder9Test extends BaseTest {

    private static final String NAME_OF_ORGANIZATION = "QAQAQA";

    private void createOrganization() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(NAME_OF_ORGANIZATION);
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

    }

    @Test
    public void testCreateOrganizationFolder() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();

        String message = getDriver().findElement(By.id("itemname-required")).getText();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(NAME_OF_ORGANIZATION + "@");
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        String error = getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1")).getText().trim();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        createOrganization();

        String nameOrganizationOnDashBoard = getDriver()
                .findElement(By.xpath("//a[@href = 'job/" + NAME_OF_ORGANIZATION + "/']"))
                .getText();

        Assert.assertEquals(message, "» This field cannot be empty, please enter a valid name");
        Assert.assertEquals(error, "Error");
        Assert.assertEquals(nameOrganizationOnDashBoard, NAME_OF_ORGANIZATION);

    }

    @Test
    public void testCreateOrganizationFolderWithValidName () {
        final String OrganizationFolderName = "NewOrganizationFolder555";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(OrganizationFolderName);
        getDriver().findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@id='job_" + OrganizationFolderName + "']//td[3]")).getText(),
                OrganizationFolderName);
    }
}
