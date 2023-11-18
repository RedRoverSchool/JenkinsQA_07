package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;
import org.openqa.selenium.WebElement

import java.util.ArrayList;
import java.util.List;


public class MultibranchPipelineDetailsPage extends BasePage {
    
  public MultibranchPipelineDetailsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTasksText() {
        WebElement parentElement = getDriver().findElement(By.xpath("//div[@id='tasks']"));
        List<WebElement> childElements = parentElement.findElements(By.xpath("./*"));

        List<String> list = new ArrayList<>();
        for (int i = 1; i <= childElements.size(); i++) {
            list.add(getDriver().findElement(By.xpath("//div[@id='tasks']/div[" + i + "]")).getText());
        }

        return list;
    }

    public MultibranchPipelineRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new MultibranchPipelineRenamePage(getDriver());
    }
}
