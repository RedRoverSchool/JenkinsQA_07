package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PluginsTest extends BaseTest {
    public void jsClick(WebElement element) throws InterruptedException {

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    @Test
    public void testEnabledPluginsList() throws InterruptedException {
        WebElement manageJenkins = getDriver().findElement(By.xpath("//*[@id='tasks']//a[@href='/manage']"));
        manageJenkins.click();
        getDriver().findElement(By.xpath("//dl/dt[text()='Plugins']"));
        getDriver().findElement(By.className("task-link-text"));
        getDriver().findElement(By.xpath(
                "//*[@id='main-panel']/section[2]/div/div[3]/a/dl/dd[1]")).click();
        WebElement installedPlugins = getDriver().findElement(By.xpath(
                "//a[@href = '/manage/pluginManager/installed']"));
        jsClick(installedPlugins);

        List<WebElement> plugins = getDriver().findElements(By.xpath("//a[starts-with(@href, 'https://plugins.jenkins.io')]"));
        Set<String> pluginsNames = new HashSet<>();
        for (WebElement el : plugins) {
            pluginsNames.add(el.getText());
        }

            Assert.assertTrue(pluginsNames.contains("Ant Plugin") );
        }
    }
