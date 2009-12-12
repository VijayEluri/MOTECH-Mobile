package com.dreamoval.motech.core.model;

import com.dreamoval.motech.model.imp.IncomingMessageFormParameter;
import java.util.Date;
import java.util.List;

public interface IncomingMessageForm extends MotechEntity {

    /**
     * @return the dateCreated
     */
    public Date getDateCreated();

    /**
     * @return the incomingMsgFormDefinition
     */
    public IncomingMessageFormDefinition getIncomingMsgFormDefinition();

    /**
     * @return the lastModified
     */
    public Date getLastModified();

    /**
     * @return the messageStatus
     */
    public IncMessageFormStatus getMessageFormStatus();

    /**
     * @return the incomingMsgFormParameters
     */
    public List<IncomingMessageFormParameter> getIncomingMsgFormParameters();

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated);

    /**
     * @param incomingMsgFormDefinition the incomingMsgFormDefinition to set
     */
    public void setIncomingMsgFormDefinition(IncomingMessageFormDefinition incomingMsgFormDefinition);

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Date lastModified);

    /**
     * @param messageStatus the messageStatus to set
     */
    public void setMessageFormStatus(IncMessageFormStatus messageStatus);

    /**
     * @param incomingMsgFormParameters the incomingMsgFormParameters to set
     */
    public void setIncomingMsgFormParameters(List<IncomingMessageFormParameter> incomingMsgFormParameters);
}
