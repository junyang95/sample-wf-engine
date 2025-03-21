package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskInstance;
import com.lrm.workflow_svc.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class WorkflowAssignmentValidatorImpl implements WorkflowAssignmentValidator{
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

    @Override
    public void validateStartPermission(ProcessDefinition processDefinition, String initiator) {

    }

    @Override
    public void validateAbortPermission(ProcessInstance processInstance, String operatorId) {

    }

    @Override
    public void validateTaskAction(TaskInstance taskInstance, String operatorId) {
        String[] candidates = taskInstance.getAssignees();

        if (candidates != null && !Arrays.asList(candidates).contains(operatorId)) {
            throw new SecurityException("用户 " + operatorId + " 没有权限操作该任务");
        }
    }
}
