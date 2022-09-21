package com.jb.MySocialNetwork.exceptions;

public class SocialNetworkException extends Exception {
    public SocialNetworkException(ErrMsg errMsg) {
        super(errMsg.getErrMsg());
    }
}
