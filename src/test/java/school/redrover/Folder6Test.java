package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder6Test extends BaseTest {

    private static final String VALID_NAME = "Folder1";
    private static final String EMPTY_NAME = "";
    private static final String INVALID_NAME = "&";
    private static final String NEW_VALID_NAME = "Folder2";

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

    @Test
    public void testCreate() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();

        WebElement INPUT_FIELD = getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']"));

        INPUT_FIELD.sendKeys(EMPTY_NAME);
        getDriver().findElement(By.xpath("//div[@id = 'itemname-required']"));

        INPUT_FIELD.sendKeys(INVALID_NAME);
        getDriver().findElement(By.xpath("//div[@id = 'itemname-invalid']"));
        INPUT_FIELD.clear();

        INPUT_FIELD.sendKeys(VALID_NAME);
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        utilsGoDashboard();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']"))
                        .getText(), VALID_NAME);
    }

    @Test
    public void testRename() {

        utilsCreate(VALID_NAME);

        getDriver().findElement(By.xpath("//a[@href = '/job/" + VALID_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.xpath("//input[@value = '" + VALID_NAME + "']")).clear();

        getDriver().findElement(By.xpath("//input[@checkurl = 'checkNewName']")).sendKeys(NEW_VALID_NAME);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        utilsGoDashboard();

        Assert.assertEquals(getDriver()
                        .findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']"))
                        .getText(), NEW_VALID_NAME);
    }
}
