package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IProcessoNumeroPdfGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ProcessoNumeroPdfGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.ProcessoNumeroPdfGetResponse;

public class ProcessoNumeroPdfGet implements IProcessoNumeroPdfGet {

	@Override
	public void run(ProcessoNumeroPdfGetRequest req, ProcessoNumeroPdfGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "validar número de processo";
	}
}
