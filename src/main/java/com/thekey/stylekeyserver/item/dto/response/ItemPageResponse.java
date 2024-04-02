package com.thekey.stylekeyserver.item.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thekey.stylekeyserver.item.entity.Item;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class ItemPageResponse {

    private List<ItemResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    @Builder
    private ItemPageResponse(List<ItemResponse> content, int pageNo, int pageSize, long totalElements, int totalPages,
                             boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public static ItemPageResponse of(List<ItemResponse> content, Page<Item> itemPage) {
        return ItemPageResponse.builder()
                .content(content)
                .pageNo(itemPage.getNumber())
                .pageSize(itemPage.getSize())
                .totalElements(itemPage.getTotalElements())
                .totalPages(itemPage.getTotalPages())
                .last(itemPage.isLast())
                .build();
    }
}
