package com.dreamoval.motech.web.webservices;

import static org.easymock.EasyMock.*;

import java.util.Date;
import java.util.List;

import com.dreamoval.motech.omi.manager.OMIManager;
import com.dreamoval.motech.omi.service.ContactNumberType;
import com.dreamoval.motech.omi.service.MessageType;
import com.dreamoval.motech.omi.service.OMIService;
import com.dreamoval.motech.omi.service.PatientImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 * Unit test for the MessagingServiceImpl class
 *
 * @author Kofi A. Asamoah (yoofi@dreamoval.com)
 * Date Created Aug 10, 2009
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/webapp-config.xml"})
public class MessageServiceImplTest{

    OMIManager mockOMI;
    OMIService mockOMIService;

    @Autowired
    MessageServiceImpl messageWebServiceBean;

    public MessageServiceImplTest() {
    }

    @Before
    public void setUp(){
        mockOMI = createMock(OMIManager.class);
        mockOMIService = createMock(OMIService.class);
        messageWebServiceBean.setOmiManager(mockOMI);

        expect(
               mockOMI.createOMIService()
               ).andReturn(mockOMIService);
    }

    /**
     * Test of sendPatientMessage method, of class MessageServiceImpl.
     */
    @Test
    public void testSendPatientMessage() {
        System.out.println("sendPatientMessage");
        Long messageId = 0L;
        String clinic = "Test clinic";
        Date serviceDate = null;
        String patientNumber = "000000000000";
        ContactNumberType patientNumberType = ContactNumberType.PERSONAL;
        MessageType messageType = MessageType.TEXT;

        expect(
                mockOMIService.sendPatientMessage(anyLong(),
                                                    (String) anyObject(),
                                                    (Date) anyObject(),
                                                    (String) anyObject(),
                                                    (ContactNumberType) anyObject(),
                                                    (MessageType) anyObject())
                ).andReturn(1L);
        replay(mockOMI, mockOMIService);
        
        MessageServiceImpl instance = messageWebServiceBean;
        Long result = instance.sendPatientMessage(messageId, clinic, serviceDate, patientNumber, patientNumberType, messageType);
        assertNotNull(result);
        verify(mockOMI, mockOMIService);
    }

    /**
     * Test of sendCHPSMessage method, of class MessageServiceImpl.
     */
    @Test
    public void testSendCHPSMessage() {
        System.out.println("sendCHPSMessage");
        Long messageId = 0L;
        String workerName = "Test worker";
        String workerNumber = "000000000000";
        List<PatientImpl> patientList = null;

        expect(
                mockOMIService.sendCHPSMessage(anyLong(),
                                                (String) anyObject(),
                                                (String) anyObject(),
                                                (List<PatientImpl>) anyObject())
                ).andReturn(1L);     
        replay(mockOMI, mockOMIService);
        
        MessageServiceImpl instance = messageWebServiceBean;
        Long result = instance.sendCHPSMessage(messageId, workerName, workerNumber, patientList);
        assertNotNull(result);
        verify(mockOMI, mockOMIService);
    }

}
