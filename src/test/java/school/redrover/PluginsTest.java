package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class PluginsTest extends BaseTest {

    @Test
    public void testInstalledPluginsContainsAnt() {
        getDriver().findElement(By.xpath(
                "//*[@id='tasks']//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//dl/dt[text()='Plugins']")).click();

        WebElement installedPlugins = getDriver().findElement(By.xpath(
                "//a[@href = '/manage/pluginManager/installed']"));

        AdditionalUtils.jsClick(getDriver(), installedPlugins);

        List<WebElement> plugins = getDriver().findElements(By.xpath("//a[starts-with(@href, 'https://plugins.jenkins.io')]"));

        Assert.assertFalse(plugins.isEmpty(), "No elements is the List");
        boolean foundAntPlugin = plugins.stream()
                .map(WebElement::getText)
                .anyMatch(text -> text.contains("Ant Plugin"));

        Assert.assertTrue(foundAntPlugin, "Ant Plugin is not found in the list of installed plugins.");
    }
}