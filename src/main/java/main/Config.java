package main;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class Config extends WsConfigurerAdapter
{
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext)
    {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/service/*");
    }

    @Bean(name = "transferDetailsWsdl")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema transferSchema)
    {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TransferDetailsPort");
        wsdl11Definition.setLocationUri("/service/transfer");
        wsdl11Definition.setTargetNamespace("transfer");
        wsdl11Definition.setSchema(transferSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema transferSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("Transfer.xsd"));
    }

    @Bean(name = "accountDetailsWsdl")
    public DefaultWsdl11Definition accountWsdl11Definition(XsdSchema transferSchema)
    {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AccountDetailsPort");
        wsdl11Definition.setLocationUri("/service/account");
        wsdl11Definition.setTargetNamespace("account");
        wsdl11Definition.setSchema(transferSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema accountSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("Account.xsd"));
    }
}