package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


import java.util.List;

public class AvailableUpdatesTest extends BaseTest {
    private WebElement getManageJenkinsElement() {
        List<WebElement> tasks = getDriver().findElements(By.xpath("//div[@id='tasks']/div/span"));
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getText().equalsIgnoreCase("Manage Jenkins")) {
                return tasks.get(i);
            }
        }
        return null;
    }

    private String getFirstFourWordsFromElement() {
        List<WebElement> newVersionAvailable = getDriver().findElements(By.xpath("//div[@id='main-panel']/section/div"));
        for (int i = 0; i < newVersionAvailable.size(); i++) {
            String text = newVersionAvailable.get(i).getText().replace("\n", " ");
            if (text.equalsIgnoreCase("New version of Jenkins (2.414.3) is available for download (changelog). Or Upgrade Automatically")) {
                String[] words = text.split(" ");

                StringBuilder firstFourWords = new StringBuilder();
                for (int j = 0; j < 4 && j < words.length; j++) {
                    firstFourWords.append(words[j]);
                    if (j < 3) {
                        firstFourWords.append(" ");
                    }
                }

                return firstFourWords.toString();
            }
        }

        return null;
    }


    @Test

    public void testAvailableNewVersion() {
        String expectedResult = "New version of Jenkins";

        getManageJenkinsElement().click();
        String actualResult = getFirstFourWordsFromElement();

        Assert.assertEquals(actualResult, expectedResult);

    }
}
