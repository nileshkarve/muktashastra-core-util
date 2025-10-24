package in.muktashastra.core.util.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import in.muktashastra.core.persistence.model.EntityId;

import java.io.IOException;

public class EntityIdSerializer extends JsonSerializer<EntityId> {

    @Override
    public void serialize(EntityId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}