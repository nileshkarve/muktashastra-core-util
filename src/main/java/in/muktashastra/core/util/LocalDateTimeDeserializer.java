/**
 * 
 */
package in.muktashastra.core.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author Nilesh
 *
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String dateStr = p.getText();
		return LocalDateTime.parse(dateStr, formatter);
	}
}
