package in.muktashastra.core.util.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private String code;
    private String message;
}
