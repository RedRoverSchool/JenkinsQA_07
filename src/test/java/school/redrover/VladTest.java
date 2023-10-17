package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;


public class VladTest extends BaseTest {
    @Test
    public void testLogin() {

        getDriver().get("https://www.ministryoftesting.com/");

        WebElement language = getDriver().findElement(By.id("nav-sign-in"));
        language.click();

        WebElement textEmail = getDriver().findElement(By.id("user_login"));
        textEmail.sendKeys("newtestd0tc0m@gmail.com");

        WebElement textPass = getDriver().findElement(By.name("user[password]"));
        textPass.sendKeys("_.3JsTMMvjtqzAa");

        WebElement textLogin = getDriver().findElement(By.name("commit"));
        textLogin.click();

        WebElement pngAccount = getDriver().findElement(By.xpath("/html/body/main/header/nav/div/div[2]/div[2]/a/img"));
        pngAccount.click();

        WebElement textDashboard = getDriver().findElement(By.xpath("/html/body/main/header/nav/div/div[2]/div[2]/ul/li[3]/a"));
        textDashboard.click();

        WebElement textExpected = getDriver().findElement(By.xpath("/html/body/main/div[3]/section[2]/div/div/div[2]/p"));

        String value = textExpected.getText();
        Assert.assertEquals(value, "Please use the navigation to find the existing sections of your My MoT pages.");
    }

    @Test
    public void testJenkinsVersion() {

        JenkinsUtils.login(getDriver());

        getDriver().findElement(By.xpath("/html/body/footer/div/div[2]/button")).click();

        getDriver().findElement(By.xpath("/html/body/div[3]/div/div/a[1]/div")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/p")).getText(),
                "Version 2.414.2");
    }

    @Test
    public void testJenkinsManage() {

        JenkinsUtils.login(getDriver());

        getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/span/a")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/section[2]/div/div[1]/a/dl/dt")).getText(),
                "System");

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/section[2]/div/div[2]/a/dl/dt")).getText(),
                "Tools");

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/section[2]/div/div[3]/a/dl/dt")).getText(),
                "Plugins");

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/section[2]/div/div[4]/a/dl/dt")).getText(),
                "Nodes");

        Assert.assertEquals(getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/section[2]/div/div[5]/a/dl/dt")).getText(),
                "Clouds");
    }
}
