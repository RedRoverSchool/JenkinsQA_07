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
    public void testCreateOrganizationFolderWithValidName() {
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

    final String folderName = "OrganizationFolder11";
    final String renamedFolder = "NewOrganizationFolder55";
    private void createFolder () {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//li[@class='jenkins_branch_OrganizationFolder']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.linkText("Dashboard")).click();
    }

    @Test
    public void testCreateOrganizationFolderWithValidNameNew () {
        createFolder();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//tr[@id='job_" + folderName + "']//td[3]")).getText(),
                folderName);
    }

    @Test
    public void testRenameOrganizationFolderNameFromDropDownList () {
        createFolder();

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName + "']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/" + folderName + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//tr[@id='job_" + renamedFolder + "']/td[3]/a/span")).getText(),
                renamedFolder);
    }
}
