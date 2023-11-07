package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject3Test extends BaseTest {

    @Test
    public void testCreateMulticonfiguratiobProject() {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.cssSelector("#name")).sendKeys("Multiconfiguration");
        getDriver().findElement(By.cssSelector(".hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//td/a[@href='job/Multiconfiguration/']")).click();

        Assert.assertEquals
                (getDriver().findElement(By.cssSelector("h1[class='matrix-project-headline page-headline']")).getText(),
                        "Project Multiconfiguration");
    final static private String NAME_PROJECT = "TestName";

    @Test
    public void testCreateMultiProject(){
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NAME_PROJECT);
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Project " + NAME_PROJECT);

    }
}
