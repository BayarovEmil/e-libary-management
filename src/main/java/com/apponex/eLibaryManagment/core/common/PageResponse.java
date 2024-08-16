package com.apponex.eLibaryManagment.core.common;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean firstPage;
    private boolean lastPage;
}
