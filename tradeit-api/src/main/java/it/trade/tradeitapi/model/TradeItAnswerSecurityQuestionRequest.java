package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItAnswerSecurityQuestionRequest {
    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("securityAnswer")
    @Expose
    public String securityAnswer;

    @SerializedName("srv")
    @Expose
    public String srv;

    public TradeItAnswerSecurityQuestionRequest(String token, String securityAnswer, String srv) {
        this.token = token;
        this.securityAnswer = securityAnswer;
        this.srv = srv;
    }

    @Override
    public String toString() {
        return "TradeItAnswerSecurityQuestionRequest{" +
                "token='" + token + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", srv='" + srv + '\'' +
                '}';
    }
}
