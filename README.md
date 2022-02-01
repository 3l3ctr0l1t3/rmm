# About The Project

##Technologies:

- Java 11
- Spring Boot 2.7.0
- Docker
- PostgresSQL 13
- Postman

#Requirements
- A running POSTGRES database named `rmm` with a clean public schema available
  - If you want to avoid installing postgres and have docker on your computer you can achieve it by executing the following command in a terminal:
    `docker run --name rmm-postgres -e POSTGRES_USER=rmmadmin -e POSTGRES_PASSWORD=rmmpassword -e POSTGRES_DB=rmm -p 5432:5432 -d postgres`
- Download the repo from: https://github.com/3l3ctr0l1t3/rmm.git 
- In the root folder of the downloaded project, execute the command ./gradlew bootRun

#Authentication
- In order to send request to the endpoint in the app is needed to have and authorization JWT passed in each request in the header `Authorization`,
to obtain the token a request to `http://localhost:8080/login`  should be executed, with a json body representing the user and his password like: `{ "email": "customer@company.com", "password": "secure_password"}`
- After executing the logIn Request, token can be found in the response headers under the name `Authorization` 
- At this moment there are 2 customers/users created within the application that can be used to log in:
  -customer@company.com with password `secure_password`
  -customer2@company.com with password `secure_password`
- The attached postman project `ninja_rmm_tech_assesment` allows you to not worry about authentication per request, given that it contains a pre-request script at collection level that where user and password can be set in order to authenticate each request.

#Endpoints
## Devices:
  - `http://localhost:8080/devices` `GET` : This endpoint allows you to list all the devices linked to the authenticated user, no request body is needed, only authentication
  - `http://localhost:8080/devices/{device-id}` `GET` : This endpoint allows you to search for a specific device, the id of the wanted device should be passed as a path param
  - `http://localhost:8080/devices/` `POST` : This endpoint allows the creation of devices for the authenticated user, the request body should be a json describing a device and its system type. Example:
    - `{"systemName": "Customer 1 Devicesdsd 3sdew9", "systemType":{"id":3}}`
  - `http://localhost:8080/devices/{deviceID}` `PUT` : This endpoint allows the customer to change the name or system type, of a specific device. Example:
    - `{"id": 34,"systemName": "megustaelatun","systemType":{"id":1}}`
## Subscriptions:
- I have decided to name subscription the relation between service and customer, in that line, everytime a user has access to a service a new subscription is created.
- `http://localhost:8080/subscriptions/` `GET`: This endpoint lists all the active subscriptions for the customer, only authorization header is needed
- `http://localhost:8080/subscriptions/` `POST`: This endpoint allows the creation of subscriptions for the authenticated user, the body of the request should be a json representation of the service the user wants to subscribe to. Example:
  - `{"id":1}`
- `http://localhost:8080/subscriptions/{subscription-id}` `DELETE`: This method allows the deletion of subscriptions, the  subscription ID is passed as a path param.
##Billing
- `http://localhost:8080/billing/` `GET` : This endpoint allows the user to calculate his current costs depending on his current subscriptions and devices.

#Pricing
Given that the examples showed that a service could have different prices per system type, I think there's a little explanation here needed in order to understand my model.
The price for the service of a specific device is calculated as follows:

The service can have different prices, the default one is represented in the property `price` of the Service Object, but that same service object also contains a list of prices per specific system type, like so, if the service contain a specific price for the system type of the device, that price will be applied otherwise, the default price will be the one used for that service in that device.

However, if a certain service does not apply for a system type(let's say antivirus for linux), a specific price should be created in the service for that system type with the value 0.
