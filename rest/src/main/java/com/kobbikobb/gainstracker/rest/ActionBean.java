package com.kobbikobb.gainstracker.rest;

import com.kobbikobb.gainstracker.repository.ActionEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ActionBean {
    private Long id;
    private String name;
    private String unit;

    public static List<ActionBean> fromEntities(Collection<ActionEntity> actionEntities) {
        return actionEntities.stream()
                .map(actionEntity -> fromEntity(actionEntity))
                .collect(Collectors.toList());
    }

    public static ActionBean fromEntity(ActionEntity actionEntity) {
        ActionBean actionBean = new ActionBean();
        actionBean.id = actionEntity.getId();
        actionBean.name = actionEntity.getName();
        actionBean.unit = actionEntity.getUnit();
        return actionBean;
    }
}
