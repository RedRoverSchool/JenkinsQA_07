package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ViewTest extends BaseTest {

    private void goHome() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createNewFreestyleProject(String projectName) {
        goHome();
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
    }

    private void createMyNewListView(String viewName) {
        goHome();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.cssSelector("a[title='New View']")).click();
        getDriver().findElement(By.cssSelector("input[name='name']")).sendKeys(viewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.cssSelector("button[id='ok']")).click();
    }

    @Test
    public void testCreateNewView() {
        final String nameFreestyleProject = "My new Freestyle project";
        final String nameView = "Test view";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(nameFreestyleProject);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).submit();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.xpath("//a[@href='/user/admin/my-views/newView']")).click();
        getDriver().findElement(By.xpath("//input[@checkurl='/user/admin/my-views/viewExistsCheck']")).sendKeys(nameView);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.ListView']")).click();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).submit();
        getDriver().findElement(By.xpath("//button[@formnovalidate = 'formNoValidate']")).submit();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/user/admin/my-views/view/Test%20view/']")).getText(), nameView);
    }

    @Test
    public void testRenameView() {
        final String projectName = "My New Freestyle Project";
        final String viewName = "Test View";
        final String newViewName = "New Test View";

        createNewFreestyleProject(projectName);
        createMyNewListView(viewName);
        goHome();

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'" + viewName + "')]")).click();
        getDriver().findElement(By.xpath("//a[contains(@href,'/configure')]")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).clear();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(newViewName);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[contains(@class,'active')]/a")).getText(),
                newViewName);
    }
    @Test
    public void testCreateNewView2() {

        final String myProjectName = "My new freestyle project name";
        final String newViewName = "My new view name";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(myProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//a[@tooltip = 'New View']")).click();

        getDriver().findElement(By.id("name")).sendKeys(newViewName);
        getDriver().findElement(By.xpath("//label[@for='hudson.model.MyView']")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id= 'projectstatus-tabBar']/div/div[1]/div[2]/a")).getText(),  NewViewName);
    }

}
