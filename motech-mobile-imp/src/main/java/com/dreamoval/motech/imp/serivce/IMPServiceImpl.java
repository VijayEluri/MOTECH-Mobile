/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamoval.motech.imp.serivce;

import com.dreamoval.motech.core.manager.CoreManager;
import com.dreamoval.motech.core.model.Duplicatable;
import com.dreamoval.motech.imp.manager.IMPManager;
import com.dreamoval.motech.imp.util.CommandAction;
import com.dreamoval.motech.imp.util.IncomingMessageParser;
import com.dreamoval.motech.core.model.IncomingMessage;
import com.dreamoval.motech.core.model.IncomingMessageResponse;
import com.dreamoval.motech.core.service.MotechContext;
import com.dreamoval.motech.model.dao.imp.IncomingMessageDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Transaction;

/**
 * @author Kofi A. Asamoah (yoofi@dreamoval.com)
 *  Date : Dec 5, 2009
 */
public class IMPServiceImpl implements IMPService {
    private int duplicatePeriod;
    private String demoMsgType;
    private String demoProvider;
    private String demoSender;
    private IMPManager impManager;
    private CoreManager coreManager;
    private IncomingMessageParser parser;
    private Map<String, CommandAction> cmdActionMap;

    /**
     *
     * @see IMPService.processRequest
     */
    public synchronized String processRequest(String message, String requesterPhone, boolean isDemo){
        String demoResp = "{" + demoMsgType + "}{" + demoProvider + "}{" + demoSender + "}{" + requesterPhone + "}";
        MotechContext context = coreManager.createMotechContext();
        IncomingMessageDAO msgDao = coreManager.createIncomingMessageDAO(context);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 0 - duplicatePeriod);
        Date beforeDate = cal.getTime();
        IncomingMessage inMsg = msgDao.getByContentNonDuplicatable(message);

        if(inMsg != null && inMsg.getContent().equalsIgnoreCase(message))
        {
            if(inMsg.getIncomingMessageForm() != null){
                if(inMsg.getIncomingMessageForm().getIncomingMsgFormDefinition().getDuplicatable() == Duplicatable.DISALLOWED || (inMsg.getIncomingMessageForm().getIncomingMsgFormDefinition().getDuplicatable() == Duplicatable.TIME_BOUND && inMsg.getDateCreated().after(beforeDate)))
                    return "Error:\nThis form has already been processed!";
            }
        }

        inMsg = parser.parseRequest(message);

        Transaction tx = (Transaction) msgDao.getDBSession().getTransaction();
        tx.begin();
        msgDao.save(inMsg);
        tx.commit();

        IncomingMessageResponse response = impManager.createCommandAction().execute(inMsg, requesterPhone, context);

        if(isDemo)
            return demoResp + "{" + response.getContent() + "}";
        
        return response.getContent();
    }

    /**
     *
     * @see IMPService.processMultiRequests
     */
    public List<FormRequest> processMultiRequests(List<FormRequest> requests, boolean isDemo) {
        for(FormRequest fReq : requests){
            fReq.setResponse(processRequest(fReq.getMessage(), fReq.getSenderNumber(), isDemo));
        }
        return requests;
    }

    /**
     * @return the coreManager
     */
    public CoreManager getCoreManager() {
        return coreManager;
    }

    /**
     * @param coreManager the coreManager to set
     */
    public void setCoreManager(CoreManager coreManager) {
        this.coreManager = coreManager;
    }

    /**
     * @return the parser
     */
    public IncomingMessageParser getParser() {
        return parser;
    }

    /**
     * @param parser the parser to set
     */
    public void setParser(IncomingMessageParser parser) {
        this.parser = parser;
    }

    /**
     * @return the cmdActionMap
     */
    public Map<String, CommandAction> getCmdActionMap() {
        return cmdActionMap;
    }

    /**
     * @param cmdActionMap the cmdActionMap to set
     */
    public void setCmdActionMap(Map<String, CommandAction> cmdActionMap) {
        this.cmdActionMap = cmdActionMap;
    }

    /**
     * @return the impManager
     */
    public IMPManager getImpManager() {
        return impManager;
    }

    /**
     * @param impManager the impManager to set
     */
    public void setImpManager(IMPManager impManager) {
        this.impManager = impManager;
    }

    /**
     * @param duplicatePeriod the duplicatePeriod to set
     */
    public void setDuplicatePeriod(int duplicatePeriod) {
        this.duplicatePeriod = duplicatePeriod;
    }

    /**
     * @param demoMsgType the demoMsgType to set
     */
    public void setDemoMsgType(String demoMsgType) {
        this.demoMsgType = demoMsgType;
    }

    /**
     * @param demoProvider the demoProvider to set
     */
    public void setDemoProvider(String demoProvider) {
        this.demoProvider = demoProvider;
    }

    /**
     * @param demoSender the demoSender to set
     */
    public void setDemoSender(String demoSender) {
        this.demoSender = demoSender;
    }
}
