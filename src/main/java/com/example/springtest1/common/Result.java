package com.example.springtest1.common;

public class Result<T> {
    private int code;      // 状态码
    private String message; // 提示信息
    private T data;        // 实际数据

    // ⬇ getter 必须有，Jackson 靠它转 JSON
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    // ⬇ 构造器私有，只能通过静态方法创建
    private Result() {}

    public static <T> Result<T> success(T data){
        Result<T> r = new Result<>();
        r.code = 200;
        r.message = "success";
        r.data = data;
        return r;
    }

    public static <T> Result<T> success(){
        return success(null);
    }

    public static <T> Result<T> error(int code, String message){
        Result<T> r = new Result<>();
        r.code = code;
        r.message = message;
        return r;
    }
}
