/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamoval.motech.core.dao.hibernate;

import com.dreamoval.motech.core.dao.*;
import javax.transaction.Transaction;
import org.hibernate.Session;

/**
 *
 * see com.dreamoval.motech.core.dao.DBSession
 *
 * @author Henry Sampson
 * Date Created @date
 */
public class DBSessionImpl<S extends Session, T extends Transaction> implements DBSession {

    Session session;
    
    public DBSessionImpl(Session session){
        this.session = session;
    }
    
    /**
     * see com.dreamoval.motech.core.dao.DBSession#releaseSession()
     */
    public void releaseSession() {
        if(session != null)
        session.close();
    }

    /**
     * see com.dreamoval.motech.core.dao.DBSession#getTransaction()
     */
    public T getTransaction() {
        if(session != null)
            return (T)session.beginTransaction();
        return null;
    }

    /**
     * see com.dreamoval.motech.core.dao.DBSession#getSession()
     */
    public S getSession() {
        return (S)session;
    }

    /**
     * see com.dreamoval.motech.core.dao.DBSession#setSession()
     */
    public void setSession(Object session) {
        this.session = (Session)session;
    }
}