package com.kalimeradev.mipymee.server;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
		Key key = tom.getKey();
		Query query = new Query("Factura").setAncestor(key);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		result = new Factura[results.size()];
		int i = 0;
		for (Entity r : results) {
			result[i] = new Factura();
			result[i].setId(r.getKey().getId());
			result[i].setRfc((String) r.getProperty("rfc"));
			result[i].setIva((Double) r.getProperty("iva"));
			result[i].setTotal((Double) r.getProperty("total"));
			result[i].setFecha((Date) r.getProperty("fecha"));
			i++;
		}
		// ///

		return result;
	}

	// public Factura retrieveFacturaByFacturaId(String facturaId) {
	// Factura result = null;
	// Key facturaKey = KeyFactory.createKey("Factura", facturaId);
	//
	// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	// Entity entity = null;
	// try {
	// entity = datastore.get(facturaKey);
	// } catch (EntityNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (entity != null) {
	// result = new Factura();
	//
	// result.setRfc((String) entity.getProperty("rfc"));
	// result.setIva((Double) entity.getProperty("iva"));
	// result.setTotal((Double) entity.getProperty("total"));
	// }
	//
	// return result;
	// }

	public Long saveFactura(Factura factura, String clienteId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key clienteKey = KeyFactory.createKey("User", clienteId);
		Entity entityFactura = null;
		Entity entityFechaYear = null;
		Entity entityFechaMonth = null;

		// Factura
		if (factura.getId() != null) {
			entityFactura = new Entity("Factura", factura.getId(), clienteKey);
		} else {
			entityFactura = new Entity("Factura", clienteKey);
		}
		entityFactura.setProperty("rfc", factura.getRfc());
		entityFactura.setProperty("iva", factura.getIva());
		entityFactura.setProperty("total", factura.getTotal());
		entityFactura.setProperty("fecha", factura.getFecha());

		// ANIO
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(factura.getFecha());
		entityFechaYear = new Entity("FechaYear", calendar.get(Calendar.YEAR), clienteKey);
		entityFechaMonth = new Entity("FechaMonth", calendar.get(Calendar.MONTH) + 1, entityFechaYear.getKey());
		//
		datastore.put(entityFactura);
		datastore.put(entityFechaYear);
		datastore.put(entityFechaMonth);

		return entityFactura.getKey().getId();
	}

	public Map<Long, Long[]> retrieveFechas(String clienteId) {
		Map<Long, Long[]> map = new HashMap<Long, Long[]>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity cliente = new Entity("User", clienteId);
		Key clienteKey = cliente.getKey();
		Query queryFechaYear = new Query("FechaYear").setAncestor(clienteKey);
		List<Entity> resultsFechaYear = datastore.prepare(queryFechaYear).asList(FetchOptions.Builder.withDefaults());
		for (Entity resultFechaYear : resultsFechaYear) {
			System.out.println("FechaYear:" + resultFechaYear.getKey().getId());

			Query queryFechaMonth = new Query("FechaMonth").setAncestor(resultFechaYear.getKey());
			List<Entity> resultsFechaMonth = datastore.prepare(queryFechaMonth).asList(FetchOptions.Builder.withDefaults());
			Long[] months = new Long[resultsFechaMonth.size()];
			int i = 0;
			for (Entity resultFechaMonth : resultsFechaMonth) {
				System.out.println("	FechaMonth:" + resultFechaMonth.getKey().getId());
				months[i] = resultFechaMonth.getKey().getId();
				i++;
			}

			map.put(resultFechaYear.getKey().getId(), months);
		}

		return map;
	}

}
