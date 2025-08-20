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
 * Jackson deserializer for LocalDateTime using Muktashastra date format.
 * 
 * @author Nilesh
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MuktashastraConstant.LOCAL_DATE_TIME_FORMAT);

    /**
     * Deserializes JSON string to LocalDateTime.
     * 
     * @param p the JSON parser
     * @param ctxt the deserialization context
     * @return parsed LocalDateTime
     * @throws IOException if parsing fails
     * @throws JacksonException if JSON processing fails
     */
	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String dateStr = p.getText();
		return LocalDateTime.parse(dateStr, formatter);
	}
}
