package school.redrover.model.jobs.details;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.jobs.configs.FolderConfigurationPage;
import school.redrover.model.NewItemPage;
import school.redrover.model.base.BaseDetailsPage;

import java.util.List;

public class FolderDetailsPage extends BaseDetailsPage<FolderConfigurationPage, FolderDetailsPage> {

    @FindBy(xpath = "//a[contains(@href, '/newJob')]")
    private WebElement newItemButton;

    @FindBy(xpath = "//a[contains(@href,'move')]")
    private WebElement moveJob;

    @FindBy(xpath = "//a[@class='content-block__link']")
    private WebElement createJob;

    @FindBy(xpath = "//a[contains(@class, 'jenkins-table__link')]")
    private List<WebElement> jobsList;

    @FindBy(xpath = "//a[contains(@class, 'jenkins-table__link')]")
    private WebElement newProject;

    public FolderDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FolderConfigurationPage createConfigurationPage() {
        return new FolderConfigurationPage(getDriver());
    }

    public NewItemPage clickCreateJob() {
        createJob.click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemButton() {
        newItemButton.click();

        return new NewItemPage(getDriver());
    }

    public List<String> getJobListInsideFolder() {
        return jobsList.stream().map(WebElement::getText).toList();
    }

    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + name.replace(" ", "%20") + "/']")).click();

        return page;
    }

    public boolean isJobInJobsList(String jobName) {

        return getJobListInsideFolder().contains(jobName);
    }

    public NewItemPage clickCreateAJob(){
        newItemButton.click();
        return new  NewItemPage(getDriver());
    }

    public boolean   isNewCreatedProjectDisplayed(){
        return  newProject.isDisplayed();
    }
}


