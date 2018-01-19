package bsr.main;


import bsr.exception.BankException;
import bsr.exception.DetailSoapFaultDefinitionExceptionResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;


/**
 * Configuration of web services
 */
@EnableWs
@Configuration
public class Config extends WsConfigurerAdapter
{

    /**
     * Bean for setting exception is soap
     * @return
     */
    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        exceptionResolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();
        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(BankException.class.getName(), SoapFaultDefinition.SERVER.toString());
        exceptionResolver.setExceptionMappings(errorMappings);
        exceptionResolver.setOrder(1);
        return exceptionResolver;
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext)
    {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/service/*");
    }

    /**
     * Creating bean of transfer wsdl
     * @param transferSchema
     * @return
     */
    @Bean(name = "transferDetailsWsdl")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema transferSchema)
    {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TransferDetailsPort");
        wsdl11Definition.setLocationUri("/service");
        wsdl11Definition.setTargetNamespace("https://www.bank.com/transfer");
        wsdl11Definition.setSchema(transferSchema);
        return wsdl11Definition;
    }

    /**
     * Creating bean of xml schema for transfer endpoint
     * @return
     */
    @Bean
    public XsdSchema transferSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("Transfer.xsd"));
    }

    /**
     * Creating bean of account wsdl
     * @param transferSchema
     * @return
     */
    @Bean(name = "accountDetailsWsdl")
    public DefaultWsdl11Definition accountWsdl11Definition(XsdSchema transferSchema)
    {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AccountDetailsPort");
        wsdl11Definition.setLocationUri("/service/account");
        wsdl11Definition.setTargetNamespace("https://www.bank.com/account");
        wsdl11Definition.setSchema(transferSchema);
        return wsdl11Definition;
    }

    /**
     * Creating been of xml schema for account endpoint
     * @return
     */
    @Bean
    public XsdSchema accountSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("Account.xsd"));
    }
}