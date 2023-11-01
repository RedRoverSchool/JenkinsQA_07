package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder11Test extends BaseTest {

    private void clickElement(By locator) {
        getDriver().findElement(locator).click();
    }
    private  void createMainFolder() {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        getDriver().findElement(By.id("name")).sendKeys("Main Folder");
        clickElement(By.className("com_cloudbees_hudson_plugins_folder_Folder"));
        clickElement(By.id("ok-button"));
        clickElement(By.name("Submit"));
        goToDashboard();
    }
    private void createNestedFolder() {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        getDriver().findElement(By.id("name")).sendKeys("Nested Folder");
        clickElement(By.className("jenkins_branch_OrganizationFolder"));
        clickElement(By.id("ok-button"));
        clickElement(By.name("Submit"));
        goToDashboard();

    }
    private void goToDashboard() {
        clickElement(By.cssSelector("a.model-link[href='/']"));
    }

    @Test
    public void testMoveFolder() {
        createMainFolder();
        createNestedFolder();
        goToDashboard();

        clickElement(By.cssSelector("a[href='job/Nested%20Folder/']"));
        clickElement(By.cssSelector("a[href='/job/Nested%20Folder/move']"));
        clickElement(By.cssSelector("select[name='destination']"));
        clickElement(By.cssSelector("option[value='/Main Folder']"));
        clickElement(By.name("Submit"));
        goToDashboard();

        Assert.assertTrue(getDriver().findElements(By.cssSelector("a[href='job/Nested%20Folder/']")).isEmpty(), "Nested Folder was not moved.");

        clickElement(By.cssSelector("a[href='job/Main%20Folder/']"));

        Assert.assertFalse(getDriver().findElements(By.cssSelector("a.jenkins-table__link[href='job/Nested%20Folder/'] span")).isEmpty(), "Nested Folder was not found.");
    }
}
