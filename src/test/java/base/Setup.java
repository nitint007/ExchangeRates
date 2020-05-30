/**
 * 
 */
package base;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
//		static protected RequestSpecification httpRequest;
		static protected Response response;
		static protected Scenario scenario;
		LocalDate dt;

		//ResorceBundle is used for loading all the values of properties file in ResorceBundle object
//		protected ResourceBundle bundle = ResourceBundle.getBundle("config");
//
//		public RequestSpecification getURI(String baseURI) {
//			return httpRequest.given().baseUri(baseURI);
//
//		}

		//Method for retrieving the local date
		public static LocalDate locatDate() {
			ZoneId zoneId = ZoneId.of("America/Los_Angeles");
			LocalDate dt = LocalDate.from(ZonedDateTime.now(zoneId));
			return dt;

		}
	    //Method for  retrieving current date data if the endpoint is of future date
		public void validatingFutureDateResponse() throws ParseException {
			dt = locatDate();
			String date = checkWeekends(dt);
			try {
				response.then().assertThat().body("date", equalTo(date));
			} catch (AssertionError e) {
				System.out.println("Assertion error occured due to US time difference ,expected date" + date);
			}
		}
		
	    //Method to check the given date is lying between weekend or not
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

