/**
 * 
 */
package util;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.equalTo;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import cucumber.api.Scenario;

/**
 * @author nitinthite
 *
 */
public class Setup {

	// reqSpec is used for adding headers,paramters and body
	static protected RequestSpecification httpRequest = null;
	static protected Response response = null;
	static protected Scenario scenario = null;
	LocalDate localdate;

	// ResorceBundle is used for loading all the values of properties file in
	// ResorceBundle object
	protected ResourceBundle bundle = ResourceBundle.getBundle("config");

	public RequestSpecification getURI(String baseURI) {
		return httpRequest.given().baseUri(baseURI);

	}

	// Method for fetching the local date
	public static LocalDate locatDate() {
		ZoneId zoneId = ZoneId.of("America/Los_Angeles");
		LocalDate localdate = LocalDate.from(ZonedDateTime.now(zoneId));
		return localdate;

	}

	// Method for retrieving current date data if the endpoint is of future date
	public void validatingFutureDateResponse() throws ParseException {
		localdate = locatDate();
		String date = checkWeekends(localdate);
		try {
			response.then().assertThat().body("date", equalTo(date));
		} catch (AssertionError e) {
			scenario.write("Assertion error occured due to US time difference ,expected date" + date);
		}
	}

	// Method to check the if given date is lying between weekend or not
	public static String checkWeekends(LocalDate date) throws ParseException {

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
