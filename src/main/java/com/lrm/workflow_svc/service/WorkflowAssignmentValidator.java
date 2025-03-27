package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskInstance;
import com.lrm.workflow_svc.entity.Transition;
import com.lrm.workflow_svc.enums.TransitionAction;

public interface WorkflowAssignmentValidator {
    void validateStartPermission(ProcessDefinition processDefinition, String initiator, LRMProcessParams params);
    void validateAbortPermission(ProcessInstance processInstance, String operatorId, LRMProcessParams params);
    void validateTaskStart(TaskInstance taskInstance, String operatorId, LRMProcessParams params);
    void validateTransition(Transition taskInstance, String operatorId, TransitionAction action, LRMProcessParams params);
}
