package com.moaaz.modernhome.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Pagination<T> {
	Page<T> getAllWithPagination(Pageable pageable);
}
