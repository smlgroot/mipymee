package com.kalimeradev.mipymee.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kalimeradev.mipymee.client.model.Factura;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface FacturasServiceAsync {
	public void retrieveFacturasByRfc(String rfc, AsyncCallback<Factura[]> callback);

	public void saveFactura(Factura factura,String clienteId, AsyncCallback<Boolean> callback) ;
}
