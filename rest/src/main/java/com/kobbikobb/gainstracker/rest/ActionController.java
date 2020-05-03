package com.kobbikobb.gainstracker.rest;

import com.kobbikobb.gainstracker.repository.ActionEntity;
import com.kobbikobb.gainstracker.repository.ActionLogEntity;
import com.kobbikobb.gainstracker.repository.ActionLogRepository;
import com.kobbikobb.gainstracker.repository.ActionRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/actions")
@ResponseBody
public class ActionController {

    private ActionRepository actionRepository;
    private ActionLogRepository actionLogRepository;

    @Autowired
    public ActionController(ActionRepository actionRepository,
                            ActionLogRepository actionLogRepository) {
        this.actionRepository = actionRepository;
        this.actionLogRepository = actionLogRepository;
    }

    @GetMapping
    public List<ActionBean> getActions() {
        return ActionBean.fromEntities( actionRepository.findAll());
    }

    @PostMapping
    public ActionBean createAction(@RequestBody CreateActionBean createActionBean) {
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setName(createActionBean.getName());
        actionEntity.setUnit(createActionBean.getUnit());

        actionRepository.save(actionEntity);
        actionRepository.flush();

        return ActionBean.fromEntity(actionEntity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAction(@PathVariable String id) {
        ActionEntity actionEntity = actionRepository.getOne(Long.parseLong(id));
        actionRepository.delete(actionEntity);
        actionRepository.flush();
    }

    @GetMapping("{id}/logs")
    public List<ActionLogBean> getActionLogs(@PathVariable String id) {
        return ActionLogBean.fromEntities(actionLogRepository.findByActionId(
                Long.parseLong(id)
        ));
    }

    @PostMapping("{id}/logs")
    public ActionLogBean createActionLog(@PathVariable String id,
                                           @RequestBody CreateActionLogBean createActionLogBean) {

        ActionEntity actionEntity = actionRepository.getOne(Long.parseLong(id));
        ActionLogEntity actionLogEntity = new ActionLogEntity();
        actionLogEntity.setValue(createActionLogBean.getValue());
        actionLogEntity.setDate(LocalDate.parse(createActionLogBean.getDate()));
        actionLogEntity.setAction(actionEntity);

        actionLogRepository.save(actionLogEntity);
        actionLogRepository.flush();

        return ActionLogBean.fromEntity(actionLogEntity);
    }

    @DeleteMapping("{actionId}/logs/{id}")
    public ActionLogBean deleteActionLog(@PathVariable String actionId, @PathVariable String id) {
        ActionLogEntity actionLogEntity = actionLogRepository.getOne(Long.parseLong(id));
        actionLogRepository.delete(actionLogEntity);
        actionLogRepository.flush();

        return ActionLogBean.fromEntity(actionLogEntity);
    }
}
