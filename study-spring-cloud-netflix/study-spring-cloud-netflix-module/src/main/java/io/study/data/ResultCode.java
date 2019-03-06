package io.study.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
public class ResultCode {

    public static Map<String, String> map = new HashMap<String, String>();

    public static String ADD_SUCC = "ADD_SUCC";
    public static String ADD_FAIL = "ADD_FAIL";
    public static String UPDATE_SUCC = "UPDATE_SUCC";
    public static String UPDATE_FAIL = "UPDATE_FAIL";
    public static String QUERY_SUCC = "QUERY_SUCC";
    public static String QUERY_FAIL = "QUERY_FAIL";
    public static String DELETE_SUCC = "DELETE_SUCC";
    public static String DELETE_FAIL = "DELETE_FAIL";
    public static String DO_SUCC = "DO_SUCC";
    public static String DO_FAIL = "DO_FAIL";

    static {

        map.put(ADD_SUCC, ADD_SUCC);
        map.put(ADD_FAIL, ADD_FAIL);
        map.put(UPDATE_SUCC, UPDATE_SUCC);
        map.put(UPDATE_FAIL, UPDATE_FAIL);
        map.put(QUERY_SUCC, QUERY_SUCC);
        map.put(QUERY_FAIL, QUERY_FAIL);
        map.put(DELETE_SUCC, DELETE_SUCC);
        map.put(DELETE_FAIL, DELETE_FAIL);
        map.put(DO_SUCC, DO_SUCC);
        map.put(DO_FAIL, DO_FAIL);

    }

    public static String getMessage(String code){
        return map.get(code);
    }

    public static Result getSucc(String code){
        return new Result(Result.SUCC, map.get(code));
    }

    public static Result getFail(String code){
        return new Result(Result.FAIL, map.get(code));
    }


    public static Result getSucc(String code, Object data){
        return new Result(Result.SUCC, map.get(code), data);
    }

    public static Result getFail(String code, Object data){
        return new Result(Result.FAIL, map.get(code), data);
    }

}
