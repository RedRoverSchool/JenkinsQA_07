package old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class GroupSurvivorsTest extends BaseTest {

    @Test
    public void testEvgenyCheckJenkinsVersion() {
        getDriver().findElement(By.cssSelector(".jenkins-button.jenkins-button--tertiary.jenkins_ver")).click();
        getDriver().findElement(By.xpath("//*[@class = 'jenkins-dropdown__item'][1]")).click();

        Assert.assertEquals(getDriver().findElement(By.className("app-about-version")).getText(), "Version 2.414.2");
    }

    @Test
    public void testEvgenyAddDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//*[@name = 'description']")).sendKeys("Test description");
        getDriver().findElement(By.xpath("//*[@id = 'description']/form/div[2]/button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id = 'description']/div")).getText(), "Test description");
    }

    @Test
    public void testEvgenyCreateFirstProject() {
        getDriver().findElement(By.xpath("//*[@href = 'newJob']")).click();
        getDriver().findElement(By.xpath("//*[@name = 'name']")).sendKeys("First project test");
        getDriver().findElement(By.xpath("//*[@class = 'category'][1]//li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//button[contains(text(), 'Save')]")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".job-index-headline.page-headline")).getText(), "Project First project test");
    }

    @Test
    public void testOlgaEditDescription() {

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.xpath("//*[@name = 'description']")).sendKeys("Test description");
        getDriver().findElement(By.xpath("//*[@id = 'description']/form/div[2]/button")).click();
        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//*[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//*[@name = 'description']")).sendKeys("Test new");
        getDriver().findElement(By.xpath("//*[@id = 'description']/form/div[2]/button")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id = 'description']/div")).getText(), "Test new");
    }

    @Test
    public void testOlgaTransitionAnalysis() {

        getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button")).click();
        getDriver().findElement(By.className("jenkins-dropdown__item")).click();
        getDriver().findElement(By.xpath("//input[@role='searchbox']")).sendKeys("log", Keys.ENTER);
        getDriver().findElement(By.className("jenkins-table__link")).click();

        Assert.assertEquals(getDriver().getTitle(), "All Jenkins Logs [Jenkins]");
    }

    @Test
    public void testIuliaFindIconLegend() throws InterruptedException {
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();
        getDriver().findElement(By.xpath("//*[@id='button-icon-legend']")).click();
        Thread.sleep(300);

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[@class='jenkins-modal__title']")).getText(), "Icon legend");
    }
}


