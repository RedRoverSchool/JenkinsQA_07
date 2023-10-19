package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ArSaFirstTest extends BaseTest {

    @Test
    public void testCreateFolder() {

        // Set folder name
        final String folderName = "Artur Sabanadze";

        //Navigate to the new job creation page
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        //Enter folder name in the input field
        getDriver().findElement(By.id("name")).sendKeys(folderName);

        //Select the folder type (Organization Folder in this case)
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();

        //Click the "OK" button to create the folder
        getDriver().findElement(By.id("ok-button")).click();

        //Click the "Save" button to save the folder
        getDriver().findElement(By.name("Submit")).click();

        //Navigate to the created folder
        getDriver().findElement(By.className("model-link")).click();
        getDriver().findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item a.model-link")).click();
        getDriver().findElement(By.linkText(folderName)).click();

        //Navigate to the configuration page of the folder
        getDriver().findElement(By.linkText("Configure")).click();

        //Enter description in the textarea
        getDriver().findElement(By.name("_.description")).sendKeys("Organization File of Artur Sabanadze. Student of Redrover School (7)");

        //Click the "Save" button to submit the changes
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

    }
}