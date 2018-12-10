package br.jus.trf2.sistemaprocessual;

import br.jus.trf2.sistemaprocessual.ISistemaProcessual.IPeticaoIntercorrenteTiposGet;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrenteTiposGetRequest;
import br.jus.trf2.sistemaprocessual.ISistemaProcessual.PeticaoIntercorrenteTiposGetResponse;

public class PeticaoIntercorrenteTiposGet implements IPeticaoIntercorrenteTiposGet {

	@Override
	public void run(PeticaoIntercorrenteTiposGetRequest req, PeticaoIntercorrenteTiposGetResponse resp)
			throws Exception {
	}

	@Override
	public String getContext() {
		return "obter lista de tipos de petição intercorrente";
	}
}
