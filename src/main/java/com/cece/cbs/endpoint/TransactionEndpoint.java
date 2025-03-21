package com.cece.cbs.endpoint;

import com.credable.soap.transactions.TransactionData;
import com.credable.soap.transactions.TransactionsRequest;
import com.credable.soap.transactions.TransactionsResponse;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.GregorianCalendar;


@Endpoint
@Slf4j
public class TransactionEndpoint {

    private static final String NAMESPACE = "http://credable.io/cbs/transaction";
    private final Faker faker = new Faker();

    @PayloadRoot(namespace = NAMESPACE, localPart = "TransactionsRequest")
    @ResponsePayload
    public TransactionsResponse getTransactions(@RequestPayload TransactionsRequest request) throws DatatypeConfigurationException {
        log.info("New Transactions data request {}", request);
        TransactionsResponse response = new TransactionsResponse();

        int count = faker.number().numberBetween(3, 20);
        for(int i=0; i<count; i++) {
            TransactionData data = new TransactionData();
            data.setAccountNumber(faker.finance().iban());
            data.setAlternativechanneltrnscrAmount(faker.number().randomDouble(2, 1000, 100000));
            data.setAlternativechanneltrnscrNumber(faker.number().numberBetween(1, 100));
            data.setAlternativechanneltrnsdebitAmount(faker.number().randomDouble(2, 1000, 50000));
            data.setAlternativechanneltrnsdebitNumber(faker.number().numberBetween(1, 50));
            data.setAtmTransactionsNumber(faker.number().numberBetween(0, 20));
            data.setAtmtransactionsAmount(faker.number().randomDouble(2, 0, 10000));
            data.setBouncedChequesDebitNumber(faker.number().numberBetween(0, 5));
            data.setBouncedchequescreditNumber(faker.number().numberBetween(0, 5));
            data.setBouncedchequetransactionscrAmount(faker.number().randomDouble(2, 0, 5000));
            data.setBouncedchequetransactionsdrAmount(faker.number().randomDouble(2, 0, 5000));
            data.setChequeDebitTransactionsAmount(faker.number().randomDouble(2, 1000, 20000));
            data.setChequeDebitTransactionsNumber(faker.number().numberBetween(0, 10));

            GregorianCalendar cal = GregorianCalendar.from(faker.date().past(1000, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()));
            XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            data.setCreatedAt(xmlCal);
            data.setCreatedDate(xmlCal);
            data.setLastTransactionDate(xmlCal);
            data.setUpdatedAt(xmlCal);

            data.setCredittransactionsAmount(faker.number().randomDouble(2, 1000, 100000));
            data.setDebitcardpostransactionsAmount(faker.number().randomDouble(2, 500, 50000));
            data.setDebitcardpostransactionsNumber(faker.number().numberBetween(0, 30));
            data.setFincominglocaltransactioncrAmount(faker.number().randomDouble(2, 100, 10000));
            data.setId(faker.number().randomNumber());
            data.setIncominginternationaltrncrAmount(faker.number().randomDouble(2, 1000, 50000));
            data.setIncominginternationaltrncrNumber(faker.number().numberBetween(0, 10));
            data.setIncominglocaltransactioncrNumber(faker.number().numberBetween(0, 15));
            data.setIntrestAmount(faker.number().numberBetween(0, 2000));

            data.setLastTransactionType(faker.options().option("DEBIT", "CREDIT"));
            data.setLastTransactionValue(faker.number().numberBetween(100, 10000));
            data.setMaxAtmTransactions(faker.number().randomDouble(2, 5000, 10000));
            data.setMaxMonthlyBebitTransactions(faker.number().randomDouble(2, 10000, 50000));
            data.setMaxalternativechanneltrnscr(faker.number().randomDouble(2, 2000, 15000));
            data.setMaxalternativechanneltrnsdebit(faker.number().randomDouble(2, 1000, 10000));
            data.setMaxbouncedchequetransactionscr(faker.number().randomDouble(2, 1000, 5000));
            data.setMaxchequedebittransactions(faker.number().randomDouble(2, 2000, 25000));
            data.setMaxdebitcardpostransactions(faker.number().randomDouble(2, 1000, 15000));

            data.setMonthlyBalance(faker.number().randomDouble(2, 500, 100000));
            data.setMonthlydebittransactionsAmount(faker.number().randomDouble(2, 1000, 50000));
            data.setOverdraftLimit(faker.number().randomDouble(2, 500, 5000));

            data.setTransactionValue(faker.number().randomDouble(2, 100, 10000));

            data.setMobilemoneycredittransactionAmount(faker.number().randomDouble(2, 100, 10000));
            data.setMobilemoneycredittransactionNumber(faker.number().numberBetween(0, 20));
            data.setMobilemoneydebittransactionAmount(faker.number().randomDouble(2, 100, 5000));
            data.setMobilemoneydebittransactionNumber(faker.number().numberBetween(0, 20));
            data.setOutgoinginttransactiondebitAmount(faker.number().randomDouble(2, 100, 10000));
            data.setOutgoinginttrndebitNumber(faker.number().numberBetween(0, 10));
            data.setOutgoinglocaltransactiondebitAmount(faker.number().randomDouble(2, 100, 5000));
            data.setOutgoinglocaltransactiondebitNumber(faker.number().numberBetween(0, 20));
            data.setOverthecounterwithdrawalsAmount(faker.number().randomDouble(2, 100, 5000));
            data.setOverthecounterwithdrawalsNumber(faker.number().numberBetween(0, 10));


            response.getTransactions().add(data);
        }
        return response;
    }
}
