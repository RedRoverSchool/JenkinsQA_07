package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class GroupJavaAutomationTest extends BaseTest {


    @Test
    public void testJenkinsHomePageAndJenkinsVersion()  {

        String title = getDriver().getTitle();
        Assert.assertEquals(title,"Dashboard [Jenkins]");

        WebElement versionJenkinsButton = getDriver().findElement
        (By.xpath("//button[@type='button']"));
        String versionJenkins = versionJenkinsButton.getText();
        Assert.assertEquals(versionJenkins,"Jenkins 2.414.2");
    }

    private void addNewItem(String nameProject) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameProject);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }
    @Test
    public void testJenkinsAddNewItemAndSearch() {

        final String nameProject = "Project_iod";
        addNewItem(nameProject);
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@id='skip2content']/following-sibling::*")).getText(),"Project " + nameProject);

    }
    @Ignore
    @Test
    public void testJenkinsSearchItem() {

        final String nameProject = "Project_iod";
        addNewItem(nameProject);

        WebElement inputSearch = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        inputSearch.sendKeys("iod");
        inputSearch.submit();

    }
}

