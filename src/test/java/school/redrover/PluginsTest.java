package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PluginsTest extends BaseTest {
    AdditionalUtils additionalUtils = new AdditionalUtils();
    @Test
    public void testInstalledPluginsContainsAnt() {
        getDriver().findElement(By.xpath(
                "//*[@id='tasks']//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dl/dt[text()='Plugins']")).click();

        WebElement installedPlugins = getDriver().findElement(By.xpath(
                "//a[@href = '/manage/pluginManager/installed']"));
        additionalUtils.jsClick(getDriver(),installedPlugins);

        List<WebElement> plugins = getDriver().findElements(By.xpath("//a[starts-with(@href, 'https://plugins.jenkins.io')]"));

        boolean foundAntPlugin = false;
        for (WebElement plugin : plugins) {
            String content = additionalUtils.findTextInPseudoElement(getDriver(), plugin,"::before");
            content = content.replaceAll("^\"|\"$", "");

            if ("Ant Plugin".contains(content)) {
                foundAntPlugin = true;
                break;
            }
        }
        Assert.assertTrue(foundAntPlugin, "Ant Plugin");
    }
}