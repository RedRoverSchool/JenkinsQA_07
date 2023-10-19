package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MariaPracticeTest extends BaseTest {

    @Test
    public void testWelcomeToJenkins() {

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[text()='Dashboard']")).getText(),
                "Dashboard");
    }

    @Test
    public void testCreateProjectQA() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys("QA");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//label[@class='attach-previous ']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("About Java languages");
        getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--primary ']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Project QA']")).getText(), "Project QA");
    }

    @Test
    public void testJenkinsVersion() {

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"))
                .getText(), "Jenkins 2.414.2");
    }

    @Test
    public void testCheckTitle() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@href='/manage']"))
                .getText(), "Manage Jenkins");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='System Configuration']"))
                .getText(), "System Configuration");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='System']"))
                .getText(), "System");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Tools']"))
                .getText(), "Tools");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Plugins']"))
                .getText(), "Plugins");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Clouds']"))
                .getText(), "Clouds");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Security']"))
                .getText(), "Security");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Users']"))
                .getText(), "Users");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Status Information']"))
                .getText(), "Status Information");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='System Information']"))
                .getText(), "System Information");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Load Statistics']"))
                .getText(), "Load Statistics");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='About Jenkins']"))
                .getText(), "About Jenkins");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Troubleshooting']"))
                .getText(), "Troubleshooting");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Manage Old Data']"))
                .getText(), "Manage Old Data");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Tools and Actions']"))
                .getText(), "Tools and Actions");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Reload Configuration from Disk']"))
                .getText(), "Reload Configuration from Disk");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Jenkins CLI']"))
                .getText(), "Jenkins CLI");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Script Console']"))
                .getText(), "Script Console");
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[text()='Prepare for Shutdown']"))
                .getText(), "Prepare for Shutdown");
    }
}