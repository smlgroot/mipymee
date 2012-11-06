package com.kalimeradev.mipymee.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kalimeradev.mipymee.client.model.Factura;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("facturas")
public interface FacturasService extends RemoteService {
	public Factura[] retrieveFacturasByUserId(String clienteId);

	public Factura retrieveFacturaByFacturaId(String facturaId);

	public String saveFactura(Factura factura, String clienteId);
}
