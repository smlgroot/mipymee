package com.kalimeradev.mipymee.server;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kalimeradev.mipymee.client.model.Factura;
import com.kalimeradev.mipymee.client.service.FacturasService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class FacturasServiceImpl extends RemoteServiceServlet implements FacturasService {

	public Factura[] retrieveFacturasByUserId(String clienteId) {
		Factura[] result = null;
		// ////
		Entity tom = new Entity("User", clienteId);
		Key tomKey = tom.getKey();
		Query photoQuery = new Query("Factura").setAncestor(tomKey);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Entity> results = datastore.prepare(photoQuery).asList(FetchOptions.Builder.withDefaults());
		result = new Factura[results.size()];
		int i = 0;
		for (Entity r : results) {
			result[i] = new Factura();
			result[i].setRfc((String) r.getProperty("rfc"));
			result[i].setIva((Double) r.getProperty("iva"));
			result[i].setTotal((Double) r.getProperty("total"));
			result[i].setFecha( (Date) r.getProperty("fecha"));
			i++;
		}
		// ///

		return result;
	}

	public Factura retrieveFacturaByFacturaId(String facturaId) {
		Factura result = null;
		Key facturaKey = KeyFactory.createKey("Factura", facturaId);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity entity = null;
		try {
			entity = datastore.get(facturaKey);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (entity != null) {
			result = new Factura();

			result.setRfc((String) entity.getProperty("rfc"));
			result.setIva((Double) entity.getProperty("iva"));
			result.setTotal((Double) entity.getProperty("total"));
		}

		return result;
	}

	public String saveFactura(Factura factura, String clienteId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key clienteKey = KeyFactory.createKey("User", clienteId);

		Entity entity = new Entity("Factura", clienteKey);
		entity.setProperty("rfc", factura.getRfc());
		entity.setProperty("iva", factura.getIva());
		entity.setProperty("total", factura.getTotal());
		entity.setProperty("fecha", factura.getFecha());

		datastore.put(entity);

		return entity.getKey().getName();
	}

}
