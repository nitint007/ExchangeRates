/**
 * 
 */
package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author nitinthite
 *
 */
/*
 * PLZ read "https://docs.cucumber.io/cucumber/api/#running-cucumber" for more
 * details on cucumber options
 * 
 * Available tags in suite : @LatestDateRates, @UAT, @PastConversionRates
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/html/",
		"html:target/cucumber-reports"}, 
features = "src/test/resources/features", 
tags = {" @UAT" }, 
glue = "stepDefinitions",
monochrome = true, 
strict = false)

/*
 * The Entry point which triggers the automation suite selected
 */
public class TestRunner {

}
