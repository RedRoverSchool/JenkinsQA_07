package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineDetailsPage extends BasePage {

    public MultibranchPipelineDetailsPage (WebDriver driver) { super(driver); }

    public MultibranchPipelineRenamePage clickRename() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/confirm-rename')]")).click();

        return new MultibranchPipelineRenamePage(getDriver());
    }
}
