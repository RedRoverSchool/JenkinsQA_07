package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PopovTest extends BaseTest {
    private void createProject(){
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.id("name")).sendKeys("Test");
        getDriver().findElement(By.className("label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("Test Project");
        getDriver().findElement(By.cssSelector("#bottom-sticker > div > button.jenkins-button.jenkins-button--primary")).click();
    }

    @Test
    public void testMainHeading() {
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(3) > div > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testLogout(){
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testCreateProject() {
        createProject();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project Test");
    }

    @Test
    public void testDeleteProject() {
        createProject();
        getDriver().findElement(By.linkText("Delete Project")).click();
        getDriver().switchTo().alert().accept();
        Assert.assertEquals(getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testUserID() {
        getDriver().findElement(By.linkText("People")).click();
        getDriver().findElement(By.linkText("admin")).click();
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText(),
                "Jenkins User ID: admin");
    }
}
