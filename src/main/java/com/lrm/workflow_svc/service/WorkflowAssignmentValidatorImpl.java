package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.dto.LRMUser;
import com.lrm.workflow_svc.entity.*;
import com.lrm.workflow_svc.enums.LRMRole;
import com.lrm.workflow_svc.enums.TransitionAction;
import com.lrm.workflow_svc.repo.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private WorkflowIdentityService workflowIdentityService;

    // who can start a new process
    @Override
    public void validateStartPermission(ProcessDefinition processDefinition, String initiator, LRMProcessParams params) {
        LRMUser user = workflowIdentityService.getUserBySoeId(initiator);

        if (Objects.isNull(user)) {
            throw new SecurityException("User not exist");
        }

        if (user.getRole() == LRMRole.ADMIN) {
            return;
        }

        String processLeCd = params.getVWLRMLegalEntity();

        if (processDefinition.getId() == 1L && user.getRole() == LRMRole.GLRM_TESTER) {
            if (user.getVWLRMLegalEntityCds().contains(processLeCd)) {
                return;
            }
        }

        if (processDefinition.getId() == 2L && user.getRole() == LRMRole.ENTITY_RISK_MANAGER) {
            if (user.getVWLRMLegalEntityCds().contains(processLeCd)) {
                return;
            }
        }


        throw new SecurityException("用户 " + initiator + " 没有权限启动流程");
    }

    // who can abort a process
    @Override
    public void validateAbortPermission(ProcessInstance processInstance, String operatorId, LRMProcessParams params) {
        LRMRole role = workflowIdentityService.getRoleByUser(operatorId);

        if (role == LRMRole.ADMIN) {
            return;
        }

        throw new SecurityException("用户 " + operatorId + " 没有权限终止流程");
    }

    @Override
    public void validateTaskStart(TaskInstance taskInstance, String operatorId, LRMProcessParams params) {
        TaskDefinition taskDefinition = taskInstance.getTaskDefinition();
        LRMUser user = workflowIdentityService.getUserBySoeId(operatorId);

        if (taskDefinition.getAllowedRoles().contains(user.getRole())) {
            return;
        }

        if (user.getVWLRMLegalEntityCds().contains(params.getVWLRMLegalEntity())) {
            return;
        }

        if (user.getVWLRMClusterCds().contains(params.getVWLRMCluster())) {
            return;
        }

        String resourceAssignmentExpression = taskDefinition.getResourceAssignmentExpression();
        // TODO: evaluate expression

        throw new SecurityException("用户 " + operatorId + " 没有权限启动任务 " + taskDefinition.getName());
    }

    @Override
    public void validateTransition(Transition transition, String operatorId, TransitionAction action, LRMProcessParams params) {
        LRMUser user = workflowIdentityService.getUserBySoeId(operatorId);

        if (transition.getAllowedRoles().contains(user.getRole())) {
            return;
        }

        if (user.getVWLRMLegalEntityCds().contains(params.getVWLRMLegalEntity())) {
            return;
        }

        if (user.getVWLRMClusterCds().contains(params.getVWLRMCluster())) {
            return;
        }

        String conditionExpression = transition.getConditionExpression();
        // TODO: evaluate expression

        throw new SecurityException("用户 " + operatorId + " 没有权限执行转移 " + action);
    }
}
