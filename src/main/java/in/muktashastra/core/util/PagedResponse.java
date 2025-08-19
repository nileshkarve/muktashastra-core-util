package in.muktashastra.core.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Nilesh
 *
 */
@Getter
@Setter
@ToString
public class PagedResponse<T> {
    private List<T> content;
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
    private boolean first;
    private boolean last;

    public PagedResponse(List<T> content, int page, int size, Long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = page == 0;
        this.last = page >= totalPages - 1;
    }
}