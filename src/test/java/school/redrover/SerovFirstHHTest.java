package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


    public class SerovFirstHHTest {
        @Test
        public void  testHHFunctions() throws InterruptedException {

            WebDriver driver = new ChromeDriver();
            driver.get("https://hh.ru/");

            try {
                WebElement getHelpButton = driver.findElement(By.xpath("//button[@class='supernova-link-switch' and @data-qa='mainmenu_help']"));
                getHelpButton.click();

                WebElement buttonQAndA = driver.findElement(By.xpath("//a[@class='supernova-dropdown-option' and @data-qa='mainmenu_writeToUs']"));
                buttonQAndA.click();

                driver.get("https://feedback.hh.ru/knowledge-base/articles/applicant?utm_source=hh.ru&utm_medium=referral&utm_campaign=from_header_new");

                WebElement buttonBaseKnowledge = driver.findElement(By.xpath("//a[@href='https://feedback.hh.ru/knowledge-base' and @target='_self']"));
                buttonBaseKnowledge.click();

                WebElement searchBaseKnowledge = driver.findElement(By.xpath("//input[@type='text' and @placeholder='Поиск по базе знаний']"));
                searchBaseKnowledge.clear();
                searchBaseKnowledge.sendKeys("Test");

                Thread.sleep(5000);

            } finally {
                driver.quit();
            }
        }
    }

