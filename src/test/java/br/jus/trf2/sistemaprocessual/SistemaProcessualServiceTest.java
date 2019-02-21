package br.jus.trf2.sistemaprocessual;

import com.crivano.swaggerservlet.SwaggerTestSupport;

public class SistemaProcessualServiceTest extends SwaggerTestSupport {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getAPI() {
		return EprocServlet.class;
	}

	@Override
	protected String getPackage() {
		return "br.jus.trf2.sistemaprocessual";
	}

	public void testNothing_Simple_Success() {
		assertEquals("1", "1");
	}

}
