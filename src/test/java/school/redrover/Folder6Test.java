package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.Arrays;
import java.util.List;

public class Folder6Test extends BaseTest {

    private static final String VALID_NAME = "Folder1";
    private static final String EMPTY_NAME = "";
    private static final String INVALID_NAME = ".";
    private static final String NEW_VALID_NAME = "Folder2";
    private static final String NAME_FOR_BOUNDARY_VALUES = "A";

    private void utilsCreate(String folderName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
    }

    private void utilsGoDashboard() {
        getDriver().findElement(By.xpath("//img[@alt = 'Jenkins']")).click();
    }

    private void utilsDelete(String folderName) {
        getDriver().findElement(By.xpath("//span[text() = '" + folderName + "']")).click();
        getDriver().findElement(By.xpath("//a[@href = '/job/" + folderName + "/delete']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).click();
    }
    private void utilsGoNameField() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();
    }

    @Test
    public void testCreateEmptyName() {
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(EMPTY_NAME);

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'itemname-required']")).getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateValidName() {
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(VALID_NAME);
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        utilsGoDashboard();
        boolean findName = getDriver().findElement(By.xpath("//tr[@id = 'job_" + VALID_NAME + "']")).isDisplayed();

        Assert.assertTrue(findName);
    }

    @Test
    public void testRename() {
        utilsCreate(VALID_NAME);

        getDriver().findElement(By.xpath("//a[@href = '/job/" + VALID_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@value = '" + VALID_NAME + "']")).clear();

        getDriver().findElement(By.xpath("//input[@checkurl = 'checkNewName']")).sendKeys(NEW_VALID_NAME);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        utilsGoDashboard();
        boolean findName = getDriver().findElement(By.xpath("//tr[@id = 'job_" + NEW_VALID_NAME + "']")).isDisplayed();

        Assert.assertTrue(findName);
    }

    @Test
    public void testCreateNameSpecialCharacters() {
        List<String> invalidNames = Arrays.asList("#", "&", "?", "!", "@", "$", "%", "^", "*", "|", "/", "\\", "<", ">", "[", "]", ":", ";");

        utilsGoNameField();

        WebElement inputName = getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']"));

        for (String invalidName: invalidNames) {

            inputName.sendKeys(invalidName);

            Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id = 'itemname-invalid']")).getText(), "» ‘" + invalidName + "’ is an unsafe character");

            inputName.clear();
        }
    }

    @Test
    public void testDelete() {
        utilsCreate(VALID_NAME);
        utilsGoDashboard();
        utilsDelete(VALID_NAME);

        getDriver().findElement(By.xpath("//input[@role = 'searchbox']")).sendKeys(VALID_NAME + "\n");

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'error']")).getText(), "Nothing seems to match.");
    }

    @Test
    public void testBoundaryValuesName() {
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(NAME_FOR_BOUNDARY_VALUES.repeat(1));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.xpath("//h1[text() = 'Configuration']"));

        utilsGoDashboard();
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(NAME_FOR_BOUNDARY_VALUES.repeat(255));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();
        getDriver().findElement(By.xpath("//h1[text() = 'Configuration']"));

        utilsGoDashboard();
        utilsGoNameField();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(NAME_FOR_BOUNDARY_VALUES.repeat(256));
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h2[@style = 'text-align: center']")).getText(), "A problem occurred while processing the request.");
    }
}
