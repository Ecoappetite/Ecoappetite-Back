package co.edu.eci.ecoappetite.server.service.motor;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.config.data.ClienteIAProperties;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteIa {

    private static final String APPLICATION_JSON_VALUE = "application/json";

    private final OkHttpClient client;
    private final ClienteIAProperties config;
  
    public String call(String prompt) throws EcoappetiteException{
        Headers cabeceras = this.headers();
        RequestBody body = this.requestBody(prompt);
        Request request = new Request.Builder()
                .url(config.getUrl())
                .post(body)
                .headers(cabeceras)
                .build();
        
        String respuestaIA = "";

        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful()) {
                respuestaIA = response.body().string();
                  
            }else{
                log.error("Mensaje" + response.message());
                log.error("esta es su respuesta " +response.code());
                log.error("Detalles del config"  + config);
                log.error("Detalles del Request"  + request);
                log.error("Detalles del Body "  + response.body().string());

            }
            
        } catch (Exception e) {
            throw new EcoappetiteException(e.getMessage());
        }
        return respuestaIA;
    }

    private Headers headers(){
        return new Headers.Builder()
                .add("Authorization", config.getBearer())
                .add("Content-Type", APPLICATION_JSON_VALUE)
                .build();
    }

    private RequestBody requestBody(String prompt){

        JSONObject raiz = new JSONObject()
                .put("model", config.getModel())
                .put("messages", new JSONArray()
                        .put(new JSONObject().put("role", "user").put("content", prompt))
                );
        
    
        return RequestBody.create(raiz.toString(), MediaType.parse(APPLICATION_JSON_VALUE));

    }

    
}
