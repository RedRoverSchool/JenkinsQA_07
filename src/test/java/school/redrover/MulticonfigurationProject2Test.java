package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MulticonfigurationProject2Test extends BaseTest {

    private static final String IS_DESCRIPTION_FOR_MULTICONFIGURATION_PROJECT_11 = "There is description for MulticonfigurationProject11!";
    private static final String MULTICONFIGURATION_PROJECT_11 = "MulticonfigurationProject11";

    @Test
    public void testCreate() {
        final String projectName = "MyMulticonfigurationProject";

        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        getDriver().findElement(By.xpath("//td/a[@href = 'job/" + projectName + "/']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#main-panel > h1")).getText(),
                "Project " + projectName);

    }

    @Test
    public void testCreate1 () {
        final String ProjectName = "MulticonfigurationProject";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("MulticonfigurationProject");
        getDriver().findElement(By.xpath("//li[@class='hudson_matrix_MatrixProject']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//td/a[@href='job/" + ProjectName + "/']")).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".matrix-project-headline.page-headline")).getText(),
                "Project " + ProjectName);
    }
    private void createMulticonfigurationProject() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@id ='name']")).sendKeys(MULTICONFIGURATION_PROJECT_11);
        getDriver().findElement(By.xpath("//span[normalize-space()='Multi-configuration project']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        getDriver().findElement(By.xpath("//td//a[@href = 'job/" + MULTICONFIGURATION_PROJECT_11 + "/']")).click();
    }

    private void addAndSeeDescriptionMulticonfigurationProject() {
        createMulticonfigurationProject();

        getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']")).click();

        getDriver().findElement(By.xpath("//span[contains(text(), 'MulticonfigurationProject11')]")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();

        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(IS_DESCRIPTION_FOR_MULTICONFIGURATION_PROJECT_11);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testCreateMulticonfigurationProjectWithDescription() {
        createMulticonfigurationProject();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id = 'main-panel']/h1")).getText(),
                "Project " + MULTICONFIGURATION_PROJECT_11);
    }

    @Test
    public void testAddAndDescriptionMulticonfigurationProject() {
        addAndSeeDescriptionMulticonfigurationProject();

        Assert.assertTrue(getDriver()
                .findElement(By.xpath("//div[@class='jenkins-!-margin-bottom-0']"))
                .getText()
                .contains(IS_DESCRIPTION_FOR_MULTICONFIGURATION_PROJECT_11));
    }
}
