//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.19 at 12:58:37 PM CET 
//


package https.www_bank_com.account;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.www_bank_com.account package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.www_bank_com.account
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TokenResponse }
     * 
     */
    public TokenResponse createTokenResponse() {
        return new TokenResponse();
    }

    /**
     * Create an instance of {@link AccountHistoryRequest }
     * 
     */
    public AccountHistoryRequest createAccountHistoryRequest() {
        return new AccountHistoryRequest();
    }

    /**
     * Create an instance of {@link LoginRequest }
     * 
     */
    public LoginRequest createLoginRequest() {
        return new LoginRequest();
    }

    /**
     * Create an instance of {@link AccountResponse }
     * 
     */
    public AccountResponse createAccountResponse() {
        return new AccountResponse();
    }

    /**
     * Create an instance of {@link AccountHistoryResponse }
     * 
     */
    public AccountHistoryResponse createAccountHistoryResponse() {
        return new AccountHistoryResponse();
    }

    /**
     * Create an instance of {@link TransferForResponse }
     * 
     */
    public TransferForResponse createTransferForResponse() {
        return new TransferForResponse();
    }

}
