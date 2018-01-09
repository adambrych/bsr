package bsr.model;


public enum TransferType {
    INTERNAL_TRANSFER("Transfer wewnętrzny", 0),
    EXTERNAL_TRANSFER("Transfer zewnętrzny", 1),
    PAYMENT("Wpłata własna", 2),
    WITHDRAWAL("Wypłata własna", 3);

    public String description;
    public int code;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    TransferType(String description, int code){
        this.description = description;
        this.code = code;
    }

    public static TransferType getByCode(int code) {
        for(TransferType e : values()) {
            if(e.code == code)
                return e;
        }
        return null;
    }
}
