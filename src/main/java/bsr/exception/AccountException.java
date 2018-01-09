package bsr.exception;

public class AccountException extends Exception{
    private ServiceFault serviceFault;

    public ServiceFault getServiceFault() {
        return serviceFault;
    }

    public void setServiceFault(ServiceFault serviceFault) {
        this.serviceFault = serviceFault;
    }

    public AccountException(String message){
        super(message);
    }

    public AccountException(String message, ServiceFault serviceFault){
        super(message);
        this.serviceFault = serviceFault;
    }
}
