package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder11Test extends BaseTest {

    private void clickElement(By locator) {
        getDriver().findElement(locator).click();
    }
    private void itemName(String name){
        getDriver().findElement(By.id("name")).sendKeys(name);
    }
    private void createMainFolder(String folderName) {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        itemName(folderName);
        clickElement(By.className("com_cloudbees_hudson_plugins_folder_Folder"));
        clickElement(By.id("ok-button"));
        goToDashboard();
    }
    private void goToDashboard() {
        clickElement(By.cssSelector("a.model-link[href='/']"));
    }

    @Test
    public void testMoveFolder() {
        createMainFolder("Main Folder");
        createMainFolder("Nested Folder");
        clickElement(By.cssSelector("a[href='job/Nested%20Folder/']"));
        clickElement(By.cssSelector("a[href='/job/Nested%20Folder/move']"));
        clickElement(By.cssSelector("select[name='destination'] option[value='/Main Folder']"));
        clickElement(By.name("Submit"));
        goToDashboard();
        clickElement(By.cssSelector("a[href='job/Main%20Folder/']"));

        Assert.assertFalse(getDriver().findElements(By.cssSelector("a.jenkins-table__link[href='job/Nested%20Folder/'] span")).isEmpty(), "Nested Folder was not found.");
    }
}
