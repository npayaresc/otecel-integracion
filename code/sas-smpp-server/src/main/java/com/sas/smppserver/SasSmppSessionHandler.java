package com.sas.smppserver;

import java.lang.ref.WeakReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;


public class SasSmppSessionHandler extends DefaultSmppSessionHandler {

	private static final Logger logger = LoggerFactory.getLogger(SasSmppSessionHandler.class);
	
	private WeakReference<SmppSession> sessionRef;

	public SasSmppSessionHandler(SmppSession session) {
		this.sessionRef = new WeakReference<SmppSession>(session);
	}

	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		SmppSession session = sessionRef.get();
		// mimic how long processing could take on a slower smsc
			try {
				// Thread.sleep(50);
			} catch (Exception e) {
		}
		return pduRequest.createResponse();
	}
}