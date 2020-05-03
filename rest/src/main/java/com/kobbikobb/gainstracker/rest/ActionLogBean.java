package com.kobbikobb.gainstracker.rest;

import com.kobbikobb.gainstracker.repository.ActionLogEntity;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ActionLogBean {
    private Long id;
    private Double value;
    private String date;

    public static List<ActionLogBean> fromEntities(Collection<ActionLogEntity> actionLogEntities) {
        return actionLogEntities.stream()
                .map(actionEntity -> fromEntity(actionEntity))
                .collect(Collectors.toList());
    }

    public static ActionLogBean fromEntity(ActionLogEntity actionLogEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ActionLogBean actionLogBean = new ActionLogBean();
        actionLogBean.id = actionLogEntity.getId();
        actionLogBean.value = actionLogEntity.getValue();
        actionLogBean.date = actionLogEntity.getDate().format(formatter);

        return actionLogBean;
    }
}
