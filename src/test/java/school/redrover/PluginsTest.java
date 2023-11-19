package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.SeleniumUtils;

import java.util.List;

public class PluginsTest extends BaseTest {

    @Test
    public void testInstalledPluginsContainsAnt() {
        getDriver().findElement(By.xpath(
                "//*[@id='tasks']//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dl/dt[text()='Plugins']")).click();

        WebElement installedPlugins = getDriver().findElement(By.xpath(
                "//a[@href = '/manage/pluginManager/installed']"));

        SeleniumUtils.jsClick(getDriver(), installedPlugins);

        List<WebElement> plugins = getDriver().findElements(By.xpath("//a[starts-with(@href, 'https://plugins.jenkins.io')]"));

        boolean foundAntPlugin = false;
        for (WebElement plugin : plugins) {
            String text = plugin.getText();
            if (text.contains("Ant Plugin")) {
                foundAntPlugin = true;
                break;
            }
        }
        Assert.assertTrue(foundAntPlugin);
    }

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