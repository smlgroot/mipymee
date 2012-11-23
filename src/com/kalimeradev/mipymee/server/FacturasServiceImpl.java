package com.kalimeradev.mipymee.server;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kalimeradev.mipymee.client.model.BoxObject;
import com.kalimeradev.mipymee.client.model.Factura;
import com.kalimeradev.mipymee.client.service.FacturasService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class FacturasServiceImpl extends RemoteServiceServlet implements FacturasService {

	public Factura[] retrieveFacturasByUserId(String clienteId, BoxObject boxObject) {
		Factura[] result = null;

		// ////
		Entity clienteEntity = new Entity("User", clienteId);
		Key clienteKey = clienteEntity.getKey();
		Query query = new Query("Factura").setAncestor(clienteKey);
		Filter filter = null;
		if ((boxObject.getYear() != null && boxObject.getYear() >= 0) && (boxObject.getMonth() != null && boxObject.getMonth() <= 0)) {
			filter = new FilterPredicate("year", FilterOperator.EQUAL, boxObject.getYear());
		} else if ((boxObject.getYear() != null && boxObject.getYear() >= 0) && (boxObject.getMonth() != null && boxObject.getMonth() > 0)) {
			FilterPredicate filterA = new FilterPredicate("year", FilterOperator.EQUAL, boxObject.getYear());
			FilterPredicate filterB = new FilterPredicate("month", FilterOperator.EQUAL, boxObject.getMonth());
			filter = CompositeFilterOperator.and(filterA, filterB);
		}
		query.setFilter(filter);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		result = genFacturasArray(results);

		return result;
	}

	public int countFacturasByUserIdAndFechas(String clienteId, BoxObject boxObject) {
		int result = 0;

		// ////
		Entity clienteEntity = new Entity("User", clienteId);
		Key clienteKey = clienteEntity.getKey();
		Query query = new Query("Factura").setAncestor(clienteKey);
		Filter filter = null;
		if ((boxObject.getYear() != null && boxObject.getYear() >= 0) && (boxObject.getMonth() != null && boxObject.getMonth() <= 0)) {
			filter = new FilterPredicate("year", FilterOperator.EQUAL, boxObject.getYear());
		} else if ((boxObject.getYear() != null && boxObject.getYear() >= 0) && (boxObject.getMonth() != null && boxObject.getMonth() > 0)) {
			FilterPredicate filterA = new FilterPredicate("year", FilterOperator.EQUAL, boxObject.getYear());
			FilterPredicate filterB = new FilterPredicate("month", FilterOperator.EQUAL, boxObject.getMonth());
			filter = CompositeFilterOperator.and(filterA, filterB);
		}
		query.setFilter(filter);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		result = datastore.prepare(query).countEntities(FetchOptions.Builder.withDefaults());
		return result;
	}

	private Factura[] genFacturasArray(List<Entity> entities) {
		Factura[] result = null;
		if (entities != null && entities.size() > 0) {
			result = new Factura[entities.size()];
			int i = 0;
			for (Entity r : entities) {
				result[i] = new Factura();
				result[i].setId(r.getKey().getId());
				result[i].setRfc((String) r.getProperty("rfc"));
				result[i].setIva((Double) r.getProperty("iva"));
				result[i].setTotal((Double) r.getProperty("total"));
				result[i].setFechaFactura((Date) r.getProperty("fechaFactura"));
				result[i].setFechaMod((Date) r.getProperty("fechaMod"));
				result[i].setFechaNew((Date) r.getProperty("fechaNew"));
				i++;
			}
		}
		return result;
	}

	public Factura saveFactura(Factura factura, String clienteId) {
		Factura result = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key clienteKey = KeyFactory.createKey("User", clienteId);
		Entity entityFactura = null;
		Entity entityFechaYear = null;
		Entity entityFechaMonth = null;
		long year = 0;
		long month = 0;

		//
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(factura.getFechaFactura());
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		//
		boolean isNew = false;
		// Factura
		if (factura.getId() != null) {
			entityFactura = new Entity("Factura", factura.getId(), clienteKey);
			entityFactura.setProperty("fechaMod", new Date());
		} else {
			entityFactura = new Entity("Factura", clienteKey);
			entityFactura.setProperty("fechaNew", new Date());
			isNew = true;
		}
		factura.setYear(year);
		factura.setMonth(month);

		entityFactura.setProperty("rfc", factura.getRfc());
		entityFactura.setProperty("iva", factura.getIva());
		entityFactura.setProperty("total", factura.getTotal());
		entityFactura.setProperty("fechaFactura", factura.getFechaFactura());
		entityFactura.setProperty("year", factura.getYear());
		entityFactura.setProperty("month", factura.getMonth());

		// ANIO
		entityFechaYear = new Entity("FechaYear", year, clienteKey);
		entityFechaMonth = new Entity("FechaMonth", month, entityFechaYear.getKey());
		//
		// Check if it is a existing or new entity, if existing continue.
		if (!isNew) {
			// Check if date changed, if it changed continue.
			Key key = KeyFactory.createKey(clienteKey, "Factura", factura.getId());
			Entity entity = null;
			try {
				entity = datastore.get(key);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
			//
			if (entity != null) {
				Date fechaFactura = (Date) entity.getProperty("fechaFactura");
				if (fechaFactura.compareTo(factura.getFechaFactura()) != 0) {
					// Count facturas by last month , if result is equal or less than 1 continue.
					long lastEntityYear = (Long) entity.getProperty("year");
					long lastEntityMonth = (Long) entity.getProperty("month");
					BoxObject boxObject = new BoxObject(lastEntityYear, lastEntityMonth);
					int facturasMonth = countFacturasByUserIdAndFechas(clienteId, boxObject);
					if (facturasMonth <= 1) {
						// Delete fechaMonth, continue.
						Entity deleteEnityFechaYear = new Entity("FechaYear", lastEntityYear, clienteKey);
						Entity deleteEntityFechaMonth = new Entity("FechaMonth", lastEntityMonth, deleteEnityFechaYear.getKey());
						datastore.delete(deleteEntityFechaMonth.getKey());
						// Count facturas by changed year, if result is one or zero continue.
						boxObject.setMonth(0L);
						int facturasYear = countFacturasByUserIdAndFechas(clienteId, boxObject);
						if (facturasYear <= 1) {
							// Delete fechaYear, continue.
							datastore.delete(deleteEnityFechaYear.getKey());
						}
					}
				}
			}
		}

		//
		Transaction transaction = datastore.beginTransaction();
		datastore.put(entityFactura);
		datastore.put(entityFechaYear);
		datastore.put(entityFechaMonth);
		transaction.commit();

		// TODO clone factura.
		result = factura;
		result.setId(entityFactura.getKey().getId());

		return result;
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

	public byte[] retrieveImageFromDataStore(String id) {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		return null;
	}
}
