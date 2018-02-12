package com.gaopai.maekhongbikebackend.domain;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * A sample class for adding error information in the response
 */
@XmlRootElement
public class RestErrorInfo {
    public final String detail;
    public final String message;
    public final int status;

    public RestErrorInfo(Exception ex, String detail, int status) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
        this.status = status;
    }
}

