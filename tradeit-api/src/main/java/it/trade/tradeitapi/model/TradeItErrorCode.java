package it.trade.tradeitapi.model;

import com.google.gson.annotations.SerializedName;

public enum TradeItErrorCode {
    /*
    Refer to https://www.trade.it/documentation/api#ErrorHandling for error codes

    100 System Error
    200 Broker Execution Error - User should modify the input for the trade request
    300 Broker Authentication Error - Authentication info is incorrect or the user may have changed their login information and the oAuth token is no longer valid.
    400 Broker Account Error - User credentials are valid, but needs to take action on the brokers site (ie. sign exchange agreement, sign margin agreement)
    500 Params Error - Publisher should check the parameters being passed in
    600 Session Expired - Publisher should call authenticate again in order to generate a new session token
    700 Token invalid or expired - Publisher should call relinkAccount in order to refresh the token
    */

    @SerializedName("100")
    SYSTEM_ERROR(100),

    @SerializedName("200")
    BROKER_EXECUTION_ERROR(200),

    @SerializedName("300")
    BROKER_AUTHENTICATION_ERROR(300),

    @SerializedName("400")
    BROKER_ACCOUNT_ERROR(400),

    @SerializedName("500")
    PARAMETER_ERROR(500),

    @SerializedName("600")
    SESSION_EXPIRED (600),

    @SerializedName("700")
    TOKEN_INVALID_OR_EXPIRED(700);

    private final int value;
    public int getValue() {
        return value;
    }

    private TradeItErrorCode(int value) {
        this.value = value;
    }
}
