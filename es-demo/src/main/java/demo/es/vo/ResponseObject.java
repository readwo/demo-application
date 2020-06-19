package demo.es.vo;

import java.io.Serializable;

/**
 * 接口返回对象
 *
 * @author
 */

public  class ResponseObject implements Serializable
{
    //返回消息编码(2000-成功；9901-异常...)
    private int respCode;
    //返回消息字符串
    private String respMsg;
    //返回对象
    private Object bizBody;


    //返回额外数据
    private Object objDate;

    public Object getObj() {
        return objDate;
    }

    public void setObj(Object obj) {
        this.objDate = obj;
    }

    /**
     * 成功
     */
    public static final String SUCCESS = "success";
    /**
     * 校验失败
     */
    public static final String FAIL = "fail";
    /**
     * 程序异常
     */
    public static final String ERROR = "error";
    
    public static final String UNAUTHORIZED="unauthorized";

    /**
     * 成功
     *
     * @return the response object
     */
    public static ResponseObject success()
    {
        return new ResponseObject(2000, SUCCESS);
    }

    /**
     * 失败
     *
     * @return the response object
     */
    public static ResponseObject fail()
    {
        return new ResponseObject(9901, FAIL);
    }

    /**
     * 异常
     *
     * @return the response object
     */
    public static ResponseObject error()
    {
        return new ResponseObject(1001, ERROR);
    }
    
    /**
     * 失败
     *
     * @return the response object
     */
    public static ResponseObject fail(String msg)
    {
        return new ResponseObject(9901, msg);
    }

    /**
     * 异常
     *
     * @return the response object
     */
    public static ResponseObject error(String msg)
    {
        return new ResponseObject(1001, msg);
    }
    
    /**
     * 成功
     *
     * @return the response object
     */
    public static ResponseObject success(Object obj)
    {
        return new ResponseObject(2000, SUCCESS, obj);
    }


    /**
     * 成功
     *
     * @return the response object
     */
    public static ResponseObject success(Object obj,  Object objDate)
    {
        return new ResponseObject(2000, SUCCESS, objDate);
    }

    /**
     * 失败
     *
     * @return the response object
     */
    public static ResponseObject fail(Object obj)
    {
        return new ResponseObject(9901, FAIL, obj);
    }

    /**
     * 异常
     *
     * @return the response object
     */
    public static ResponseObject error(Object obj)
    {
        return new ResponseObject(9901, ERROR, obj);
    }
    
    /**
     * 失败
     *
     * @return the response object
     */
    public static ResponseObject fail(String msg, Object obj)
    {
        return new ResponseObject(9901, msg, obj);
    }
    
    /**
     * 失败
     *
     * @return the response object
     */
    public static ResponseObject error(String msg, Object obj)
    {
        return new ResponseObject(9901, msg, obj);
    }
    
    /**
     * 失败
     *
     * @return the response object
     */
    public static ResponseObject unauthorized()
    {
        return new ResponseObject(403, UNAUTHORIZED);
    }
    
    /**
     * 无权限
     *
     * @return the response object
     */
    public static ResponseObject unauthorized(Object obj)
    {
        return new ResponseObject(403, UNAUTHORIZED, obj);
    }

    /**
     * 无权限
     *
     * @return the response object
     */
    public static ResponseObject unauthorized(String msg, Object obj)
    {
        return new ResponseObject(403, msg, obj);
    }

    /**
     * Instantiates a new Response object.
     */
    public ResponseObject() {}

    /**
     * Instantiates a new Response object.
     *
     * @param msgCode the msg code
     * @param msg     the msg
     */
    public ResponseObject(int respCode, String respMsg)
    {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    /**
     * Instantiates a new Response object.
     *
     * @param msgCode      the msg code
     * @param msg          the msg
     * @param responseBody the response body
     */
    public ResponseObject(int respCode, String respMsg, Object responseBody)
    {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.bizBody = responseBody;
    }


    public ResponseObject(int respCode, String respMsg, Object responseBody,  Object objDate)
    {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.bizBody = responseBody;
        this.objDate=objDate;
    }
    /**
     * Gets msg code.
     *
     * @return the msg code
     */
    public int getRespCode()
    {
        return respCode;
    }

    /**
     * Sets msg code.
     *
     * @param msgCode the msg code
     */
    public void setRespCode(int respCode)
    {
        this.respCode = respCode;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getRespMsg()
    {
        return respMsg;
    }

    /**
     * Sets msg.
     *
     * @param msg the msg
     */
    public void setRespMsg(String respMsg)
    {
        this.respMsg = respMsg;
    }

    /**
     * Gets response body.
     *
     * @return the response body
     */
    public Object getBizBody()
    {
        return bizBody;
    }

    /**
     * Sets response body.
     *
     * @param responseBody the response body
     */
    public void setBizBody(Object bizBody)
    {
        this.bizBody = bizBody;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "respCode=" + respCode +
                ", respMsg='" + respMsg + '\'' +
                ", bizBody=" + bizBody +
                '}';
    }
}
