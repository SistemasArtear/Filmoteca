package com.artear.filmo.bo.programacion;

public class Pagination {

	private int limit;
	private int offset;
	private int total;
	private boolean hasNext;
	private boolean hasPrevious;

	public Pagination() {
		super();
	}

	public Pagination(int limit, int offset, int total, boolean hasNext,
			boolean hasPrevious) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.total = total;
		this.hasNext = hasNext;
		this.hasPrevious = hasPrevious;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

}
