package com.cece.cbs.endpoint;

import com.credable.soap.kyc.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.github.javafaker.Faker;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@Endpoint
@Slf4j
public class CustomerEndpoint {

    private static final String NAMESPACE = "http://credable.io/cbs/customer";
    private final Faker faker = new Faker();
    private final List<String> testNumbers = Arrays.asList(
            "234774784",
            "318411216",
            "340397370",
            "366585630",
            "397178638"
    );

    @PayloadRoot(namespace = NAMESPACE, localPart = "CustomerRequest")
    @ResponsePayload
    public CustomerResponse getCustomer(@RequestPayload CustomerRequest request) throws DatatypeConfigurationException {
        log.info("Get Customer request received for customer number: {}", request.getCustomerNumber());

        CustomerResponse response = new CustomerResponse();

        if (!testNumbers.contains(request.getCustomerNumber())) {
            response.setCustomer(null);
            return response;
        }

        Customer customer = new Customer();
        customer.setCreatedAt(getDate(faker.number().numberBetween(1, 2000)));
        customer.setCreatedDate(getDate(faker.number().numberBetween(1, 2000)));
        customer.setCustomerNumber(request.getCustomerNumber());
        customer.setDob(getDate(faker.number().numberBetween(6600, 25000)));
        customer.setEmail(faker.internet().emailAddress());
        customer.setFirstName(faker.name().firstName());
        customer.setGender(faker.options().option(Gender.values()));
        customer.setId(faker.number().randomNumber());
        customer.setIdNumber(faker.idNumber().valid());
        customer.setIdType(faker.options().option(IdType.values()));
        customer.setLastName(faker.name().lastName());
        customer.setMiddleName(faker.name().lastName());
        customer.setMobile(faker.phoneNumber().cellPhone());
        customer.setMonthlyIncome(faker.number().randomDouble(2, 1000, 10000));
        customer.setStatus(faker.options().option(Status.values()));
        customer.setUpdatedAt(getDate(faker.number().numberBetween(1, 1000)));

        response.setCustomer(customer);
        return response;
    }

    private XMLGregorianCalendar getDate(int days) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -days);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}

