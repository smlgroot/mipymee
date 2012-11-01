package com.kalimeradev.mipymee.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kalimeradev.mipymee.client.model.Factura;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("facturas")
public interface FacturasService extends RemoteService {
	public Factura[] retrieveFacturasByRfc(String rfc) ;

	public Boolean saveFactura(Factura factura,String clienteId) ;
}
