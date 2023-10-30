package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    private void clickRestApi() {
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();
    }

    @Test
    public void testVersionJenkins() {
        Assert.assertEquals(
                getDriver()
                .findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button"))
                .getText(),
        "Jenkins 2.414.2");
    }

    @Test
    public void testVerifyClickabilityOfRestAPILink() {
        clickRestApi();

        Assert.assertEquals(getDriver().getTitle(), "Remote API [Jenkins]");
    }

    @Test
    public void testVerifyRedirectedRestApi() {
        clickRestApi();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("api"));
    }
    @Test
    public void testJenkinsVersionMainPage() {
        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']"))
                .getText(),"Jenkins 2.414.2");
    }
}
