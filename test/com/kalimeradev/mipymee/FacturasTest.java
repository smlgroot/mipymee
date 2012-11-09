package com.kalimeradev.mipymee;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	//@Test
	public void saveFactura() {
		FacturasServiceImpl service = new FacturasServiceImpl();
		Factura factura = new Factura();
		factura.setRfc("rfc");
		factura.setIva(10.10);
		factura.setTotal(1000.10);

		Long res = service.saveFactura(factura, "test@example.com");
		assertNotNull (res);
	}
	@Test
	public void retrieveFEchas() {
		FacturasServiceImpl service = new FacturasServiceImpl();
		Factura factura = new Factura();
		factura.setRfc("rfc");
		factura.setIva(10.10);
		factura.setTotal(1000.10);

		factura.setFecha(new Date(2010,01,01));
		service.saveFactura(factura, "test@example.com");
		
		factura.setFecha(new Date(2010,02,01));
		service.saveFactura(factura, "test@example.com");
		
		factura.setFecha(new Date(2012,02,01));
		service.saveFactura(factura, "test@example.com");
		
		Map<Long, Long[]> map = service.retrieveFechas("test@example.com");
		assertNotNull(map);
		System.out.println(map.toString());
		
	}
}
