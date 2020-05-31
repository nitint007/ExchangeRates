/**
 * The class describes methods of feature file scenarios i.e. implementation details
 */
package stepDefinitions;

import org.junit.Assert;
import base.Setup;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;

/**
 * @author nitinthite
 */
public class Acceptance extends Setup {

	// REST API Under test
	protected static final String BASE_URI = "https://api.ratesapi.io/api/latest";

	// protected RequestSpecification httpRequest;
	private int actualStatusCode;

	/*
	 * Precondition for all scenarios - Will run at start of each scenarios active for execution
	 */
	@Given("^Foreign Exchange Rates API is accessible$")
	public void apiAccessible() throws Throwable {
		System.out.println("API under test: " + BASE_URI);

		// Specifying URI to test
		RestAssured.baseURI = BASE_URI;
		httpRequest = RestAssured.given();
		
		// Storing response received in response object
		response = httpRequest.get();

		// Extracting status code to verify
		actualStatusCode = response.getStatusCode();

		// Checking if value retrieved is expected
		Assert.assertEquals(200, actualStatusCode);

		System.out.println("API response recevied : " + response.asString());
	}

	/*
	 * Method to hit API end-point/s
	 */
	@When("^Hit the API with end point as \"([^\"]*)\"$")
	public void hitEndpoint(String endPoint) throws Throwable {
		System.out.println("Hiting API with specific endpoint " + endPoint + " and getting response");

		// Specifying URI to test with end-point
		RestAssured.baseURI = BASE_URI+endPoint;
		httpRequest = RestAssured.given();

		System.out.println("URI hit with endpoint is : " + RestAssured.baseURI);
	}

	/*
	 * Validating API responses
	 */
	@Then("^Should respond with status code as (\\d+)$")
	public void getStatusCode(int expectedStatusCode) throws Throwable {
		// Extracting status code to verify
		actualStatusCode = response.getStatusCode();

		// Checking if value retrieved is as expected
		Assert.assertEquals(expectedStatusCode, actualStatusCode);

		// response.then().assertThat().statusCode(expectedStatusCode);
		System.out.println("Response code recieved : " + expectedStatusCode);
	}

	@Then("^API should respond with base value \"([^\"]*)\"$")
	public void getBaseValue(String expectedBaseValue) throws Throwable {
		System.out.println("*************** Verifying base values from Response **********");

		// Specifying URI to test
		RestAssured.baseURI = BASE_URI;
		httpRequest = RestAssured.given();

		// Getting response through get method
		response = httpRequest.get();

		// Retrieve the body of the Response
		body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		bodyAsString = body.asString();

		// Checking if value retrieved is as expected
		Assert.assertEquals(bodyAsString.contains(expectedBaseValue), true);
		System.out.println("Base Value Received : " + expectedBaseValue);
	}

	@Then("^Error message should be displayed as \"([^\"]*)\"$")
	public void verifyErrorMessage(String expectedErrorMessage) throws Throwable {
		System.out.println("Verifying error message for incorrect/incomplete endpoint");

		// Retrieve the body of the Response
		body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		bodyAsString = body.asString();

		// Checking if value retrieved is expected
		Assert.assertEquals(bodyAsString.contains(expectedErrorMessage), true);

		// response.then().assertThat().body("error", equalTo(expectedErrorMessage));
		System.out.println("Error message received : " + expectedErrorMessage);
	}

	@Then("^API should return the current date rates$")
	public void getCurrentDateRates() throws Throwable {
		System.out.println("Verifying current date data should return although the endpoint is of future date");

		// Method for retrieving current date data if the end-point is of future date
		validatingFutureDateResponse();
	}
}
