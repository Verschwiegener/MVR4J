package de.verschwiegener.xchange;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

/**
 * 
 * @author julius
 *
 */
public interface XChangeServer {
	
	
	public void start() throws InterruptedException, SSLException, CertificateException;
	
	public void shutdown() throws InterruptedException;

}