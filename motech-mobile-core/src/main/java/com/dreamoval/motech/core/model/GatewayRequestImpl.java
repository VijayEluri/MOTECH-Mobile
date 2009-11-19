package com.dreamoval.motech.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Joseph Djomeda (joseph@dreamoval.com)
 */
public class GatewayRequestImpl extends MotechEntityImpl implements GatewayRequest {

    private GatewayRequestDetails gatewayRequestDetails;
    private Date dateTo;
    private String message;
    private String recipientsNumber;
    private Date dateFrom;
    private Set<GatewayResponse> responseDetails = new HashSet<GatewayResponse>();
    private Date dateSent;
    private int tryNumber;
    private String requestId;
    private MStatus messageStatus;
    private Date lastModified;

    public GatewayRequestImpl() {
    }

    public GatewayRequestImpl(Date dateTo, String messageText, String recipientsNumber, Date dateFrom, Date dateSent) {

        this.dateTo = dateTo;
        this.message = messageText;
        this.dateFrom = dateFrom;
        this.dateSent = dateSent;
        this.recipientsNumber = recipientsNumber;

    }

    /**
     * @return the messageType
     */
    /**
     * @return the numberOfPages
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param numberOfPages the numberOfPages to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the messageText
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param messageText the messageText to set
     */
    public void setMessage(String messageText) {
        this.message = messageText;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the responseDetails
     */
    public Set<GatewayResponse> getResponseDetails() {
        return responseDetails;
    }

    /**
     * @param responseDetails the responseDetails to set
     */
    public void setResponseDetails(Set<GatewayResponse> responseDetails) {
        this.responseDetails = responseDetails;
    }

    /**
     * @return the dateSent
     */
    public Date getDateSent() {
        return dateSent;
    }

    /**
     * @param dateSent the dateSent to set
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /**
     * @return the recipientsNumbers
     */
    public String getRecipientsNumber() {
        return recipientsNumber;
    }

    /**
     * @param recipientsNumbers the recipientsNumbers to set
     */
    public void setRecipientsNumber(String recipientsNumbers) {
        this.recipientsNumber = recipientsNumbers;
    }

    /**
     * @see {@link com.dreamoval.motech.core.model.GatewayRequest#addResponse(com.dreamoval.motech.core.model.GatewayResponse)  }
     */
    public void addResponse(GatewayResponse response) {
        response.setGatewayRequest(this);
        this.responseDetails.add(response);
    }

    /**
     * @see {@link  com.dreamoval.motech.core.model.GatewayRequest#removeResponse(com.dreamoval.motech.core.model.GatewayResponse)  }
     */
    public void removeResponse(GatewayResponse response) {
        if (this.responseDetails.contains(response)) {
            this.responseDetails.remove(response);
        }

    }

    /**
     * @see {@link com.dreamoval.motech.core.model.GatewayRequest#addResponse(java.util.List)  }
     */
    public void addResponse(List<GatewayResponse> responses) {

        for (GatewayResponse r : responses) {
            r.setGatewayRequest(this);
            this.responseDetails.add(r);
        }

    }

    /**
     * @see {@link com.dreamoval.motech.core.model.GatewayRequest#removeResponse(java.util.List)  }
     */
    public void removeResponse(List<GatewayResponse> responses) {
        for (GatewayResponse r : responses) {
            if (this.responseDetails.contains(r)) {
                this.responseDetails.remove(r);
            }
        }
    }

    /**
     * @return the gatewayRequestDetails
     */
    public GatewayRequestDetails getGatewayRequestDetails() {
        return gatewayRequestDetails;
    }

    /**
     * @param gatewayRequestDetails the gatewayRequestDetails to set
     */
    public void setGatewayRequestDetails(GatewayRequestDetails gatewayRequestDetails) {
        this.gatewayRequestDetails = gatewayRequestDetails;
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the tryNumber
     */
    public int getTryNumber() {
        return tryNumber;
    }

    /**
     * @param tryNumber the tryNumber to set
     */
    public void setTryNumber(int tryNumber) {
        this.tryNumber = tryNumber;
    }

    /**
     * @return the messageStatus
     */
    public MStatus getMessageStatus() {
        return messageStatus;
    }

    /**
     * @param messageStatus the messageStatus to set
     */
    public void setMessageStatus(MStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * @return the lastModified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
