package com.cece.cbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

@Component
public class BasicAuthSoapInterceptor extends EndpointInterceptorAdapter {

    @Value("${api.core-banking.username}")
    private String username;

    @Value("${api.core-banking.password}")
    private String password;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        SaajSoapMessage soapMessage = (SaajSoapMessage) messageContext.getRequest();
        SoapHeader soapHeader = soapMessage.getSoapHeader();
        SoapHeaderElement authHeaderElement = soapHeader.examineAllHeaderElements()
                .next(); // Assuming the Authorization header is the first header element

        if (authHeaderElement == null) {
            throw new RuntimeException("Missing Authorization header");
        }

        String authHeader = authHeaderElement.getText();
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String base64Credentials = authHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        String[] values = credentials.split(":", 2);
        if (values.length != 2 || !username.equals(values[0]) || !password.equals(values[1])) {
            throw new RuntimeException("Invalid username or password");
        }

        return true;
    }

}








