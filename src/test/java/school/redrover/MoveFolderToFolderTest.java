package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MoveFolderToFolderTest extends BaseTest {

    @Test
    public void testMoveFolder() {
        createFolder("Main");
        navigateToJenkinsHome();

        createFolder("Nested");
        navigateToJenkinsHome();

        getDriver().findElement(By.linkText("Nested")).click();
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.className("setting-input")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/select/option[2]")).click();
        getDriver().findElement(By.name("Submit")).click();

        navigateToJenkinsHome();

        getDriver().findElement(By.cssSelector("li[data-href='/']")).click();
        getDriver().findElement(By.cssSelector("a[href='/view/all/']")).click();
        getDriver().findElement(By.cssSelector("li[data-href='/view/all/']")).click();
        getDriver().findElement(By.cssSelector("a[href='/view/all/job/Main/']")).click();

        Assert.assertEquals(
            getDriver()
                .findElement(By.xpath("//*[@id='job_Nested']/td[3]/a"))
                .getText(), "Nested");
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
    }

    private void navigateToJenkinsHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }
}
