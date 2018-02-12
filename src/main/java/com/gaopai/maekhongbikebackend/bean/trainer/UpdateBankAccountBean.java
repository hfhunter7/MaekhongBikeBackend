package com.gaopai.maekhongbikebackend.bean.trainer;

public class UpdateBankAccountBean {
    private String bankName;
    private String branch;
    private String bankNumber;
    private String bankType;
    private String holderName;

    public UpdateBankAccountBean() {
    }

    public String getBankName() {
        return bankName;
    }

    public String getBranch() {
        return branch;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getBankType() {
        return bankType;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
}
