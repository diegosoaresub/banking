package com.bank.controller;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.dto.AccountDto;
import com.bank.dto.WireTransferDto;
import com.bank.exception.BusinessException;
import com.bank.exception.BusinessResponse;
import com.bank.model.Account;
import com.bank.service.AccountService;


/**
 * Created by diego.rodrigues.
 */
@Component
@Path("/accounts/")
public class AccountController {

    @Autowired
    private AccountService service;
    
    @GET
    @Produces("application/json")
    @Path("{number}")
    public AccountDto findByNumber(@PathParam("number") Long number){
        return convertToDto(service.findByNumber(number));
    }

    @POST
    @Consumes("application/json")
	public Response add(AccountDto dto){
    	Account acc = convertToEntity(dto);

		Account newProduct = service.save(acc);
		URI uri = URI.create("accounts/" + newProduct.getNumber());

		return Response.created(uri).build();
	}
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("wireTransfer/")
	public ResponseEntity<BusinessResponse> wireTransfer(WireTransferDto dto){
    	
    	try {
			service.wireTransfer(dto);
		} catch (BusinessException e) {
			BusinessResponse response = new BusinessResponse();
			response.setErrorMessage(e.getError().getMessage());
			response.setErrorCode(e.getError().getError());
			return new ResponseEntity<BusinessResponse>(response, HttpStatus.BAD_REQUEST);
		}
    	
    	return new ResponseEntity<BusinessResponse>(HttpStatus.OK);
	}
    
    public static Account convertToEntity(AccountDto dto){
        return new Account(dto.getNumber(), dto.getName(), dto.getBalance());
    }

    public static AccountDto convertToDto(Account acc){
        if (acc == null)
            return null;
        return new AccountDto(acc.getNumber(), acc.getName(), acc.getBalance());
    }
}
