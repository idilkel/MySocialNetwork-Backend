package com.jb.MySocialNetwork.exceptions;

import lombok.Data;

@Data
public class SocialNetworkSecurityException extends Exception {
    private SecMsg secMsg; //in order to enable e.getSecMsg from outside

    public SocialNetworkSecurityException(SecMsg secMsg) {
        super(secMsg.getMsg());
        this.secMsg = secMsg;
    }
}
