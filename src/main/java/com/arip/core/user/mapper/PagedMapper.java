package com.arip.core.user.mapper;

import com.arip.core.user.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PagedMapper {

    public <T, R> PagedResponse<R> pagedResponse(Page<T> page, Function<T, R> mapper) {

        List<R> data = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PagedResponse<>(
                data,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}