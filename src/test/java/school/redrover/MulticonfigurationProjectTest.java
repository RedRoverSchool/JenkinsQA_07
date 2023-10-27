package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProjectTest extends BaseTest {

    private void createMulticonfigurationProject (String MulticonfigurationProjectName) {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(MulticonfigurationProjectName);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateWithValidName() {
        final String MulticonfigurationProjectName = "MCProjectName";
        createMulticonfigurationProject (MulticonfigurationProjectName);

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + MulticonfigurationProjectName + "/']")).click();

        Assert.assertEquals(getDriver().getTitle(),MulticonfigurationProjectName + " [Jenkins]");
    }
}
