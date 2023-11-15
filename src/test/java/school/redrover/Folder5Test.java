package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import static org.testng.Assert.assertEquals;

public class Folder5Test extends BaseTest {

    @Test
    public void testCreateFolder() {
        createFolder("Main");
        navigateToJenkinsHome();

        assertEquals(
            getDriver().findElement(By.xpath("//*[@id='job_Main']/td[3]/a")).getText(), "Main"
        );
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testMoveFolderToFolder() {
        createFolder("Nested");

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

        assertEquals(
            getDriver()
                .findElement(By.xpath("//*[@id='job_Nested']/td[3]/a"))
                .getText(), "Nested");
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testCreateJobInsideFolder() {
        getDriver().findElement(By.xpath("//*[@id='job_Main']/td[3]/a")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Main/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("NewProject");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.cssSelector("li[data-href='/job/Main/']")).click();
        getDriver().findElement(By.className("jenkins-dropdown__item")).click();

        assertEquals(
            getDriver()
                .findElement(By.xpath("//a[@href='job/NewProject/']"))
                .getText(), "NewProject");
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testAddDescriptionFolder() {
        getDriver().findElement(By.xpath("//*[@id='job_Main']/td[3]/a")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("new description");
        getDriver().findElement(By.name("Submit")).click();

        assertEquals(
            getDriver()
                .findElement(By.xpath("//*[@id='description']/div[1]"))
                .getText(), "new description"
        );
    }

    @Test(dependsOnMethods = "testAddDescriptionFolder")
    public void testDeleteDescriptionFolder() {
        //createFolder("Main");
        getDriver().findElement(By.xpath("//*[@id='job_Main']/td[3]/a")).click();
        addDescription("description");
        navigateToJenkinsHome();

        getDriver().findElement(By.xpath("//*[@id='job_Main']/td[3]/a")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.className("jenkins-input")).clear();
        getDriver().findElement(By.name("Submit")).click();

        assertEquals(
            getDriver()
                .findElement(By.xpath("//*[@id='description']/div[1]"))
                .getText(), ""
        );
    }

    private void createFolder(String folderName) {
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        navigateToJenkinsHome();
    }

    private void navigateToJenkinsHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void addDescription(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();
    }
}
