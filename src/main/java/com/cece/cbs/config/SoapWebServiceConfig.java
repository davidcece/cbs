package com.cece.cbs.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

@Configuration
@EnableWs
@RequiredArgsConstructor
public class SoapWebServiceConfig extends WsConfigurerAdapter {

    private final BasicAuthSoapInterceptor basicAuthSoapInterceptor;

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/service/*");
    }

    @Bean(name = "customer")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCustomer(XsdSchema customerSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("CustomerPort");
        wsdl.setLocationUri("/service/customer");
        wsdl.setTargetNamespace("http://credable.io/cbs/customer");
        wsdl.setSchema(customerSchema);
        return wsdl;
    }

    @Bean(name = "transaction")
    public DefaultWsdl11Definition defaultWsdl11DefinitionTransaction(XsdSchema transactionSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("TransactionPort");
        wsdl.setLocationUri("/service/transaction");
        wsdl.setTargetNamespace("http://credable.io/cbs/transaction");
        wsdl.setSchema(transactionSchema);
        return wsdl;
    }

    @Bean
    public XsdSchema customerSchema() {
        return new SimpleXsdSchema(new ClassPathResource("customer.xsd"));
    }

    @Bean
    public XsdSchema transactionSchema() {
        return new SimpleXsdSchema(new ClassPathResource("transaction.xsd"));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(basicAuthSoapInterceptor);
    }
}
