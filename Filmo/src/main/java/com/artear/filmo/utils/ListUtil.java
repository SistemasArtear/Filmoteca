package com.artear.filmo.utils;

import java.util.List;

import com.artear.filmo.bo.programacion.Pagination;

public class ListUtil {

	public static <T> List<T> paging(List<T> list, Pagination pagination) {

		int total = list.size();
		int offset = pagination.getOffset();
		int limit = pagination.getLimit();
		int start = Math.min(total, offset);
		list.subList(0, start).clear();

		int size = list.size();
		int end = Math.min(limit, size);
		list.subList(end, size).clear();

		pagination.setHasNext((end < size));
		pagination.setHasPrevious((start > 0));
		pagination.setTotal(total);

		return list;
	}

}
