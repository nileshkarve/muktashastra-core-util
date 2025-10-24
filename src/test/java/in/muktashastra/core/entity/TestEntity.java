package in.muktashastra.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.persistence.model.PersistableEntity;
import in.muktashastra.core.persistence.model.Status;
import in.muktashastra.core.persistence.relationalstore.annotation.DatabaseColumn;
import in.muktashastra.core.persistence.relationalstore.annotation.RelationalDatabaseEntity;
import in.muktashastra.core.persistence.util.JavaConversionType;
import in.muktashastra.core.util.constant.CoreConstant;
import in.muktashastra.core.util.serialization.InstantDeserializer;
import in.muktashastra.core.util.serialization.InstantSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@RelationalDatabaseEntity(tableName = "test_entity")
@ToString
public class TestEntity implements PersistableEntity {

    @DatabaseColumn(name = "id", javaConversionType = JavaConversionType.ENTITY_ID, primaryKey = true)
    private EntityId id;

    @DatabaseColumn(name = "name", javaConversionType = JavaConversionType.STRING, nullable = false, indexSequenceNumber = 1)
    private String name;

    @DatabaseColumn(name = "description", javaConversionType = JavaConversionType.STRING)
    private String description;

    @DateTimeFormat(pattern = CoreConstant.LOCAL_DATE_FORMAT)
    @JsonFormat(pattern = CoreConstant.LOCAL_DATE_FORMAT)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DatabaseColumn(name = "procure_date", javaConversionType = JavaConversionType.LOCAL_DATE)
    private LocalDate procureDate;

    @DateTimeFormat(pattern = CoreConstant.LOCAL_DATE_TIME_FORMAT)
    @JsonFormat(pattern = CoreConstant.LOCAL_DATE_TIME_FORMAT)
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    @DatabaseColumn(name = "create_timestamp", javaConversionType = JavaConversionType.INSTANT)
    private Instant createTimestamp;

    @DatabaseColumn(name = "price", javaConversionType = JavaConversionType.BIG_DECIMAL)
    private BigDecimal price;

    @DatabaseColumn(name = "status", javaConversionType = JavaConversionType.ENTITY_STATUS, statusColum = true, nullable = false)
    private Status status;

    @DatabaseColumn(name = "active", javaConversionType = JavaConversionType.BOOLEAN, nullable = false)
    private Boolean active;

    @DatabaseColumn(name = "category", javaConversionType = JavaConversionType.STRING, nullable = false)
    private String category;

    @DatabaseColumn(name = "rating", javaConversionType = JavaConversionType.INTEGER, nullable = false)
    private Integer rating;
}