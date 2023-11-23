package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.model.HomePage;
import school.redrover.model.PluginsPage;
import school.redrover.runner.BaseTest;
import java.util.List;

public class PluginsTest extends BaseTest {

    @Test
    public void testInstalledPluginsContainsAnt() {
        new HomePage(getDriver())
                .clickManageJenkins()
                .goPluginsPage();

        List<String> pluginsNames =  new PluginsPage(getDriver())
                .jsClickInstalledPlugins()
                .installedPluginsList();

        Assert.assertFalse(pluginsNames.isEmpty(), "No elements in the List");
        Assert.assertTrue(pluginsNames.stream().anyMatch(text -> text.contains("Ant Plugin")),"Ant Plugin was not found in the list of installed plugins.");
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