package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class OrganizationFolder2Test extends BaseTest {

    private void createOrganizationFolder() {

        final String folderName = "OrganizationFolder";

        getDriver().findElement(By.cssSelector("a[href='newJob']")).click();
        getDriver().findElement(By.xpath("//span[text() = 'Organization Folder']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name ='Submit']")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        getDriver().findElement(By.cssSelector("td a[href='job/" + folderName + "/'] span")).click();
    }

    @Test
    public void renameOrganizationFolder() {

        createOrganizationFolder();

        final String newFolderName = "OrganizationFolderRenamed";

        getDriver().findElement(By.xpath("//a[@href='/job/OrganizationFolder/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newFolderName);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath
                ("//h1[contains(text(), 'OrganizationFolderRenamed')]")).getText(), newFolderName);
    }

    @Test
    public void testDeleteOrganizationFolder() {
        final String folderName = "OrganizationFolder";
        boolean deletetOK = true;

        createOrganizationFolder();

        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.linkText(folderName)).click();
        getDriver().findElement(By.xpath("//a[@href='/job/OrganizationFolder/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        try {
            if (getDriver().findElement(By.xpath("//table[@id ='projectstatus']")).isDisplayed()) {
                List<WebElement> elements = getDriver().findElements(By.xpath("//td/a"));
                List<String> jobs = new ArrayList<>();
                for (WebElement element : elements) {
                    jobs.add(element.getText());
                }
                deletetOK = jobs.contains(folderName);
            }
        } catch (Exception e) {
            deletetOK = false;
        }

        Assert.assertTrue(getDriver().getTitle().equals("Dashboard [Jenkins]"));
        Assert.assertFalse(deletetOK);
    }
}