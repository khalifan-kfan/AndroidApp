package com.okellomwaka.eazysacco.Models;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class deposit extends  DocId{

    String user_id;
    String account;
    @ServerTimestamp
    Date deposit_timestamp;

    String deposit;
    public deposit(String user_id, String account, String deposit) {
        this.user_id = user_id;
        this.account = account;
        this.deposit = deposit;
    }

    public deposit() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getDeposit_timestamp() {
        return deposit_timestamp;
    }

    public void setDeposit_timestamp(Date deposit_timestamp) {
        this.deposit_timestamp = deposit_timestamp;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }
}
