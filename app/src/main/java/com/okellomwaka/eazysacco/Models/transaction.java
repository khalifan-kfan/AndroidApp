package com.okellomwaka.eazysacco.Models;

public class transaction extends  DocId {
/*      pay.put("amount", amount.getText().toString());
        pay.put("mobile", phone_number.getText().toString());
        pay.put("externalId", externalId);
        pay.put("userId",auth.getCurrentUser().getUid());
        pay.put("transactionId",ref);
        pay.put("account", account);

 */
    String amount;
    String mobile;
    String externalId;
    String userId;
    String transactionId;
    String account;

    public transaction(String amount, String mobile, String externalId, String userId, String transactionId, String account) {
        this.amount = amount;
        this.mobile = mobile;
        this.externalId = externalId;
        this.userId = userId;
        this.transactionId = transactionId;
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
