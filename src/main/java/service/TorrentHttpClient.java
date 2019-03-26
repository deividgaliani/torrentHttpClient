package service;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class TorrentHttpClient {
	
	private String url;
	
	/**
	 * define a URL que será acessada para extrair o conteúdo
	 * @param url
	 */
	public TorrentHttpClient(String url) {
		this.url = url;
	}

	/** Marcação inicial para extrair magnet link */
    private final static String MARCA_INICIAL = "<a href=\"magnet:";
  
    /** Marcação final */
    private final static String MARCA_FINAL = "</a>";
    
    public String obterMagnetLink() {
    	//Criação do cliente HTTP que fará a conexão com o site
        HttpClient httpclient = new DefaultHttpClient();
        try {
        	// Definição da URL a ser utilizada
        	HttpGet httpget = new HttpGet(getUrl());
        	// Manipulador da resposta da conexão com a URL
        	ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // Resposta propriamente dita
            String html = httpclient.execute(httpget, responseHandler);
            //Retorno das dezenas, após tratamento
            return extrairMagnetLink(html);
          } catch (Exception e) {
        	  // Caso haja erro, dispara exceção.
        	  throw new RuntimeException("Um erro inesperado ocorreu.", e);
          } finally {
        	  //Destruição do cliente para liberação dos recursos do sistema.
        	  httpclient.getConnectionManager().shutdown();
          }
    }
    
    private String extrairMagnetLink(String html) {
    	// Posição inicial de onde começa o magnet link
        Integer parteInicial = html.indexOf(MARCA_INICIAL) + MARCA_INICIAL.length();
        // Posição final do magnet link
        Integer parteFinal = html.indexOf(MARCA_FINAL);
        // Substring montada com base nas posições, com remoção de espaços.
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
