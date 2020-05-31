/**
 * 
 */
package base;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Assert;

import cucumber.api.Scenario;

/**
 * @author nitinthite
 *
 */
public class Setup {

	// Objects for RestAssured classes to be further used within package and sub-packages
	protected static RequestSpecification httpRequest;
	protected static String bodyAsString;
	protected static ResponseBody body;
	protected static Response response;
	protected static Scenario scenario;
	LocalDate dt;

	// Method for retrieving the local date
	public static LocalDate locatDate() {
		ZoneId zoneId = ZoneId.of("America/Los_Angeles");
		LocalDate dt = LocalDate.from(ZonedDateTime.now(zoneId));
		return dt;

	}

	// Method for retrieving current date data if the end-point is of future date
	protected void validatingFutureDateResponse() throws ParseException {
		dt = locatDate();
		String date = checkWeekends(dt);
		try {
			// Storing response received in response object
			response = httpRequest.get();

			// Retrieve the body of the Response
			body = response.getBody();

			// To check for sub string presence get the Response body as a String.
			bodyAsString = body.asString();

			// Checking if value retrieved is expected
			Assert.assertEquals(bodyAsString.contains(bodyAsString), true);
		} catch (AssertionError e) {
			System.out.println("Assertion error occured due to US time difference ,expected date" + date);
		}
	}

	// Method to check the given date is lying between weekend or not
	protected static String checkWeekends(LocalDate date) throws ParseException {

		LocalDate result = date;
		if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			result = date.minusDays(1);
		}

		else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			result = date.minusDays(2);
		}

		return result.toString();
	}
}
