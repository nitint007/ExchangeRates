@LatestDateRates @UAT 
Feature: Get latest date data from Foreign Exchange Rates API 
	Description: Validate if API is fit for purpose in the use of the exchange rate for financial reasons

#Background: 
#	Given Foreign Exchange Rates API is accessible 
	
Scenario Outline: 
	When Hit the API with end point as "<endPoint>" 
	Then Should respond with status code as "<statusCode>"
	
	Examples: 
		| endPoint                    | statusCode |
		| /latest                     |     200     |
		| /latest?symbols=USD,GBP     |     200     |
		| /latest?base=USD            |     200     |
		| /latest?base=USD&symbols=GBP|     200     |
