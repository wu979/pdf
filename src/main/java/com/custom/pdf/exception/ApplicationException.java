package com.custom.pdf.exception;

/**
 * @des:
 * @author: wsw
 * @email: 18683789594@163.com
 * @date: 2020/1/2 21:20
 */
public class ApplicationException extends RuntimeException {

    private Integer code;

    private String msg;

    public ApplicationException(String msg){
        super(msg);
        this.code = 500;
    }

    public ApplicationException(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
