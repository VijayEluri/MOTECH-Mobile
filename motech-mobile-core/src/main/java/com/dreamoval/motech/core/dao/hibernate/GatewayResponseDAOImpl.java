package com.dreamoval.motech.core.dao.hibernate;

import com.dreamoval.motech.core.dao.GatewayResponseDAO;
import com.dreamoval.motech.core.model.GatewayResponse;
import com.dreamoval.motech.core.model.GatewayResponseImpl;
import com.dreamoval.motech.core.model.MStatus;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/*
 * GatewayResponseDAOImpl is the implementation class of the GatewayResponseDAO interface.
 * This Class implements only GatewayResponseDAO specific persistent operation to the GatewayResponse model.
 *
 * Date :Jul 24, 2009
 * @author Joseph Djomeda (jojo@dreamoval.com)
 * @author Henry Sampson (henry@dreamoval.com)
 * 
 */
public class GatewayResponseDAOImpl extends HibernateGenericDAOImpl<GatewayResponseImpl> implements GatewayResponseDAO<GatewayResponseImpl> {

    private static Logger logger = Logger.getLogger(GatewayResponseDAOImpl.class);

    public GatewayResponseDAOImpl() {
    }

    /**
     * Retrieve the most recent GatewayResponse Object based on the request id and the fact its status is not pending nor processing
     * @param requestId the requestId to pass
     * @return GatewayResponse object
     */
    public GatewayResponse getMostRecentResponseByRequestId(String requestId) {
        logger.debug("getMostRecentResponseByRequestId");
        logger.debug(requestId);

        try {
            
            Session sess = this.getDBSession().getSession();
            Criterion notpending = Restrictions.ne("messageStatus", MStatus.PENDING);
            Criterion notprocessing = Restrictions.ne("messageStatus", MStatus.PROCESSING);
            LogicalExpression andExp = Restrictions.and(notpending, notprocessing);

            List responses = sess.createCriteria(this.getPersistentClass())
                    .add(andExp)
                    .add(Restrictions.eq("requestId", requestId))
                    .addOrder(Order.desc("dateCreated")).list();

            logger.debug(responses);

            return responses != null && responses.size() > 0 ? (GatewayResponse) responses.get(0) : null;
        } catch (HibernateException he) {

            logger.debug("Persistence or JDBC Exception in getMostRecentResponseByRequestId", he);
            return null;
        } catch (Exception ex) {
            
            logger.debug("Exception in getMostRecentResponseByRequestId", ex);
            return new GatewayResponseImpl();
        }
    }

    public GatewayResponse getByRequestIdAndTryNumber(String requestId, int tryNumber) {
        logger.debug("getByRequestIdAndTryNumber");
        logger.debug(requestId);

//        try {

            Session session = this.getDBSession().getSession();
            GatewayResponse response = null;
            String query = "from GatewayResponseImpl g where g.requestId = :reqId and g.gatewayRequest.messageStatus != :status and g.gatewayRequest.tryNumber= :trynum ";

            response = (GatewayResponse) session.createQuery(query)
                    .setParameter("reqId", requestId)
                    .setParameter("trynum", tryNumber)
                    .setParameter("status", MStatus.PENDING)
                    .setMaxResults(1)
                    .uniqueResult();
            return response;
//        } catch (HibernateException he) {
//
//            logger.error("Persistence or JDBC Exception in getByRequestIdAndTryNumber", he);
//            return null;
//        } catch (Exception ex) {
//
//            logger.error("Exception in getByRequestIdAndTryNumber", ex);
//            return null;
//        }

    }
}
