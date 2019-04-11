package service;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TorrentHttpClient {
	
	private String url;
	
	/**
	 * define a URL que sera acessada para extrair o conteudo
	 * @param url
	 */
	public TorrentHttpClient(String url) {
		this.url = url;
	}

	/** Marcacao inicial para extrair magnet link */
    private final static String MARCA_INICIAL = "href=\"magnet:";
  
    /** Marcacao final */
    private final static String MARCA_FINAL = "\"";
    
    public String obterMagnetLink() {
    	//Criacao do cliente HTTP que fara a conexao com o site
        HttpClient httpclient = HttpClients.createDefault();
        try {
        	// Definicao da URL a ser utilizada
        	HttpGet httpget = new HttpGet(getUrl());
        	// Manipulador da resposta da conexao com a URL
        	ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // Executa request
            String html = httpclient.execute(httpget, responseHandler);
            //Retorno do link, apos tratamento
            return extrairMagnetLink(html);
          } catch (Exception e) {
        	  throw new RuntimeException("Um erro inesperado ocorreu.", e);
          } finally {
        	  //Destruicao do cliente para liberacao dos recursos do sistema.
        	  httpclient.getConnectionManager().shutdown();
          }
    }
    
    private String extrairMagnetLink(String html) {
    	System.err.println(html);
    	// Posicao inicial de onde comeca o magnet link ( + 6 para remover href=" )
        Integer parteInicial = html.indexOf(MARCA_INICIAL) + 6;
        // Posicaoo final do magnet link
        //searchIndex = preIndex + string.substring(preIndex).indexOf(searchString);
        Integer parteFinal = parteInicial + html.substring(parteInicial).indexOf(MARCA_FINAL);
//        Integer parteFinal = html.indexOf(MARCA_FINAL);
        if(parteFinal < parteInicial) {
        	return "Erro ao tentar encontrar link";
        }
        // Substring montada com base nas posicoes, com remocao de espacos.
        String extracao = html.substring(parteInicial, parteFinal);
        return extracao;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
