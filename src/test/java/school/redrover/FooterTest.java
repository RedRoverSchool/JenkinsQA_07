package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.collections.Sets;
import school.redrover.runner.BaseTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void testRestApiPageOpensAndHas3ApiOptions() {
        clickRestApi();

        List<WebElement> apiTypes = getDriver().findElements(By.xpath("//div[@id='main-panel']/dl/dt/a"));
        Assert.assertEquals(apiTypes.size(), 3, "REST API page should always have 3 API types");

        Set<String> apiTypeText = new HashSet<>();
        for (WebElement el : apiTypes) {
            apiTypeText.add(el.getText());
        }
        Set<String> expected = Sets.newHashSet("XML API", "JSON API", "Python API");
        Assert.assertEquals(apiTypeText, expected);
    }

}
