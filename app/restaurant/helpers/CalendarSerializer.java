package restaurant.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarSerializer extends JsonSerializer<Calendar> {

    @Override
    public void serialize(Calendar calendar,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String outputJSON = dateTimeFormat.format(calendar.getTime());
        jsonGenerator.writeObject(outputJSON);
    }
}
