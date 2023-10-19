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
    @Test
    public void testJenkinsAddNewItemAndSearch(){
        WebElement buttonNewItem = getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']"));
        buttonNewItem.click();

        String nameProject = "Project_iod";
        WebElement inputFieldName = getDriver().findElement(By.id("name"));
        inputFieldName.sendKeys(nameProject);

        WebElement freestyleProject = getDriver().findElement(By.xpath("//span[text()='Freestyle project']"));
        freestyleProject.click();

        WebElement buttonOk = getDriver().findElement(By.id("ok-button"));
        buttonOk.click();

        WebElement buttonSave = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        buttonSave.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@id='skip2content']/following-sibling::*")).getText(),"Project " + nameProject);

    }
    @Ignore
    @Test
    public void testJenkinsSearchItem(){
        testJenkinsAddNewItemAndSearch();

        WebElement inputSearch = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        inputSearch.sendKeys("iod");
        inputSearch.submit();


    }
}

