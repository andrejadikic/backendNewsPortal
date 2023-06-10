package rs.raf.demo.enums;

public class ReqException extends Exception{

    private int code;

    public ReqException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
