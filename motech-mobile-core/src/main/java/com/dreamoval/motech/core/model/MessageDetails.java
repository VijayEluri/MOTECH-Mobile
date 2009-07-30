/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamoval.motech.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jojo
 */
public class MessageDetails implements Serializable{

    public MessageDetails(){}

    private static final long serialVersionUID = 1L;
    private String gid;

    private Long messageId;

    private String messageType;
    private int numberOfPages;
    private String messageText;
    private String recipientsNumbers;
    private String globalStatus;
    private List<ResponseDetails> responseDetails;

    public MessageDetails(String messageType, int numberOfPages, String messageText, String recipientsNumbers, String globalStatus, Date dateSent) {
        this.messageType = messageType;
        this.numberOfPages = numberOfPages;
        this.messageText = messageText;
        this.globalStatus = globalStatus;
        this.dateSent = dateSent;
        this.recipientsNumbers = recipientsNumbers;
    }
    private Date dateSent;

    /**
     * @return the messageType
     */
    
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * @param numberOfPages the numberOfPages to set
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * @return the messageText
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * @param messageText the messageText to set
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * @return the globalStatus
     */
    public String getGlobalStatus() {
        return globalStatus;
    }

    /**
     * @param globalStatus the globalStatus to set
     */
    public void setGlobalStatus(String globalStatus) {
        this.globalStatus = globalStatus;
    }

    /**
     * @return the responseDetails
     */
    public List<ResponseDetails> getResponseDetails() {
        return responseDetails;
    }

    /**
     * @param responseDetails the responseDetails to set
     */
    public void setResponseDetails(List<ResponseDetails> responseDetails) {
        this.responseDetails = responseDetails;
    }

    /**
     * @return the messageId
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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
    public String getRecipientsNumbers() {
        return recipientsNumbers;
    }

    /**
     * @param recipientsNumbers the recipientsNumbers to set
     */
    public void setRecipientsNumbers(String recipientsNumbers) {
        this.recipientsNumbers = recipientsNumbers;
    }

    /**
     * @return the gid
     */
    public String getGid() {
        return gid;
    }

    /**
     * @param gid the gid to set
     */
    public void setGid(String gid) {
        this.gid = gid;
    }

   
}