package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolderTest extends BaseTest {

    private void creationNewOrganizationFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testCreatedNewOrganizationFolder() {
        final String folderName = "Organization_Folder";

        creationNewOrganizationFolder(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//tr[@id='job_" + folderName + "']")).isDisplayed());

    }

    @Test
    public void testDeleteOrganizationFolder() {
        final String folderName = "Organization_Folder";

        creationNewOrganizationFolder(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.xpath("//span/a[@href='/job/Organization_Folder/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Welcome to Jenkins!");
    }

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

    @Test
    public void testDisableOrganizationFolder() {

        creationNewOrganizationFolder("New Folder");

        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//button[@name='Submit']")).getText(),
                "Enable");
    }

    @Test
    public void testCreateOrganizationFolderWithValidName() {

        final String name = "Project";

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Organization Folder']")).click();
        getDriver().findElement(By.xpath("//div[@class='footer']//button")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']//button[@name='Submit']")).click();
        WebElement findObject = getDriver().findElement(By.xpath("//ol[@id='breadcrumbs']/li[3]/a"));
        String actualResult = findObject.getText();

        Assert.assertEquals(actualResult, name);
    }

    @Test
    public void testVerifyWarningMessageEmptyName() {

        final String expectedResultWarningMessage = "» This field cannot be empty, please enter a valid name";

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//span[text()='Organization Folder']")).click();

        WebElement findWarning = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));
        String actualResult = findWarning.getText();

        Assert.assertEquals(actualResult, expectedResultWarningMessage);
    }

    @Test
    public void testRedirectAfterDeleting() {
        final String folderName = "OrganizationFolder";

        creationNewOrganizationFolder(folderName);

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.xpath("//a[@href='/job/OrganizationFolder/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertTrue(getDriver().getTitle().equals("Dashboard [Jenkins]"));
    }
}