package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder6Test extends BaseTest {

    final String validName = "Folder1";
    final String emptyName = "";
    final String invalidName = "&";

    private void utilsGoDashboard() {
        getDriver().findElement(By.xpath("//img[@alt = 'Jenkins']")).click();
    }

    @Test
    public void testCreate() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(emptyName);
        getDriver().findElement(By.xpath("//div[@id = 'itemname-required']"));

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(invalidName);
        getDriver().findElement(By.xpath("//div[@id = 'itemname-invalid']"));
        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).clear();

        getDriver().findElement(By.xpath("//input[@class = 'jenkins-input']")).sendKeys(validName);
        getDriver().findElement(By.xpath("//li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.xpath("//button[@type = 'submit']")).click();

        utilsGoDashboard();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//a[@class = 'jenkins-table__link model-link inside']"))
                .getText(), validName);
        
    }
}
