package school.redrover;

import org.openqa.selenium.By;
<<<<<<< HEAD
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;



public class FreestyleProject5Test extends BaseTest {

    @Test
    public void testCreat() {
        /*
        1.Create a freestyle project with valid name
        2.See created project on a Dashboard
        3.Can go to created project configuration page
         */


        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys("FreeStyleProjectName");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href = 'job/FreeStyleProjectName/']")).click();

                Assert.assertEquals(
                        getDriver().findElement(By.cssSelector("#main-panel >h1")).getText(),
                        "Project FreeStyleProjectName");


















    }

}
=======
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject5Test extends BaseTest {
    private void createProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void deleteProject() {
        final String projectName1 = "Dead project";
        createProject(projectName1);
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();
        getDriver().findElement(By.xpath("//span[text()='" + projectName1 + "']")).click();
        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().switchTo().alert().accept();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a")).click();

        boolean deleted = isElementDeleted(By.xpath("//span[text()='" + projectName1 + "']"));
        assert (deleted);
    }

    private boolean isElementDeleted(By locator) {
        try {
            WebElement element = getDriver().findElement(locator);
            return !element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return true;
        }
    }
}

>>>>>>> e82a678bd113fe46170918185a7bce7cd7d81693
