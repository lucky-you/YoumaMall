package com.zhowin.youmamall.mine.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GsonUtils;
import com.zhowin.base_library.utils.SPUtils;

/**
 * author : zho
 * date  ：2021/1/27
 * desc ：提现账号
 */
public class AccountMessage implements Parcelable {

    private String accountNumber;
    private String userName;


    public static void setUserAccountMessage(AccountMessage data) {
        String accountMessage = GsonUtils.toJson(data);
        SPUtils.set(ConstantValue.ACCOUNT_MESSAGE, accountMessage);
    }

    public static AccountMessage getUserAccountMessage() {
        AccountMessage accountMessage = GsonUtils.fromJson(SPUtils.getString(ConstantValue.ACCOUNT_MESSAGE), AccountMessage.class);
        if (accountMessage != null) {
            return accountMessage;
        } else {
            return new AccountMessage();
        }
    }

    public AccountMessage() {

    }

    public AccountMessage(String accountNumber, String userName) {
        this.accountNumber = accountNumber;
        this.userName = userName;
    }

    protected AccountMessage(Parcel in) {
        accountNumber = in.readString();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountNumber);
        dest.writeString(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountMessage> CREATOR = new Creator<AccountMessage>() {
        @Override
        public AccountMessage createFromParcel(Parcel in) {
            return new AccountMessage(in);
        }

        @Override
        public AccountMessage[] newArray(int size) {
            return new AccountMessage[size];
        }
    };

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
