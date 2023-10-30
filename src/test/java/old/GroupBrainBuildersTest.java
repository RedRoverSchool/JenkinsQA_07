package old;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

@Ignore
public class GroupBrainBuildersTest extends BaseTest {

    private void folderCreation(String folderName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//input[@name='_.displayNameOrNull']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testJenkinsFolderCreationWithValidName() {

        String folderName= "Folder1";
        folderCreation(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + folderName + "']")).isDisplayed());
    }

    @Test
    public void testJenkinsFolderCreationWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//div[@class='input-validation-message']")).isDisplayed());
    }

    @Test
    public void testJenkinsFolderRenameWithValidNameFromDropDownMenu() {

        String folderName = "Folder1";
        String renamedFolder = "Folder111";
        folderCreation(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + renamedFolder + "']")).isDisplayed());
    }

    @Test
    public void testJenkinsFolderRenameWithValidNameFromLeftMenu() {

        String folderName = "Folder1";
        String renamedFolder = "Folder111";
        folderCreation(folderName);
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName + "/']")).click();
        getDriver().findElement(By.linkText("Rename")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(renamedFolder);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + renamedFolder + "']")).isDisplayed());
    }

    @Test
    public void testJenkinsTwoFoldersCreationWithValidNames() {

        String folderName1= "Folder11";
        folderCreation(folderName1);
        getDriver().findElement(By.linkText("Dashboard")).click();

        String folderName2= "Folder22";
        folderCreation(folderName2);
        getDriver().findElement(By.linkText("Dashboard")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + folderName1 + "']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + folderName2 + "']")).isDisplayed());
    }

    @Test
    public void testJenkinsMoveFolderInsideAnotherFolderMovedFolderIsNotPresentOnDashboard() {

        String folderName1= "ParentFolder";
        folderCreation(folderName1);
        getDriver().findElement(By.linkText("Dashboard")).click();

        String folderName2= "NestedFolder";
        folderCreation(folderName2);
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName2 + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.xpath("//select[@name='destination']")).click();
        getDriver().findElement(By.xpath("//option[text() = 'Jenkins » " + folderName1 + "']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();

        List<WebElement> elements = getDriver().findElements(By.xpath("//tr[@id='job_" + folderName2 + "']"));
        Assert.assertTrue(elements.size() == 0);
    }

    @Test
    public void testJenkinsMoveFolderInsideAnotherFolderMovedFolderIsPresentInParentsFolder() {

        String folderName1= "ParentFolder";
        folderCreation(folderName1);
        getDriver().findElement(By.linkText("Dashboard")).click();

        String folderName2= "NestedFolder";
        folderCreation(folderName2);
        getDriver().findElement(By.linkText("Dashboard")).click();

        getDriver().findElement(By.xpath("//*[@id='job_" + folderName2 + "']/td[3]/a")).click();
        getDriver().findElement(By.linkText("Move")).click();
        getDriver().findElement(By.xpath("//select[@name='destination']")).click();
        getDriver().findElement(By.xpath("//option[text() = 'Jenkins » " + folderName1 + "']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.linkText("Dashboard")).click();
        getDriver().findElement(By.xpath("//a[@href='job/" + folderName1 + "/']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='job_" + folderName2 + "']")).isDisplayed());
    }

    @Test
    public void testJenkinsAdminStatus() {

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(2) > span > a")).click();
        // From the list of users I would like to get name of the particular user and click on it
        WebElement recordInTheList = getDriver().findElement(By.className("jenkins-table__link"));
        String userName = recordInTheList.getText();
        recordInTheList.click();
        // And to verify that on the next page userID match with the name
        Assert.assertTrue(getDriver().getPageSource().contains(userName));
    }

    @Test
    public void testJenkinsCredentialsTooltip() {

        WebElement adminMenu = getDriver().findElement(By.xpath("//a[@href='/user/admin']"));
        adminMenu.click();

        WebElement credentialsItem = getDriver().findElement(By.xpath("//a[@href='/user/admin/credentials']"));
        credentialsItem.click();

        WebElement systemTableItem = getDriver().findElement(By.xpath("//a[@href='/manage/credentials/store/system']"));
        systemTableItem.click();

        WebElement imageSystemTable = getDriver().findElement(By.xpath("//img[@class='icon-credentials-domain icon-lg']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(imageSystemTable).perform();

        WebElement tooltip = getDriver().findElement(By.xpath("//img[@aria-describedby = 'tippy-10']"));
        Assert.assertTrue(tooltip.isDisplayed());
    }


    private void createNewItemFreestyle(String freestyleName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(freestyleName);

        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText(), "Project " + freestyleName);
    }

    private void deleteItemFreestyle(String freestyleName) {

        getDriver().findElement(By.xpath("//a[@href='job/" + freestyleName + "/']")).click();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]/span/a/span[1]")).click();

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    @Test
    public void changeFreestyleName() throws InterruptedException {

        final String freestyleProjectName = "Brains";
        final String freestyleChangedProjectName = "NEW_Brains";

        createNewItemFreestyle(freestyleProjectName);

        getDriver().findElement(By.xpath("//a[@href='/']")).click();

        getDriver().findElement(By.xpath("//a[@href='job/" + freestyleProjectName + "/']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + freestyleProjectName + "/confirm-rename']")).click();

        WebElement newNameField = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input"));
        newNameField.clear();
        newNameField.sendKeys(freestyleChangedProjectName);
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.xpath("//a[@href='/']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='job/" + freestyleChangedProjectName + "/']")).getText(), freestyleChangedProjectName);

        deleteItemFreestyle(freestyleChangedProjectName);
    }

    @Test
    public void addFreestyleDescription() throws InterruptedException {

        final String freeStyleProjectName = "New_brains";

        createNewItemFreestyle(freeStyleProjectName);

        getDriver().findElement(By.xpath("//a[@href='/']")).click();

        getDriver().findElement(By.xpath("//a[@href='job/" + freeStyleProjectName + "/']")).click();

        getDriver().findElement(By.xpath("//a[@href='editDescription']")).click();

        WebElement descriptionField = getDriver().findElement(By.xpath("//*[@id='description']/form/div[1]/div[1]/textarea"));
        descriptionField.clear();
        descriptionField.sendKeys("my_new_project");
        getDriver().findElement(By.xpath("//*[@id='description']/form/div[2]/button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText(), "my_new_project");
    }
}
