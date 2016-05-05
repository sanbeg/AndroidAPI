package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItAnswerSecurityQuestionRequest extends TradeItRequestWithSession {
    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("securityAnswer")
    @Expose
    public String securityAnswer;

    @SerializedName("srv")
    @Expose
    public String serverUuid;

    public TradeItAnswerSecurityQuestionRequest(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public String toString() {
        return "TradeItAnswerSecurityQuestionRequest{" +
                "token='" + token + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", srv='" + serverUuid + '\'' +
                '}';
    }
}
