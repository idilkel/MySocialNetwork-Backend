package com.jb.MySocialNetwork.exceptions;

import lombok.Getter;

@Getter
public enum ErrMsg {
    ID_DOES_NOT_EXIST_EXCEPTION("ID doesn't exist exception"),
    EMAIL_DOES_NOT_EXIST_EXCEPTION("Email doesn't exist exception"),
    ID_ALREADY_EXISTS_EXCEPTION("ID already exists exception"),
    ID_OR_EMAIL_ALREADY_EXISTS_EXCEPTION("ID or email already exists exception"),
    FRIENDSHIP_ALREADY_EXISTS("Friendship already exists exception"),
    FRIENDSHIP_DOESNT_EXISTS("Friendship doesn't exists exception"),
    CANNOT_ADD_OR_REMOVE_USER1("User#1 can't be added or removed as a friend");

    private String ErrMsg;

    ErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }
}
