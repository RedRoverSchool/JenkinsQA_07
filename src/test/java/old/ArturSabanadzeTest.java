package old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class ArturSabanadzeTest {

    //Check if Title of the Website is correct.
    @Test
    public void titeleCheck()
    {

        WebDriver driver = new ChromeDriver();
       try
       {
           driver.get("https://www.selenium.dev/selenium/web/web-form.html");

           String title = driver.getTitle();
           Assert.assertEquals(title, "Web form");
       }
       finally {
           driver.quit();
       }
    }
    //Check if the form "Text Input" is working, try to input some text, check the input text.
    @Test
    public void textInputTest() {

        WebDriver driver = new ChromeDriver();
        try{
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            WebElement nameInput = driver.findElement(By.id("my-text-id"));
            nameInput.sendKeys("Artur Sabanadze");

            String enteredText = nameInput.getAttribute("value");
            Assert.assertEquals(enteredText, "Artur Sabanadze");
        }
        finally {
            driver.quit();
        }
    }

    //Check "my-disabled" element for the following functions: is disabled, is visible and the placeholder text is correct.
    @Test
    public void disabledTextInputTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            WebElement disabledInput = driver.findElement(By.name("my-disabled"));

            // Check if the input is disabled
            Assert.assertEquals(disabledInput.getAttribute("disabled"), "true");

            //Check if the element is visible
            Assert.assertTrue(disabledInput.isDisplayed());

            // Check the placeholder text in the disabled input
            String placeholderText = disabledInput.getAttribute("placeholder");
            Assert.assertEquals(placeholderText, "Disabled input");

        } finally {
            driver.quit();
        }
    }


    @Test
    public void colorCheck() {
        WebDriver driver = new ChromeDriver();
        try {

            driver.get("https://www.selenium.dev/selenium/web/web-form.html");


            WebElement colorInputElement = driver.findElement(By.name("my-colors"));


            String actualColor = colorInputElement.getAttribute("value");


            String expectedColor = "#563d7c";


            Assert.assertEquals(actualColor, expectedColor, "Element color is not as expected");
        }
        finally {
            driver.quit();
        }
    }
}
