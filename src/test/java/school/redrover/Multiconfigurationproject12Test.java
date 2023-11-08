package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Multiconfigurationproject12Test extends BaseTest {

    private static final  String DESCRIPTION = "This is a description!!!";
    private static final  String PROJECTNAME = "MulticonfigurationProject";


    private WebElement textDescription() {
        return getDriver().findElement(By.xpath("//*[@class='jenkins-!-margin-bottom-0']//div"));
    }

    private void createProject() {

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECTNAME);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
    }

    private void addDescriptionIntoProject() {
        getDriver().findElement(By.xpath("//span[normalize-space()='" + PROJECTNAME + "']")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
    }

    @Test
    public void createProjectWithDescription() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECTNAME);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[@class='jenkins-!-margin-bottom-0']//div")).isDisplayed());
        Assert.assertEquals(DESCRIPTION, textDescription().getText());
    }

    @Test
    public void addDescription() {
        createProject();
        addDescriptionIntoProject();
        Assert.assertEquals(DESCRIPTION, textDescription().getText());
    }

    @Test
    public void editDescription() {
        createProject();
        addDescriptionIntoProject();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        System.out.println(getDriver().findElement(By.xpath("//div[@id='description']//div")).getText());
        Assert.assertEquals(DESCRIPTION + DESCRIPTION, textDescription().getText());
    }

    @Test
    public void deleteDescription() {
        createProject();
        addDescriptionIntoProject();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        System.out.println(getDriver().findElement(By.xpath("//div[@id='description']//div")).getText() + " del descr");
        Assert.assertEquals("", textDescription().getText());
    }

}
