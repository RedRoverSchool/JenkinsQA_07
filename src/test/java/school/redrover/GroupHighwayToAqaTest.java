package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class GroupHighwayToAqaTest extends BaseTest {

    @Test
    public void testLogin() {

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/h1[text()='Welcome to Jenkins!']")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testEmptyProjectName() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();

        Assert.assertEquals(getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateNewFreestyleProject() {

        final String projectName = "HighwayNew";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                String.format("Project %s", projectName));
    }

    @Test
    public void testRenamePipelineProject() {

        final String projectName = "HighwayNewPipeline";
        final String newProjectName = "HighwayNewPipeline_NewName";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[.='Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[contains(.,'Rename')]")).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(newProjectName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        WebElement newTitle = getDriver().findElement(By.cssSelector(".job-index-headline"));

        assert newTitle.getText().contains("Pipeline " + projectName);
    }


    @Test
    public void testSetFolderDisplayNameAndDescription() {

        final String folderName = String.format("Some test folder name %3d", (int) (Math.random() * 1000));
        final String folderDisplayName = "Some test folder display name";
        final String folderDescription = "Some test folder description";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(folderDisplayName);
        getDriver().findElement(By.name("_.description")).sendKeys(folderDescription);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='main-panel']/h1")).getText(),
                folderDisplayName);
        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(),
                folderDescription);
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText()
                .contains(String.format("Folder name: %s", folderName)));
    }

    @Test
    public void testSideBarOnMainPage() {

        final String[] sideBarTitles = {"New Item", "People", "Build History", "Manage Jenkins", "My Views"};

        List<WebElement> sideBarItems = getDriver()
                .findElements(By.xpath("//div[@id = 'tasks']//div[@class = 'task ']"));

        Assert.assertEquals(sideBarTitles.length, sideBarItems.size());

        for (int i = 0; i < sideBarTitles.length; i++) {
            Assert.assertEquals(sideBarItems.get(i).getText(), sideBarTitles[i]);
        }
    }

    @Test
    public void testManageToolsGitInstallation() throws InterruptedException {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='configureTools']")).click();

        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement checkBox = getDriver().findElement(By.xpath("//span[@class='jenkins-checkbox']"));
        js.executeScript("arguments[0].scrollIntoView();", checkBox);
        checkBox.click();
        Thread.sleep(1000);

        WebElement addInstallerIsVisible = getDriver().findElement(By.xpath("//button[.='Add Installer']"));
        Assert.assertTrue(addInstallerIsVisible.isDisplayed());
    }

    @Test
    public void testComparisonManageSystem() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/section[2]/div/div[1]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1"))
                .getText(), "System");
    }

    @Test
    public void testCreateFolderViaCopyFrom() {

        final String originalFolderName = String.format("Some test folder name %3d", (int) (Math.random() * 1000));
        final String folderDescription = "Some test folder description";
        final String newFolderName = String.format("Some test folder name %3d", (int) (Math.random() * 1000));

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(originalFolderName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.description")).sendKeys(folderDescription);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(newFolderName);
        getDriver().findElement(By.id("from")).sendKeys(originalFolderName);
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(getDriver().findElement(By.name("_.description")).getText(),
                folderDescription);
    }
}
