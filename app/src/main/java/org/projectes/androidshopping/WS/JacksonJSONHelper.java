package org.projectes.androidshopping.WS;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by mrr on 29/04/15.
 */
public class JacksonJSONHelper {
    /*
    * Inicializamos el OBjectMapper con el formato de fechas que requieren los webservices. */
    public static ObjectMapper Initialize(){
        ObjectMapper objectMapper = new ObjectMapper();
        //final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.setDateFormat(df);
        return objectMapper;
    }
}