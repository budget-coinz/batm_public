/*************************************************************************************
 * Copyright (C) 2014-2020 GENERAL BYTES s.r.o. All rights reserved.
 *
 * This software may be distributed and modified under the terms of the GNU
 * General Public License version 2 (GPL2) as published by the Free Software
 * Foundation and appearing in the file GPL2.TXT included in the packaging of
 * this file. Please note that GPL2 Section 2[b] requires that all works based
 * on this software must also be made publicly available under the terms of
 * the GPL2 ("Copyleft").
 *
 * Contact information
 * -------------------
 *
 * GENERAL BYTES s.r.o.
 * Web      :  http://www.generalbytes.com
 *
 ************************************************************************************/
package com.budgetcoinz.batm.server.extensions.shared;

/**
 * Simple data transfer class
 */
public class ExtensionRestResponse {
    int resultCode;
    String message;

    Object data;
    public ExtensionRestResponse() {
    }

    public ExtensionRestResponse(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ExtensionRestResponse(int resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
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

    @Override
    public String toString() {
        return "ExtensionRestResponse{" +
            "resultCode=" + resultCode +
            ", message='" + message + '\'' +
            '}';
    }
}
