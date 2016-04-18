package com.blazebit.weblink.testsuite.common.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.weblink.core.model.jpa.SequenceBaseEntity;

@EntityView(SequenceBaseEntity.class)
public interface LongIdHolderView extends IdHolderView<Long> {

}
