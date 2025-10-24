package in.muktashastra.core.util.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Generic paged response wrapper for paginated data.
 * 
 * @param <T> the type of content in the response
 * @author Nilesh
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PagedResponse<T> implements Serializable {
    /** List of content items for current page */
    private List<T> content;
    
    /** Current page number (0-based) */
    private Integer page;
    
    /** Number of items per page */
    private Integer size;
    
    /** Total number of elements across all pages */
    private Long totalElements;
    
    /** Total number of pages */
    private Integer totalPages;
    
    /** Whether this is the first page */
    private boolean first;
    
    /** Whether this is the last page */
    private boolean last;

    /**
     * Creates a paged response with pagination metadata.
     * 
     * @param content the list of items for current page
     * @param page current page number (0-based)
     * @param size number of items per page
     * @param totalElements total number of elements
     */
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