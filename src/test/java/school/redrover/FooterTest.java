package school.redrover;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class FooterTest extends BaseTest {

    @Test
    //https://trello.com/c/OOkxIvHX/402-tc1200107-footer-jenkins-version-check-the-tippy-box
    public void checkTippyBox() throws InterruptedException
    {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")));

        List<String> expectedMenu = List.of("About Jenkins", "Get involved", "Website");
        List<String> actualMenu = new ArrayList<>();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-button--tertiary jenkins_ver']")).click();
        Thread.sleep(1000);
        List<WebElement> tippyBoxElements = getDriver().findElements(By.xpath("//div[@class = 'tippy-content']/a"));

        for (WebElement tippyContectMenu : tippyBoxElements) {
            actualMenu.add(tippyContectMenu.getText());
        }

        Assert.assertEquals(actualMenu, expectedMenu, "Tippy box context menu doesn't macth");
    }
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
