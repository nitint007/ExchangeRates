@PastConversionRates @UAT
Feature: To get past conversion rates from Foreign Exchange Rates API 
	Description: Validate if API is fit for purpose in the use of past conversion rates for financial reasons

Background: 
	Given Foreign Exchange Rates API is accessible 
	
Scenario: Assert the correct response with latest date data 
	When Hit the API with end point as "/latest?base=USD" 
	Then API should respond with base value "USD" 
	
Scenario: Validate results on providing incorrect/incomplete endpoint 
	When Hit the API with end point as "latest?base=XXX" 
	Then Should respond with status code as 400 
	And Error message should be displayed as "Base 'XXX' is not supported." 
	
Scenario: To assert the success status of past conversion rates response 
	When Hit the API with end point as "/2010-01-12" 
	Then Should respond with status code as 200 
	
Scenario: Assert the correct response - content of the past conversion rates response 
	When Hit the API with end point as "/2010-01-12?base=INR" 
	Then API should respond with base value "INR" 
	
Scenario: Hit the API with future date and validate current date data should present 
	When Hit the API with end point as "/2021-05-12" 
	Then API should return the current date rates