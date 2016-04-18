package com.blazebit.weblink.testsuite.common.view;

import com.blazebit.persistence.view.IdMapping;

public interface IdHolderView<T> {

	@IdMapping("id")
	public T getId();
}
