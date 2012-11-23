package com.kalimeradev.mipymee.client.service;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kalimeradev.mipymee.client.model.BoxObject;
import com.kalimeradev.mipymee.client.model.Factura;


public interface FacturasServiceAsync {
	public void retrieveFacturasByUserId(String clienteId,BoxObject boxObject, AsyncCallback<Factura[]> callback);

	public void retrieveFechas(String clienteId, AsyncCallback<Map<Long, Long[]>> callback);

	public void saveFactura(Factura factura, String clienteId, AsyncCallback<Factura> callback);
}
