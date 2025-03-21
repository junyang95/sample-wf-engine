package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskInstance;

public interface WorkflowAssignmentValidator {
    void validateStartPermission(ProcessDefinition processDefinition, String initiator);
    void validateAbortPermission(ProcessInstance processInstance, String operatorId);
    void validateTaskAction(TaskInstance taskInstance, String operatorId);
}
