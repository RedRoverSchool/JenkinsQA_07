package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineConfigurationPage extends BasePage {

    @FindBy(xpath = "//a[@class='model-link'][contains(@href, 'job')]")
    private WebElement breadcrumbJobName;

    public MultibranchPipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public String getJobName() {

        return breadcrumbJobName.getText();
    }

}
