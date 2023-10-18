package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import school.redrover.runner.JenkinsUtils;
import school.redrover.runner.ProjectUtils;


@Ignore
public class TopNewsTest extends BaseTest {




    @Test(description = "Проверка Заголовка приветствия")
    public void testJenkinsAuthorization() {

        String actualInfo = getDriver().findElement(By.xpath("//h2[@class ='h4'][contains(text(), 'Start')]")).getText();

        Assert.assertEquals(actualInfo, "Start building your software project", "Заголовок не совпадает");
    }

    @Test(description = "Проверка адреса URL страницы новой Job")
    public void testCheckUrl() {

        getDriver().findElement(By.xpath("//span[contains(text(),'Create a job')]")).click();
        String actualURL = getDriver().getCurrentUrl();

        Assert.assertEquals(actualURL, "http://localhost:8080/newJob", "URL не совпадает");
    }

    @Test
    public void testName() {
    }

    @Test
    public void testName() {
    }

    @Test
    public void testName() {
    }
}
