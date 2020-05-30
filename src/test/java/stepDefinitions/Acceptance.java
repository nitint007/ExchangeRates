/**
 * 
 */
package stepDefinitions;

import org.junit.Assert;

import base.Setup;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

/**
 * @author nitinthite
 *
 */
public class Acceptance extends Setup {

	// REST API Under test
	private static final String BASE_URI = "https://api.ratesapi.io/api";

	static protected RequestSpecification httpRequest;
	static protected ResponseBody body;
	private String bodyAsString;
	private int actualStatusCode;

	/*
	 * Precondition for all scenarios
	 */
	@Given("^Foreign Exchange Rates API is accessible$")
	public void apiAccessible() throws Throwable {
		System.out.println("API under test: " + BASE_URI);

		// Specifying URI to test
		RestAssured.baseURI = BASE_URI;
		httpRequest = RestAssured.given();

		// Extracting status code to verify
		actualStatusCode = response.getStatusCode();

		// Checking if value retrived is expected
		Assert.assertEquals(actualStatusCode, 200);
		System.out.println("API response code " + actualStatusCode);
	}

	/*
	 * Method to hit API endpoint
	 */
	@When("^Hit the API with end point as \"([^\"]*)\"$")
	public void hitEndpoint(String endPoint) throws Throwable {
		System.out.println("Hiting API with specific endpoint " + endPoint + " and getting response");

		// Getting response through get method
		response = httpRequest.get(endPoint);
		System.out.println("Response is " + response);
	}

	/*
	 * Validating API responses
	 */
	@Then("^Should respond with status code as (\\d+)$")
	public void getStatusCode(int expectedStatusCode) throws Throwable {
		// Extracting status code to verify
		int actualStatusCode = response.getStatusCode();

		// Checking if value retrived is expected
		Assert.assertEquals(actualStatusCode, expectedStatusCode);

		// response.then().assertThat().statusCode(expectedStatusCode);
		System.out.println("Reponse code recived " + expectedStatusCode);
	}

	@Then("^Should respond with status code as \"([^\"]*)\"$")
	public void responseCode(String statusCode) throws Throwable {
		// Extracting status code to verify
		response.then().assertThat().statusLine(statusCode);
		System.out.println("Reponse code recived " + statusCode);
	}

	@Then("^API should respond with base value \"([^\"]*)\"$")
	public void getBaseValue(String expectedBaseValue) throws Throwable {
		System.out.println("Verifying base value");

		// Retrieve the body of the Response
		body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		bodyAsString = body.asString();

		// Checking if value retrived is expected
		Assert.assertEquals(bodyAsString.contains(expectedBaseValue), true);
		System.out.println("Base Value Received " + expectedBaseValue);
	}

	@Then("^Error message should be displayed as \"([^\"]*)\"$")
	public void verifyErrorMessage(String expectedErrorMessage) throws Throwable {
		System.out.println("Verifying error message for incorrect/incomplete endpoint");

		// Retrieve the body of the Response
		body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		bodyAsString = body.asString();

		// Checking if value retrived is expected
		Assert.assertEquals(bodyAsString.contains(expectedErrorMessage), true);

		// response.then().assertThat().body("error", equalTo(expectedErrorMessage));
		System.out.println("Error message received" + expectedErrorMessage);
	}

	@Then("^API should return the current date rates$")
	public void getCurrentDateRates() throws Throwable {
		System.out.println("Verifying current date data should return although the endpoint is of future date");

		// function for retrieving current date data if the end-point is of future date
		validatingFutureDateResponse();
	}
}
