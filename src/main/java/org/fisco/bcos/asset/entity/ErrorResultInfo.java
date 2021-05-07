package org.fisco.bcos.asset.entity;

/**
 * @author yangmeilin <yangmeilin@kuaishou.com>
 * Created on 2021-04-27
 */
public class ErrorResultInfo {
    String nodeId;
    double errorRate;
    int errorLevel;
    int errorTime;
    double loss;

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public ErrorResultInfo(String nodeId,double errorRate,int errorLevel,int errorTime,double loss){
        this.errorLevel=errorLevel;
        this.errorRate=errorRate;
        this.errorTime=errorTime;
        this.nodeId=nodeId;
        this.loss=loss;
    }

    public ErrorResultInfo(){

    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    public int getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(int errorLevel) {
        this.errorLevel = errorLevel;
    }

    public int getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(int errorTime) {
        this.errorTime = errorTime;
    }
}
