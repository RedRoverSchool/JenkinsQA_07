package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Folder10Test extends BaseTest {

    private static final String ITEM_NAME = "testfolder";
    private static final String NEW_ITEM_NAME = "testfolder_rename";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + ITEM_NAME + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText(),
                ITEM_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testRename() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + ITEM_NAME + "/']")).click();

        getDriver().findElement(By.xpath("//a[@href='/job/" + ITEM_NAME + "/confirm-rename']")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys(NEW_ITEM_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/" + NEW_ITEM_NAME + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='main-panel']//h1")).getText(),
                NEW_ITEM_NAME);
    }

    @Test
    public void testCreatingANewFolder() {
        final String folder = "Folder_1";

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]//a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folder);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']//button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_Folder_1']/td[3]/a")).getText(), folder);

    }

}
