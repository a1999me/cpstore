package com.cy.store.service.ex;

import javax.xml.ws.Service;

/**
 * 用此异常来表示收货地址总数超出限制的异常（20）
 */
public class AddressCountLimitException extends ServiceException {
    public AddressCountLimitException() {
        super();
    }

    public AddressCountLimitException(String message) {
        super(message);
    }

    public AddressCountLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressCountLimitException(Throwable cause) {
        super(cause);
    }

    protected AddressCountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public void printStackTrace(String message) {
        super.printStackTrace(message);
    }
}
