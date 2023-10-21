package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

@Ignore
public class FilipRahuliaDraftTest extends BaseTest {

    private static final String testDescriptionText = "Test description";

    @Test
    public void testEditDescription() {

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(testDescriptionText);
        getDriver().findElement(By.name("Submit")).click();
        String actualDescriptionText = getDriver().findElement(By.xpath
                ("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualDescriptionText, testDescriptionText);
    }


}

