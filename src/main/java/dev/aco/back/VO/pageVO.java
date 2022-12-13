package dev.aco.back.VO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class pageVO {
    private Integer requestedPageNumber;
    private Integer requestedPageSize;
    private Integer responsedPageNumber;
    private Integer totalPageSize;
    private List<Object> list;
}
