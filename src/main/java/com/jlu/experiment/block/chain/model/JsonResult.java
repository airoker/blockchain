package com.jlu.experiment.block.chain.model;

/**
 * description:
 */
public class JsonResult<T> {
    private boolean ret;
    private int status;
    private String msg;
    private T data;


    private JsonResult() {
    }

    public JsonResult(int status, String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<T>();
        result.setRet(true);
        result.setStatus(0);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> error(String message) {
        JsonResult result = new JsonResult();
        result.setRet(false);
        result.setStatus(10000);
        result.setMsg(message);
        return result;
    }

    public static <T> JsonResult<T> error(int errStatus, String message) {
        JsonResult result = new JsonResult();
        result.setRet(false);
        result.setStatus(errStatus);
        result.setMsg(message);
        return result;
    }

    public static <T> JsonResult<T> error(int errStatus, String message, T data) {
        JsonResult result = new JsonResult();
        result.setRet(false);
        result.setStatus(errStatus);
        result.setMsg(message);
        result.setData(data);
        return result;
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
