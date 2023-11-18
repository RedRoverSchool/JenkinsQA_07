package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

import java.util.ArrayList;
import java.util.List;


public class MultibranchPipelineDetailsPage extends BasePage {
    
  public MultibranchPipelineDetailsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTasksText() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(getDriver().findElement(By.xpath("//div[@id='tasks']/div[" + i + "]")).getText());
        }

        return list;
    }