package com.kobbikobb.gainstracker.rest;

import com.kobbikobb.gainstracker.repository.ActionEntity;
import com.kobbikobb.gainstracker.repository.ActionLogEntity;
import com.kobbikobb.gainstracker.repository.ActionLogRepository;
import com.kobbikobb.gainstracker.repository.ActionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    // TODO: POST
    // TODO: Beans

    @GetMapping
    public List<ActionEntity> getActions() {
        return actionRepository.findAll();
    }

    @GetMapping("{id}/logs")
    public List<ActionLogEntity> getActionLogs(@PathVariable String id) {
        return actionLogRepository.findByActionId(
                Long.parseLong(id)
        );
    }
}
