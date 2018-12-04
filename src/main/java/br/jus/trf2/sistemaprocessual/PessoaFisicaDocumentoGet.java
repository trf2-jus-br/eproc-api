package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPessoaFisicaDocumentoGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PessoaFisicaDocumentoGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PessoaFisicaDocumentoGetResponse;

public class PessoaFisicaDocumentoGet implements IPessoaFisicaDocumentoGet {

	@Override
	public void run(PessoaFisicaDocumentoGetRequest req, PessoaFisicaDocumentoGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter documento de pessoa física";
	}
}
