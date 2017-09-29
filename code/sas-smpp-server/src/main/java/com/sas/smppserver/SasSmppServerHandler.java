package com.sas.smppserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;

public class SasSmppServerHandler implements SmppServerHandler {

	private static final Logger logger = LoggerFactory.getLogger(SasSmppServerHandler.class);

	@Override
	public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration,final BaseBind bindRequest) throws SmppProcessingException {
		// test name change of sessions
		// this name actually shows up as thread context....
		sessionConfiguration.setName("Application.SMPP." + sessionConfiguration.getSystemId());
		// throw new SmppProcessingException(SmppConstants.STATUS_BINDFAIL, null);
	}

	@Override
	public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse) throws SmppProcessingException {
		logger.info("Session created: {}", session);
		// need to do something it now (flag we're ready)
		session.serverReady(new SasSmppSessionHandler(session));
	}

	@Override
	public void sessionDestroyed(Long sessionId, SmppServerSession session) {
		logger.info("Session destroyed: {}", session);
		// print out final stats
		if (session.hasCounters()) {
			logger.info(" final session rx-submitSM: {}", session.getCounters().getRxSubmitSM());
		}
		// make sure it's really shutdown
		session.destroy();
	}

}
