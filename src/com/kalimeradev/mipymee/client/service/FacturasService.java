package com.kalimeradev.mipymee.client.service;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kalimeradev.mipymee.client.model.BoxObject;
import com.kalimeradev.mipymee.client.model.Factura;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("facturas")
public interface FacturasService extends RemoteService {
	public Factura[] retrieveFacturasByUserId(String clienteId,BoxObject boxObject);

	public Map<Long, Long[]> retrieveFechas(String clienteId) ;
	
	public Factura saveFactura(Factura factura, String clienteId);
}
