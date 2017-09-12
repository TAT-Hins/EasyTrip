package com.seu.cose.xutils3.pojo;

import java.io.Serializable;

/**
 * Created by Hins on 2017/9/11,011.
 */

public class JsonBean implements Serializable {

    private Object data;
    private String tokenId;
    private String serviceType;
    private int statusId;
    private String statusMsg;

    public JsonBean() {    }

    public JsonBean(Class<?> data, String serviceType, int statusId, String statusMsg, String tokenId) {
        this.data = data;
        this.serviceType = serviceType;
        this.statusId = statusId;
        this.statusMsg = statusMsg;
        this.tokenId = tokenId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public String toString() {
        return "JSONObject{" +
                "data=" + data +
                ", tokenId='" + tokenId + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", statusId=" + statusId +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}
