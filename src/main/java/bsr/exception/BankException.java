package bsr.exception;

/**
 * Exception for bank methods
 */
public class BankException extends Exception{
    private ServiceFault serviceFault;

    public ServiceFault getServiceFault() {
        return serviceFault;
    }

    public void setServiceFault(ServiceFault serviceFault) {
        this.serviceFault = serviceFault;
    }

    public BankException(String message){
        super(message);
    }

    public BankException(String message, ServiceFault serviceFault){
        super(message);
        this.serviceFault = serviceFault;
    }
}
