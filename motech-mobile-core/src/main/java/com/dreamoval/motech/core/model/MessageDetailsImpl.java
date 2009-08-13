
package com.dreamoval.motech.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Joseph Djomeda (joseph@dreamoval.com)
 */
public class MessageDetailsImpl extends MotechEntityImpl implements MessageDetails{
    
    private String messageType;
    private int numberOfPages;
    private String messageText;
    private String recipientsNumbers;
    private String globalStatus;
    private Set<ResponseDetails> responseDetails = new HashSet<ResponseDetails>();
    private Date dateSent;

    public MessageDetailsImpl(){}

    public MessageDetailsImpl(String messageType, int numberOfPages, String messageText, String recipientsNumbers, String globalStatus, Date dateSent) {
        this.messageType = messageType;
        this.numberOfPages = numberOfPages;
        this.messageText = messageText;
        this.globalStatus = globalStatus;
        this.dateSent = dateSent;
        this.recipientsNumbers = recipientsNumbers;
    }    

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
    public Set<ResponseDetails> getResponseDetails() {
        return responseDetails;
    }

    /**
     * @param responseDetails the responseDetails to set
     */
    public void setResponseDetails(Set<ResponseDetails> responseDetails) {
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
    public String getRecipientsNumbers() {
        return recipientsNumbers;
    }

    /**
     * @param recipientsNumbers the recipientsNumbers to set
     */
    public void setRecipientsNumbers(String recipientsNumbers) {
        this.recipientsNumbers = recipientsNumbers;
    }

    public void addResponse(ResponseDetails response) {
        response.setMessageId(this);
        this.responseDetails.add(response);
    }

    public void removeResponse(ResponseDetails response) {
        if(this.responseDetails.contains(response)) {
            this.responseDetails.remove(response);
        }

    }

    public void addResponse(List<ResponseDetails> responses) {

        for(ResponseDetails r : responses) {
            r.setMessageId(this);
            this.responseDetails.add(r);
        }

    }

      public void removeResponse(List<ResponseDetails> responses) {
          for(ResponseDetails r : responses) {
              if(this.responseDetails.contains(r))
                  this.responseDetails.remove(r);
          }
    }

}