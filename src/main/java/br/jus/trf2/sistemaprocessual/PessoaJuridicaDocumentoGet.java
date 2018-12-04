package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPessoaJuridicaDocumentoGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PessoaJuridicaDocumentoGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PessoaJuridicaDocumentoGetResponse;

public class PessoaJuridicaDocumentoGet implements IPessoaJuridicaDocumentoGet {

	@Override
	public void run(PessoaJuridicaDocumentoGetRequest req, PessoaJuridicaDocumentoGetResponse resp) throws Exception {
	}

	@Override
	public String getContext() {
		return "obter documento de pessoa jurídica";
	}
}
