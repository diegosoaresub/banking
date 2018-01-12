#Banking Scenario


*Diego Rodrigues - diegosoaresub@gmail.com*


####Frameworks and Tools

- Spring Boot
- Spring Data
- Jersey (JAX-RS)
- Hibernate
- Rest Assured
- HSQLDB
- Maven

####Maven Commands


#####Building the jar application
`mvn package`

#####Running the application
`mvn spring-boot:run`

#####Running the tests
`mvn integration-test -P integration`


####Available Restful Services:

The services follows the Restful pattern using JSON for data representation.

**Account Services**: the path `/accounts/` represents path for account entity. The following operations are available:

- Create: `POST` method with the entity Account as JSON object.
- Find by account: `GET` method with the number account as path parameter. (`/accounts/{accountNumber}`)
- Wire Transfer: `POST` method with the entity WireTransfer as JSON object.

####Tests:
- Create Account: create an account and check the created object.
- Wire Transfer: transfer money between two account and check the balances.
- Invalid source account on wire transfer: try to transfer an amount between two account but with an invalid account number.
- Insufficient balance: try to transfer an amount but the source account doesn't have enough balance.

#####Observations
The system is be able to handle simultaneous transfer requests through concurrency control provided by @Transaction annotation. The concurrency access on the same account is assured through the @Lock annotation passing PESSIMISTIC_WRITE as parameter that works as FOR UPDATE sql clause.
