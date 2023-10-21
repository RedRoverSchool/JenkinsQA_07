package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FilipRahuliaJenkinsTest extends BaseTest {

    @Test
    public void testEditDescription() {
        String testDescriptionText = "Test description";

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.name("description")).sendKeys(testDescriptionText);

        getDriver().findElement(By.name("Submit")).click();

        String actualDescriptionText = getDriver().findElement(By.xpath
                ("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualDescriptionText, testDescriptionText);
    }
}



