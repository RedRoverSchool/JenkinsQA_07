package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RestApiLlinkTest extends BaseTest {

    @Test(description = "Кликабельность ссылки и отображение страницы REST API")
    public void testvisabilityAndClickabilityRestApiLink() {

        String link = getDriver().findElement(By.xpath("//a[@href='api/']")).getText();
        getDriver().findElement(By.xpath("//a[@href='api/']")).click();

        String getTitle = getDriver().findElement(By.xpath("//h1[contains(text(),'REST API')]")).getText();

        Assert.assertEquals(link, "REST API", "заголовок не совпадает");
        Assert.assertEquals(getTitle, "REST API", "заголовок страницы не совпадает");

    }

}
