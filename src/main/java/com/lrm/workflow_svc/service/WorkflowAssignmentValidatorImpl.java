package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskInstance;
import com.lrm.workflow_svc.enums.LRMRole;
import com.lrm.workflow_svc.enums.TransitionAction;
import com.lrm.workflow_svc.repo.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowAssignmentValidatorImpl implements WorkflowAssignmentValidator {
    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;


    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private TaskDefinitionRepository taskDefinitionRepository;

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    @Autowired
    private TransitionRepository transitionRepository;

    // who can start a new process
    @Override
    public void validateStartPermission(ProcessDefinition processDefinition, String initiator, LRMProcessParams params) {

    }

    // who can abort a process
    @Override
    public void validateAbortPermission(ProcessInstance processInstance, String operatorId, LRMProcessParams params) {

    }

    @Override
    public void validateTaskStart(TaskInstance taskInstance, String operatorId, LRMProcessParams params) {

    }

    @Override
    public void validateTaskAction(TaskInstance taskInstance, String operatorId, TransitionAction action, LRMProcessParams params) {
        List<String> candidates = taskInstance.getNominateAssignees();

        if (candidates != null && !Arrays.asList(candidates).contains(operatorId)) {
            throw new SecurityException("用户 " + operatorId + " 没有权限操作该任务");
        }
    }
}
