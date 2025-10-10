package in.muktashastra.core.persistence;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

public final class EntityId implements Serializable {

    private final UUID id;
    private final int hash;

    private EntityId(UUID id) {
        this.id = id;
        this.hash = Objects.hash(id);
    }

    public static EntityId generate() {
        return new EntityId(UUID.randomUUID());
    }

    public static EntityId fromUUID(UUID uuid) {
        return new EntityId(uuid);
    }

    public static EntityId fromString(String uuidString) {
        return new EntityId(UUID.fromString(uuidString));
    }

    public static EntityId fromBytes(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            throw new IllegalArgumentException("Invalid UUID bytes");
        }
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        return new EntityId(new UUID(buf.getLong(), buf.getLong()));
    }

    public byte[] toBytes() {
        ByteBuffer buf = ByteBuffer.allocate(16);
        buf.putLong(id.getMostSignificantBits());
        buf.putLong(id.getLeastSignificantBits());
        return buf.array();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public UUID toUUID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof EntityId other && id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
