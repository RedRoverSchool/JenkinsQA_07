package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
public class ArSaFirstTest extends BaseTest {


    String URL = "https://www.selenium.dev/selenium/web/web-form.html";
  

    //Check if Title of the Website is correct..
    @Test
    public void testTitleCheck()
    {
        getDriver().get(URL);
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Web form");
    }

    //Check if the form "Text Input" is working, try to input some text, check the input text.
    @Test
    public void testTextInput()
    {
        getDriver().get(URL);
        WebElement nameInput = getDriver().findElement(By.id("my-text-id"));
        nameInput.sendKeys("Artur Sabanadze");
        String enteredText = nameInput.getAttribute("value");
        Assert.assertEquals(enteredText, "Artur Sabanadze");
    }

    //Check "my-disabled" element for the following functions: is disabled, is visible and the placeholder text is correct.
    @Test
    public void testDisabledTextInput()
    {
        getDriver().get(URL);
        WebElement disabledInput = getDriver().findElement(By.name("my-disabled"));
        Assert.assertEquals(disabledInput.getAttribute("disabled"), "true");
        Assert.assertTrue(disabledInput.isDisplayed());
        String placeholderText = disabledInput.getAttribute("placeholder");
        Assert.assertEquals(placeholderText, "Disabled input");
    }


    //Check the color of "my-colors" element
    @Test
    public void testColorCheck()
    {
        getDriver().get(URL);
        WebElement colorInputElement = getDriver().findElement(By.name("my-colors"));
        String actualColor = colorInputElement.getAttribute("value");
        String expectedColor = "#563d7c";
        Assert.assertEquals(actualColor, expectedColor, "Element color is not as expected");
    }
}