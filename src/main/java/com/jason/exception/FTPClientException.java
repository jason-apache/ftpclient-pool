package com.jason.exception;

/**
 * @author: Jason
 * @Date: 2020/7/23 13:57
 * @Description:
 */
public class FTPClientException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FTPClientException() {
    }

    public FTPClientException(String message) {
        super(message);
    }

    public FTPClientException(Throwable cause) {
        super(cause);
    }

    public FTPClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
