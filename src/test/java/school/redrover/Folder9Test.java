package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class Folder9Test extends BaseTest {

    private void creationNewFolder(String folder) {

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(folder);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();

    }

    @Test
    public void testCreatingNewFolder() {

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys("TestFolder");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_TestFolder']/td[3]/a/span")).getText(),
                "TestFolder");
    }

    @Ignore
    @Test
    public void testRenameFolder2() {

        final String folderName = "FolderTest";
        creationNewFolder(folderName);

        getDriver().findElement(By.xpath("//*[@id='job_FolderTest']/td[3]/a/span")).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")).clear();
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input")).sendKeys("NewName");
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_NewName']/td[3]/a/span")).getText(),
                "NewName");
    }

    @Test
    public void testSubfolderInBreadcrumbs() {
        final String folderName = "Test Folder";
        final String subfolderName = "Test SubFolder";

        creationNewFolder(folderName);

        getDriver().findElement(By.xpath(String.format("//td/a[@href='job/%s/']",
                folderName.replace(" ", "%20")))).click();
        getDriver().findElement(By.xpath("//li/a[@href='newJob']")).click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(subfolderName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();

        List<String> listBreadcrumbsItems = getDriver().findElements(
                        By.xpath("//li[@class='jenkins-breadcrumbs__list-item']/a"))
                .stream().map(WebElement::getText).toList();
        List<String> listExpectedItems = new ArrayList<>(List.of
                (new String[]{"Dashboard", folderName, subfolderName}));

        Assert.assertEquals(listBreadcrumbsItems, listExpectedItems);
    }
}
