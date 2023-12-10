package school.redrover.cucumber;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import school.redrover.model.FreestyleProjectConfigurePage;
import school.redrover.model.FreestyleProjectDetailsPage;
import school.redrover.model.HomePage;
import school.redrover.model.NewItemPage;
import school.redrover.runner.CucumberDriver;
import school.redrover.runner.ProjectUtils;


public class FreestyleSteps {

    private HomePage homePage;
    private NewItemPage newItemPage;

    private FreestyleProjectDetailsPage freestyleProjectDetailsPage;
    private FreestyleProjectConfigurePage freestyleProjectConfigurePage;

    @When("Go to NewJob")
    public void goToNewJob() {
        newItemPage = new HomePage(CucumberDriver.getDriver()).clickNewItem();
    }

    @And("Choose job type as {string}")
    public void setJobType(String jobType) {
        if ("FreestyleProject".equals(jobType)) {
            newItemPage = newItemPage.selectFreestyleProject();
        } else if ("Folder".equals(jobType)) {
            newItemPage = newItemPage.selectItemFolder();
        } else {
            throw new RuntimeException("Project type {%s} does not found.".formatted(jobType));
        }
    }

    @And("Choose job type as Freestyle")
    public void setJobTypeAsFreestyle() {
        newItemPage = newItemPage.selectFreestyleProject();
    }

    @And("Type job name {string}")
    public void enterItemName(String name) {
        newItemPage.typeItemName(name);
    }

    @And("Click Ok and go to config")
    public void clickOkAndGoToConfig() {
        freestyleProjectConfigurePage = newItemPage.clickOk(new FreestyleProjectConfigurePage(CucumberDriver.getDriver()));
    }

    @And("Go home")
    public void goHome() {
        ProjectUtils.get(CucumberDriver.getDriver());
        homePage = new HomePage(CucumberDriver.getDriver());
    }

    @And("Job with name {string} is exists")
    public void checkJobName(String jobName) {
        Assert.assertTrue(homePage.isProjectExist(jobName));
    }

    @And("Save config and go to Freestyle job")
    public void saveConfigAndGoToFreestyleJob() {
        freestyleProjectDetailsPage = freestyleProjectConfigurePage
                .clickSaveButton();
    }

    @Then("Freestyle job name is {string}")
    public void assertFreestyleJobName(String jobName) {
        Assert.assertEquals(freestyleProjectDetailsPage.getProjectName(), jobName);
    }

    @When("Click Freestyle job {string}")
    public void clickFreestyleJob(String jobName) {
        freestyleProjectDetailsPage = new HomePage(CucumberDriver.getDriver())
                .clickJobByName(jobName, new FreestyleProjectDetailsPage(CucumberDriver.getDriver()));
    }

    @And("Click Freestyle configure")
    public void clickFreestyleConfigure() {
        freestyleProjectConfigurePage = freestyleProjectDetailsPage.clickConfigure();
    }

    @And("Type Freestyle job description as {string}")
    public void setFreestyleJobDescription(String jobDescription) {
        freestyleProjectConfigurePage.inputProjectDescription(jobDescription);
    }

    @Then("Job description is {string}")
    public void assertFreestyleJobDescription(String jobDescription) {
        Assert.assertEquals(freestyleProjectDetailsPage.getDescriptionText(), jobDescription);
    }
}
