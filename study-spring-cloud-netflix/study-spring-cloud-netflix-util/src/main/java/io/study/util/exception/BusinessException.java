package io.study.util.exception;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException() {
    }

    public BusinessException(String code) {
        super(code);
    }

    public BusinessException(String code, Throwable cause) {
        super(code, cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
