package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TorrentHttpClient {
	
	private String url;   
	
	/** @param url -> define a URL que sera acessada para extrair o conteudo */
	public TorrentHttpClient(String url) {
		this.url = url;
	}

	/** Marcacao inicial para extrair magnet link */
    private final static String MARCA_INICIAL = "href=\"magnet:";
  
    /** Marcacao final */
    private final static String MARCA_FINAL = "\"";
    
    public List<String> obterMagnetLinks(){
    	String html = httpGet();
    	return extrairConteudoPagina(html, MARCA_INICIAL, MARCA_FINAL);
    }
    
    /**
     * metodo responsavel por realizar requisicao http em uma pagina
     * e extrair todo o html
     * @return String -> retorna todo o html da URL passada por parametro no construtor
     */
    public String httpGet() {
    	/**Criacao do cliente HTTP que fara a conexao com o site*/
        HttpClient httpclient = HttpClients.createDefault();
        try {
        	/** Definicao da URL a ser utilizada*/
        	HttpGet httpget = new HttpGet(getUrl());
        	/** Manipulador da resposta da conexao com a URL*/
        	ResponseHandler<String> responseHandler = new BasicResponseHandler();
            /** Executa request*/
            String html = httpclient.execute(httpget, responseHandler);
            return html;
          } catch (Exception e) {
        	  System.err.println(e.getMessage());
        	  return "";
          } finally {
        	  /**Destruicao do cliente para liberacao dos recursos do sistema.*/
        	  httpclient.getConnectionManager().shutdown();
          }
    }
    
    /**
     *metodo responsavel por extrair informacao da pagina
     * @param initPos posicao minima de busca na pagina
     * @param marcaInicial marcacao inicial de um trecho a ser buscado no html
     * @param marcaFinal marcacao final de um trecho a ser buscado no html
     * @return -> List<String> -> lista de couteudo que contem marcaIncial e marcaFinal
     */
    public List<String> extrairConteudoPagina(String html, final String marcaInicial, final String marcaFinal) {
    	Integer initPos = 0;
    	List<String>listaLinks = new ArrayList<String>();
    	while(html.contains(marcaInicial)) {
    		/** Posicao inicial de onde comeca o magnet link ( + 6 para remover href=" )*/
            Integer parteInicial = html.indexOf(marcaInicial, initPos) + 6;
            if (parteInicial < 0) {
            	throw new RuntimeException("Nenhum link encontrado");
            }
            /** Posicao final do magnet link*/
            Integer parteFinal = parteInicial + html.substring(parteInicial).indexOf(marcaFinal);
            /** Substring montada com base nas posicoes, com remocao de espacos.
             * adiciona na lista de links*/
            initPos = parteInicial;
            listaLinks.add( html.substring(parteInicial, parteFinal));
            html = html.replaceFirst(marcaInicial, "");
    	}
    	
        return listaLinks;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
		
}
