package org.jsp.SuperMarket.helper;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomerId implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String randomnumber=String.valueOf((int)(Math.random()*1000));
		return "cid"+randomnumber;
	}
	

}
