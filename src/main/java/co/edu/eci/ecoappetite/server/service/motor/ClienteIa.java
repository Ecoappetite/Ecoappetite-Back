package co.edu.eci.ecoappetite.server.service.motor;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.config.bean.ClienteIAConfig;
import co.edu.eci.ecoappetite.server.config.data.ClienteIAProperties;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



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
                System.err.println("Mensaje" + response.message());
                System.err.println("esta es su respuesta " +response.code());
                System.err.println("Detalles del config"  + config);
                System.err.println("Detalles del Request"  + request);
                System.err.println("Detalles del Body "  + response.body().string());

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
