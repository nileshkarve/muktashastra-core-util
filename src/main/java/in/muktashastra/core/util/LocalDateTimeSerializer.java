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
 * @author Nilesh
 *
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		String dateStr = value.format(formatter);
		gen.writeString(dateStr);
	}
}
