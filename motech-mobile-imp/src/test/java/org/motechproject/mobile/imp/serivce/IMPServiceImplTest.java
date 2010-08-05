/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.motechproject.mobile.imp.serivce;

import java.util.HashMap;
import java.util.Map;
import org.motechproject.mobile.core.dao.DBSession;
import org.motechproject.mobile.core.manager.CoreManager;
import org.motechproject.mobile.core.model.Duplicatable;
import org.motechproject.mobile.imp.manager.IMPManager;
import org.motechproject.mobile.imp.util.CommandAction;
import org.motechproject.mobile.imp.util.IncomingMessageParser;
import org.motechproject.mobile.core.model.IncomingMessage;
import org.motechproject.mobile.core.model.IncomingMessageFormDefinitionImpl;
import org.motechproject.mobile.core.model.IncomingMessageFormImpl;
import org.motechproject.mobile.core.model.IncomingMessageImpl;
import org.motechproject.mobile.core.model.IncomingMessageResponseImpl;
import org.motechproject.mobile.model.dao.imp.IncomingMessageDAO;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.mobile.core.model.IncMessageFormStatus;
import org.motechproject.mobile.core.model.IncomingMessageResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

/**
 * Test for IMPServiceImpl class
 *
 *  Date : Dec 5, 2009
 * @author Kofi A.  Asamoah (yoofi@dreamoval.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/imp-test-config.xml"})
public class IMPServiceImplTest {
    IMPManager mockImp;
    DBSession mockSession;
    Transaction mockTrans;
    CoreManager mockCore;
    CommandAction mockCmdAxn;
    IncomingMessageDAO mockMsgDao;
    IncomingMessageParser mockParser;

    IMPServiceImpl instance;

    public IMPServiceImplTest() {
    }

    @Before
    public void setUp() {
        mockCore = createMock(CoreManager.class);
        mockCmdAxn = createMock(CommandAction.class);
        mockParser = createMock(IncomingMessageParser.class);
        mockMsgDao = createMock(IncomingMessageDAO.class);
        mockSession = createMock(DBSession.class);
        mockTrans = createMock(Transaction.class);
        mockImp = createMock(IMPManager.class);

        instance = new IMPServiceImpl();
        instance.setParser(mockParser);
        instance.setCoreManager(mockCore);
        instance.setImpManager(mockImp);
        instance.setCharsPerSMS(160);
    }

    /**
     * Test of processRequest method, of class IMPServiceImpl.
     */
    @Test
    public void testProcessRequest() {
        System.out.println("processRequest");
        String message = "Type=EditPatient\nCHPSID=123\nPatientRegNum=123";
        String requesterPhone = "000000000000";
        String expResult = "Form entry cancelled";
        Map<String, CommandAction> caMap = new HashMap<String, CommandAction>();
        caMap.put("TYPE", mockCmdAxn);
        instance.setCmdActionMap(caMap);

        IncomingMessage inMsg = new IncomingMessageImpl();
        inMsg.setIncomingMessageForm(new IncomingMessageFormImpl());
        inMsg.getIncomingMessageForm().setIncomingMsgFormDefinition(new IncomingMessageFormDefinitionImpl());
        inMsg.getIncomingMessageForm().getIncomingMsgFormDefinition().setSendResponse(false);

        IncomingMessageResponseImpl response = new IncomingMessageResponseImpl();
        response.setContent(expResult);

//        expect(
//                mockCore.createMotechContext()
//                ).andReturn(new MotechContextImpl());
        expect(
                mockCore.createIncomingMessageDAO()
                ).andReturn(mockMsgDao);
        expect(
                mockCore.createIncomingMessageResponse()
                ).andReturn(new IncomingMessageResponseImpl());
        expect(
                mockMsgDao.getByContentNonDuplicatable((String)anyObject())
                ).andReturn(null);
        expect(
                mockParser.parseRequest((String) anyObject())
                ).andReturn(inMsg);
        expect(
                mockParser.getCommand((String)anyObject())
                ).andReturn("Type");
//        expect(
//                mockMsgDao.getDBSession()
//                ).andReturn(mockSession);
//        expect(
//                mockSession.getTransaction()
//                ).andReturn(mockTrans);

//        mockTrans.begin();
        expectLastCall();

        expect(
                mockMsgDao.save((IncomingMessage) anyObject())
                ).andReturn(null);

//        mockTrans.commit();
        expectLastCall();
        
        expect(
                mockCmdAxn.execute((IncomingMessage) anyObject(), (String) anyObject())
                ).andReturn(response);

//        replay(mockParser, mockCore, mockMsgDao, mockSession, mockTrans, mockImp, mockCmdAxn);
        replay(mockParser, mockCore, mockMsgDao, mockImp, mockCmdAxn);
        IncomingMessageResponse result = instance.processRequest(message, requesterPhone, false);
//        verify(mockParser, mockCore, mockMsgDao, mockSession, mockTrans, mockImp, mockCmdAxn);
        verify(mockParser, mockCore, mockMsgDao, mockImp, mockCmdAxn);

        assertEquals(expResult, result.getContent());

        //Test duplicate message detection
//        reset(mockParser, mockCore, mockMsgDao, mockSession, mockTrans, mockImp, mockCmdAxn);
        reset(mockParser, mockCore, mockMsgDao, mockImp, mockCmdAxn);

        String duplicateResp = "Error:\nThis form has already been processed!";

        inMsg.setContent(message);
        inMsg.getIncomingMessageForm().getIncomingMsgFormDefinition().setDuplicatable(Duplicatable.DISALLOWED);
        inMsg.getIncomingMessageForm().setMessageFormStatus(IncMessageFormStatus.SERVER_VALID);
        
//        expect(
//                mockCore.createMotechContext()
//                ).andReturn(new MotechContextImpl());
        expect(
                mockCore.createIncomingMessageDAO()
                ).andReturn(mockMsgDao);
        expect(
                mockCore.createIncomingMessageResponse()
                ).andReturn(new IncomingMessageResponseImpl());
        expect(
                mockMsgDao.getByContentNonDuplicatable((String)anyObject())
                ).andReturn(inMsg);

//        replay(mockParser, mockCore, mockMsgDao, mockSession, mockTrans, mockImp, mockCmdAxn);
        replay(mockParser, mockCore, mockMsgDao, mockImp, mockCmdAxn);
        result = instance.processRequest(message, requesterPhone, false);
//        verify(mockParser, mockCore, mockMsgDao, mockSession, mockTrans, mockImp, mockCmdAxn);
        verify(mockParser, mockCore, mockMsgDao, mockImp, mockCmdAxn);

        assertEquals(instance.getFormProcessSuccess(), result.getContent());
    }

    @Test
    public void testFormatPhoneNumber(){
        String number = "0244000000";
        String expResult = "233244000000";

        instance.setDefaultCountryCode("233");
        instance.setLocalNumberExpression("0[0-9]{9}");
        String result = instance.formatPhoneNumber(number);        
        assertEquals(result, expResult);

        number = "1234567890";
        result = instance.formatPhoneNumber(number);
        assertEquals(number, result);

        number = "01234567890";
        result = instance.formatPhoneNumber(number);
        assertEquals(number, result);
    }
}