package project.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

import static project.util.TaskTimeFormatter.DATE_TIME_FORMATTER;
import static project.util.TaskTimeFormatter.ZERO_DATE;

public class LocalDateAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
        if (localDateTime == null) {
            jsonWriter.value(ZERO_DATE);
        } else jsonWriter.value(localDateTime.format(DATE_TIME_FORMATTER));
    }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        String in = jsonReader.nextString();
        if (!ZERO_DATE.equals(in)) {
           return LocalDateTime.parse(in, DATE_TIME_FORMATTER);
       } else return null;
   }
}