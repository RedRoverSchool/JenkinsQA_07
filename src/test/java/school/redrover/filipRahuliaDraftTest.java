package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

@Ignore
public class filipRahuliaDraftTest {

    @Test
    public void textBox() {


        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://demoqa.com/elements");
            WebElement textBoxMenu = driver.findElement(By.id("item-0"));
            textBoxMenu.click();
            WebElement inputName = driver.findElement(By.id("userName"));
            inputName.sendKeys("Filip Rahulia");
            WebElement inputEmail = driver.findElement(By.id("userEmail"));
            inputEmail.sendKeys("test@test.com ");
            WebElement inputCurrentAddress = driver.findElement(By.id("currentAddress"));
            inputCurrentAddress.sendKeys("Current Address");
            WebElement inputPermanentAddress = driver.findElement(By.id("permanentAddress"));
            inputPermanentAddress.sendKeys("Permanent Address");
        } finally {
            driver.quit();
        }

    }

    @Test
    public void checkBox() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://demoqa.com/checkbox");

            Assert.assertFalse(driver.findElement(By.id("tree-node-home")).isSelected(),
                    "Home checkbox should be UNchecked by default");
            WebElement homeCheckbox = driver.findElement(By.cssSelector(".rct-checkbox"));
            homeCheckbox.click();
            Assert.assertTrue(driver.findElement(By.id("tree-node-home")).isSelected(),
                    "Home checkbox should be checked");

            WebElement result = driver.findElement(By.xpath("//div[@id='result']//span[1]"));
            Assert.assertEquals(result.getText(), "You have selected :",
                    "Result section not displayed");

            String homeToggleClassName = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li")).getAttribute("class");
            Assert.assertTrue(homeToggleClassName.contains("rct-node-collapsed"));

            WebElement expandHomeButton = driver.findElement(By.xpath("//label[@for='tree-node-home']/../button"));
            expandHomeButton.click();

            homeToggleClassName = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li")).getAttribute("class");
            Assert.assertTrue(homeToggleClassName.contains("rct-node-expanded"));

            Assert.assertTrue(driver.findElement(By.id("tree-node-desktop")).isSelected(),
                    "Desktop checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-documents")).isSelected(),
                    "Documents checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-downloads")).isSelected(),
                    "Downloads checkbox should be checked");

            WebElement expandDesctop = driver.findElement(By.xpath("//label[@for='tree-node-desktop']/../button"));
            expandDesctop.click();
            Assert.assertTrue(driver.findElement(By.id("tree-node-notes")).isSelected(),
                    "Notes checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-commands")).isSelected(),
                    "Commands checkbox should be checked");


            WebElement expandDocuments = driver.findElement(By.xpath("//label[@for='tree-node-documents']/..//button"));
            expandDocuments.click();
            Assert.assertTrue(driver.findElement(By.id("tree-node-workspace")).isSelected(),
                    "WorkSpace checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-office")).isSelected(),
                    "Office checkbox should be checked");

            WebElement expandDownloads = driver.findElement(By.xpath("//label[@for='tree-node-downloads']/..//button"));
            expandDownloads.click();
            Assert.assertTrue(driver.findElement(By.id("tree-node-wordFile")).isSelected(),
                    "Word File checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-excelFile")).isSelected(),
                    "Excel File checkbox should be checked");
            WebElement workSpace = driver.findElement(By.xpath("//label[@for='tree-node-workspace']/..//button"));
            workSpace.click();
            Assert.assertTrue(driver.findElement(By.id("tree-node-react")).isSelected(),
                    "React checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-angular")).isSelected(),
                    "Angular File checkbox should be checked");
            Assert.assertTrue(driver.findElement(By.id("tree-node-veu")).isSelected(),
                    "Veu checkbox should be checked");


            WebElement expandAllButton = driver.findElement(By.cssSelector("button[title='Expand all']"));
            expandAllButton.click();
            homeToggleClassName = driver.findElement(By.xpath("//*[@id='tree-node']/ol/li")).getAttribute("class");
            Assert.assertTrue(homeToggleClassName.contains("rct-node-expanded"));
            List<WebElement> actualList = driver.findElements(By.cssSelector(".text-success"));
            List<WebElement> expectedList = driver.findElements(By.cssSelector(".rct-title"));
            Boolean resultsMatched = actualList.equals(expectedList);
            System.out.println(resultsMatched);
        } finally {
            driver.quit();
        }
    }
}

