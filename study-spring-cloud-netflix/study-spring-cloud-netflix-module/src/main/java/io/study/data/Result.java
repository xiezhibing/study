package io.study.data;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
public class Result {

    public static String SUCC = "200";
    public static String FAIL = "-1";

    private String code;
    private String message;
    private Object data;

    public Result(){}

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
