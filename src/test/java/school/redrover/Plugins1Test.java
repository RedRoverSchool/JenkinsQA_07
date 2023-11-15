package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Plugins1Test extends BaseTest{
    @Test
    public void testInstalledPluginsSearch() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='pluginManager']")).click();
        getDriver().findElement(By.xpath("//a[@href='/manage/pluginManager/installed']")).click();
        getDriver().findElement(By.xpath("//input[@type='search']")).sendKeys("Build Timeout");

        Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='https://plugins.jenkins.io/build-timeout']"))
                .getText().contains("Build Timeout"));
    }
}