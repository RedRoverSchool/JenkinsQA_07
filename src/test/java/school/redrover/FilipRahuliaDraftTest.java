package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FilipRahuliaDraftTest extends BaseTest {

    @Test
    public void textBox() {

            getDriver().get("https://demoqa.com/elements");
            WebElement textBoxMenu = getDriver().findElement(By.id("item-0"));
            textBoxMenu.click();
            WebElement inputName = getDriver().findElement(By.id("userName"));
            inputName.sendKeys("Filip Rahulia");
            WebElement inputEmail = getDriver().findElement(By.id("userEmail"));
            inputEmail.sendKeys("test@test.com ");
            WebElement inputCurrentAddress = getDriver().findElement(By.id("currentAddress"));
            inputCurrentAddress.sendKeys("Current Address");
            WebElement inputPermanentAddress = getDriver().findElement(By.id("permanentAddress"));
            inputPermanentAddress.sendKeys("Permanent Address");
    }

    @Test
    public void checkBox() {

        getDriver().get("https://demoqa.com/checkbox");

            Assert.assertFalse(getDriver().findElement(By.id("tree-node-home")).isSelected(),
                    "Home checkbox should be UNchecked by default");
            WebElement homeCheckbox = getDriver().findElement(By.cssSelector(".rct-checkbox"));
            homeCheckbox.click();
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-home")).isSelected(),
                    "Home checkbox should be checked");

            WebElement result = getDriver().findElement(By.xpath("//div[@id='result']//span[1]"));
            Assert.assertEquals(result.getText(), "You have selected :",
                    "Result section not displayed");

            String homeToggleClassName = getDriver().findElement(By.xpath("//*[@id='tree-node']/ol/li")).getAttribute("class");
            Assert.assertTrue(homeToggleClassName.contains("rct-node-collapsed"));

            WebElement expandHomeButton = getDriver().findElement(By.xpath("//label[@for='tree-node-home']/../button"));
            expandHomeButton.click();

            homeToggleClassName = getDriver().findElement(By.xpath("//*[@id='tree-node']/ol/li")).getAttribute("class");
            Assert.assertTrue(homeToggleClassName.contains("rct-node-expanded"));

            Assert.assertTrue(getDriver().findElement(By.id("tree-node-desktop")).isSelected(),
                    "Desktop checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-documents")).isSelected(),
                    "Documents checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-downloads")).isSelected(),
                    "Downloads checkbox should be checked");

            WebElement expandDesctop = getDriver().findElement(By.xpath("//label[@for='tree-node-desktop']/../button"));
            expandDesctop.click();
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-notes")).isSelected(),
                    "Notes checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-commands")).isSelected(),
                    "Commands checkbox should be checked");

            WebElement expandDocuments = getDriver().findElement(By.xpath("//label[@for='tree-node-documents']/..//button"));
            expandDocuments.click();
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-workspace")).isSelected(),
                    "WorkSpace checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-office")).isSelected(),
                    "Office checkbox should be checked");

            WebElement expandDownloads = getDriver().findElement(By.xpath("//label[@for='tree-node-downloads']/..//button"));
            expandDownloads.click();
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-wordFile")).isSelected(),
                    "Word File checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-excelFile")).isSelected(),
                    "Excel File checkbox should be checked");
            WebElement workSpace = getDriver().findElement(By.xpath("//label[@for='tree-node-workspace']/..//button"));
            workSpace.click();
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-react")).isSelected(),
                    "React checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-angular")).isSelected(),
                    "Angular File checkbox should be checked");
            Assert.assertTrue(getDriver().findElement(By.id("tree-node-veu")).isSelected(),
                    "Veu checkbox should be checked");


            WebElement expandAllButton = getDriver().findElement(By.cssSelector("button[title='Expand all']"));
            expandAllButton.click();
            homeToggleClassName = getDriver().findElement(By.xpath("//*[@id='tree-node']/ol/li")).getAttribute("class");
            Assert.assertTrue(homeToggleClassName.contains("rct-node-expanded"));
            List<WebElement> actualList = getDriver().findElements(By.cssSelector(".text-success"));
            List<WebElement> expectedList = getDriver().findElements(By.cssSelector(".rct-title"));
            Boolean resultsMatched = actualList.equals(expectedList);
            System.out.println(resultsMatched);
    }
}

