package com.kalimeradev.mipymee;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.kalimeradev.mipymee.client.model.Factura;
import com.kalimeradev.mipymee.server.FacturasServiceImpl;

public class FacturasTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	// @Test
	public void testRetrieveFacturasByRfc() {
		FacturasServiceImpl service = new FacturasServiceImpl();
		Factura[] res = service.retrieveFacturasByUserId("aaaa");
		assertNotNull(res);
	}

	@Test
	public void saveFatura() {
		FacturasServiceImpl service = new FacturasServiceImpl();
		Factura factura = new Factura();
		factura.setRfc("rfc");
		factura.setIva(10.10);
		factura.setTotal(1000.10);

		String res = service.saveFactura(factura, "test@example.com");
		assertNotNull (res);
	}
}
