package com.lrm.workflow_svc.controller;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskInstance;
import com.lrm.workflow_svc.enums.ProcessInstanceStatus;
import com.lrm.workflow_svc.enums.TransitionAction;
import com.lrm.workflow_svc.repo.ProcessInstanceRepository;
import com.lrm.workflow_svc.service.WorkflowEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Validated  //To enable RequestParams and PathVariables
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private WorkflowEngine workflowEngine;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/startProcess")
    public ResponseEntity<ProcessInstance> startProcess(
            @RequestParam(value = "processId") Long processId,
            @RequestParam(value = "initiator") String initiator,
            @RequestParam(value = "le") String le,
            @RequestParam(value = "cluster") String cluster) {

        LRMProcessParams variables = new LRMProcessParams();

        variables.setVWLRMCluster(cluster);
        variables.setVWLRMProcess("CF");
        variables.setVWLRMLegalEntity(le);
        variables.setAssessmentYear("2025");

        ProcessInstance processInstance = workflowEngine.startProcess(processId, initiator, variables);

        return new ResponseEntity<>(processInstance, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/abortProcess")
    public ResponseEntity<String> abortProcess(
            @RequestParam(value = "processInstanceId") Long processInstanceId,
            @RequestParam(value = "operator") String operator) {

        workflowEngine.abortProcess(processInstanceId, operator);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/startCompleteTask")
    @Transactional
    public ResponseEntity<String> startCompleteTask(
            @RequestParam(value = "processInstanceId") Long processInstanceId,
            @RequestParam(value = "operator") String operator,
            @RequestParam(value = "action") TransitionAction action) {

        ProcessInstance processInstance = processInstanceRepository
                .findById(processInstanceId)
                .orElseThrow(() -> new RuntimeException("流程实例不存在, id: " + processInstanceId));

//        if (processInstance.getStatus() != ProcessInstanceStatus.COMPLETED) {
//            throw new RuntimeException("Process already completed");
//        }

        TaskInstance currentTask = processInstance.getCurrentTask();

        workflowEngine.startTask(currentTask.getId(), operator);

        workflowEngine.completeTask(currentTask.getId(), action, operator, new HashMap<>());

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}
