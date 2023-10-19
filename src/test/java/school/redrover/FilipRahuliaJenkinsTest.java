package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FilipRahuliaJenkinsTest extends BaseTest {

    @Test
    public void testEditDescription() {
        getDriver().get("http://localhost:8080/");
        String testDescriptionText = "Test description";

        WebElement addDescriptionButton = getDriver().findElement(By.id("description-link"));
        addDescriptionButton.click();

        WebElement descriptionTextArea = getDriver().findElement(By.name("description"));
        descriptionTextArea.sendKeys(testDescriptionText);

        getDriver().findElement(By.name("Submit")).click();

        String actualDescriptionText = getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText();

        Assert.assertEquals(actualDescriptionText, testDescriptionText);
    }
}



