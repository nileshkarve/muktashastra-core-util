/**
 * 
 */
package in.muktashastra.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson serializer for LocalDateTime using Muktashastra date format.
 * 
 * @author Nilesh
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);

    /**
     * Serializes LocalDateTime to JSON string.
     * 
     * @param value the LocalDateTime to serialize
     * @param gen the JSON generator
     * @param serializers the serializer provider
     * @throws IOException if serialization fails
     */
	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		String dateStr = value.format(formatter);
		gen.writeString(dateStr);
	}
}
