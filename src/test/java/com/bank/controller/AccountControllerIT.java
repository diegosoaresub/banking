package com.bank.controller;

import static com.jayway.restassured.RestAssured.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.hamcrest.Matchers.equalTo;

import com.bank.application.ExamApplication;
import com.bank.dto.AccountDto;
import com.bank.dto.WireTransferDto;
import com.bank.exception.ErrorCode;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

/**
 * Created by diego.rodrigues
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExamApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AccountControllerIT {

    private static final String ACCOUNTS_RESOURCE = "/accounts/";

    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
    }

    /**
     * Create an account
     */
    @Test
    public void addAccountShouldReturnCreated(){
        
    	//Create the account
    	String location =    	
		    	given()
		            .body(new AccountDto("Diego", 170.00))
		            .contentType(ContentType.JSON)
		        .expect()
		        	.statusCode(HttpStatus.SC_CREATED)
		        .when()
		            .post(ACCOUNTS_RESOURCE)
		        .then()
		            .extract()
		            	.header("Location");
    	
    	System.out.println("Location: " + location);
    	
    	String id = StringUtils.substringAfter(location, ACCOUNTS_RESOURCE);
    	
    	System.out.println("ID: : " + id);
    	
    	//Get the account and check if the name is the same
    	when().
    		get(ACCOUNTS_RESOURCE + id)
    	.then()
    		.statusCode(HttpStatus.SC_OK)
    		.body("name", equalTo("Diego"));
    	
    }
    
    /**
     * Create two accounts and wire transfer 50.00 with success
     */
    @Test
    public void wireTransfer(){
    
    	//Create the source account
    	String sourceAccountLocation =    	
		    	given()
		            .body(new AccountDto("Diego", 170.00))
		            .contentType(ContentType.JSON)
		        .expect()
		        	.statusCode(HttpStatus.SC_CREATED)
		        .when()
		            .post(ACCOUNTS_RESOURCE)
		        .then()
		            .extract()
		            	.header("Location");
    	
    	String sourceNumber = StringUtils.substringAfter(sourceAccountLocation, ACCOUNTS_RESOURCE);
    	System.out.println("Source Account Number: : " + sourceNumber);
    	
    	//Create the target account
    	String targetAccountLocation =    	
		    	given()
		            .body(new AccountDto("Jose", 100.00))
		            .contentType(ContentType.JSON)
		        .expect()
		        	.statusCode(HttpStatus.SC_CREATED)
		        .when()
		            .post(ACCOUNTS_RESOURCE)
		        .then()
		            .extract()
		            	.header("Location");
    	
    	String targetNumber = StringUtils.substringAfter(targetAccountLocation, ACCOUNTS_RESOURCE);
    	System.out.println("Target Account Number: : " + targetNumber);    	
    	
    	
    	//Transfer the value
    	WireTransferDto transfer = new WireTransferDto(Long.parseLong(sourceNumber), Long.parseLong(targetNumber), 50.00);
    	
    	given()
    		.body(transfer)
    		.contentType(ContentType.JSON)
	    .when()
	        .post(ACCOUNTS_RESOURCE + "wireTransfer/")
	    .then()
	    	.statusCode(HttpStatus.SC_OK)
    		.body("statusCode", equalTo("OK"));
    	
    	//Check the source account balance
    	when().
    		get(ACCOUNTS_RESOURCE + sourceNumber)
    	.then()
    		.statusCode(HttpStatus.SC_OK)
    		.body("balance", equalTo(170.0f - 50.0f));
    	
    	//Check the target account balance
    	when().
    		get(ACCOUNTS_RESOURCE + targetNumber)
    	.then()
    		.statusCode(HttpStatus.SC_OK)
    		.body("balance", equalTo(100.0f + 50.0f));
    	
    }
    
    /**
     * Try to transfer a value greater than the source account balance
     */
    @Test
    public void insufficientBalanceWireTransfer(){
    	//Create the source account
    	String sourceAccountLocation =    	
		    	given()
		            .body(new AccountDto("Diego", 170.00))
		            .contentType(ContentType.JSON)
		        .expect()
		        	.statusCode(HttpStatus.SC_CREATED)
		        .when()
		            .post(ACCOUNTS_RESOURCE)
		        .then()
		            .extract()
		            	.header("Location");
    	
    	String sourceNumber = StringUtils.substringAfter(sourceAccountLocation, ACCOUNTS_RESOURCE);
    	System.out.println("Source Account Number: : " + sourceNumber);
    	
    	//Create the target account
    	String targetAccountLocation =    	
		    	given()
		            .body(new AccountDto("Jose", 100.00))
		            .contentType(ContentType.JSON)
		        .expect()
		        	.statusCode(HttpStatus.SC_CREATED)
		        .when()
		            .post(ACCOUNTS_RESOURCE)
		        .then()
		            .extract()
		            	.header("Location");
    	
    	String targetNumber = StringUtils.substringAfter(targetAccountLocation, ACCOUNTS_RESOURCE);
    	System.out.println("Target Account Number: : " + targetNumber);  
    	
    	//Transfer a value greater than the source account balance
    	WireTransferDto transfer = new WireTransferDto(Long.parseLong(sourceNumber), Long.parseLong(targetNumber), 10000.00);
    	
    	given()
    		.body(transfer)
    		.contentType(ContentType.JSON)
	    .when()
	        .post(ACCOUNTS_RESOURCE + "wireTransfer/")
	    .then()
	    	.statusCode(HttpStatus.SC_OK)
    		.body("body.errorCode", equalTo(ErrorCode.INSUFFICIENT_BALANCE.getError()))
    		.body("statusCode", equalTo("BAD_REQUEST"));
    }
    
    /**
     * Try to transfer with a invalid account number
     */
    @Test
    public void invalidSourceAccount(){
    	//Transfer the value
    	WireTransferDto transfer = new WireTransferDto(999L, 777L, 10000.00);
    	
    	given()
    		.body(transfer)
    		.contentType(ContentType.JSON)
	    .when()
	        .post(ACCOUNTS_RESOURCE + "wireTransfer/")
	    .then()
	    	.statusCode(HttpStatus.SC_OK)
    		.body("body.errorCode", equalTo(ErrorCode.INVALID_SOURCE_ACC.getError()))
    		.body("statusCode", equalTo("BAD_REQUEST"));
    }
}
