package com.lrm.workflow_svc.init;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.TaskDefinition;
import com.lrm.workflow_svc.entity.Transition;
import com.lrm.workflow_svc.enums.LRMRole;
import com.lrm.workflow_svc.enums.LRMTaskName;
import com.lrm.workflow_svc.enums.TransitionAction;
import com.lrm.workflow_svc.repo.*;
import com.lrm.workflow_svc.service.WorkflowEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class InitCommand implements CommandLineRunner {
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
    private WorkflowEngine workflowEngine;

    @Override
    public void run(String... args) throws Exception {

        ProcessDefinition processDefinition = defineNewProcessDefinition();

        addTaskDefinition(processDefinition);

        //
        LRMProcessParams variables = new LRMProcessParams();
        variables.setVWLRMCluster("ASIA_S");
        variables.setVWLRMProcess("CF");
        variables.setVWLRMLegalEntity("IN");
        variables.setAssessmentYear("2025");
        workflowEngine.startProcess(processDefinition.getId(), "jw94700", variables);
    }

    private ProcessDefinition defineNewProcessDefinition() {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setName("LRM_FIVE_WF");
        processDefinition.setDescription("LRM 5 level workflow");
        processDefinition.setVersion(1);

        processDefinitionRepository.save(processDefinition);
        return processDefinition;
    }

    private void addTaskDefinition(ProcessDefinition processDefinition) {
        List<TaskDefinition> taskDefinitions = new ArrayList<>();
        List<Transition> transitions = new ArrayList<>();
//        TaskDefinition taskDefinition = new TaskDefinition();
//        taskDefinition.setName(LRMTaskName.PROCESS_START);
//        taskDefinition.setProcessDefinition(processDefinition);
//        taskDefinition.setNodeType(LRMTaskName.PROCESS_START.getNodeType());
//        taskDefinitions.add(taskDefinition);

        TaskDefinition taskDefinition1 = new TaskDefinition();
        taskDefinition1.setName(LRMTaskName.REVIEW_INITIATED);
        taskDefinition1.setProcessDefinition(processDefinition);
        taskDefinition1.setNodeType(LRMTaskName.REVIEW_INITIATED.getNodeType());
        taskDefinition1.setAllowedRoles(List.of(LRMRole.ADMIN));
        taskDefinition1.setResourceAssignmentExpression("evaluator.evaluateGlrmTester(userId, params)");
        taskDefinitions.add(taskDefinition1);

        TaskDefinition taskDefinition2 = new TaskDefinition();
        taskDefinition2.setName(LRMTaskName.PENDING_ENTITY_RISK_MGR_REVIEW);
        taskDefinition2.setProcessDefinition(processDefinition);
        taskDefinition2.setNodeType(LRMTaskName.PENDING_ENTITY_RISK_MGR_REVIEW.getNodeType());
        taskDefinition2.setAllowedRoles(List.of(LRMRole.ADMIN));
        taskDefinition2.setResourceAssignmentExpression("evaluator.evaluateErm(userId, params)");
        taskDefinitions.add(taskDefinition2);


        TaskDefinition taskDefinition3 = new TaskDefinition();
        taskDefinition3.setName(LRMTaskName.PENDING_QR_REVIEW);
        taskDefinition3.setProcessDefinition(processDefinition);
        taskDefinition3.setNodeType(LRMTaskName.PENDING_QR_REVIEW.getNodeType());
        taskDefinition3.setAllowedRoles(List.of(LRMRole.ADMIN));
        taskDefinition3.setResourceAssignmentExpression("evaluator.evaluateQr(userId, params)");
        taskDefinitions.add(taskDefinition3);

        TaskDefinition taskDefinition4 = new TaskDefinition();
        taskDefinition4.setName(LRMTaskName.PENDING_REGIONAL_RISK_HEAD_REVIEW);
        taskDefinition4.setProcessDefinition(processDefinition);
        taskDefinition4.setNodeType(LRMTaskName.PENDING_REGIONAL_RISK_HEAD_REVIEW.getNodeType());
        taskDefinition4.setAllowedRoles(List.of(LRMRole.ADMIN));
        taskDefinition4.setResourceAssignmentExpression("evaluator.evaluateRegionalHead(userId, params)");
        taskDefinitions.add(taskDefinition4);

        TaskDefinition taskDefinition5 = new TaskDefinition();
        taskDefinition5.setName(LRMTaskName.PENDING_GLRM_HEAD_REVIEW);
        taskDefinition5.setProcessDefinition(processDefinition);
        taskDefinition5.setNodeType(LRMTaskName.PENDING_GLRM_HEAD_REVIEW.getNodeType());
        taskDefinition5.setAllowedRoles(List.of(LRMRole.ADMIN));
        taskDefinition5.setResourceAssignmentExpression("evaluator.evaluateGlrmHead(userId, params)");
        taskDefinitions.add(taskDefinition5);

        TaskDefinition taskDefinition6 = new TaskDefinition();
        taskDefinition6.setName(LRMTaskName.REVIEW_COMPLETED);
        taskDefinition6.setProcessDefinition(processDefinition);
        taskDefinition6.setNodeType(LRMTaskName.REVIEW_COMPLETED.getNodeType());
        taskDefinitions.add(taskDefinition6);

        taskDefinitionRepository.saveAll(taskDefinitions);

        Transition transition1to2 = new Transition();
        transition1to2.setFromTaskDefinition(taskDefinition1);
        transition1to2.setToTaskDefinition(taskDefinition2);
        transition1to2.setConditionExpression("evaluator.evaluate1to2Approve(userId, params)");
        transition1to2.setAction(TransitionAction.APPROVE);
        transition1to2.setProcessDefinition(processDefinition);
        transitions.add(transition1to2);

        Transition transition2to1 = new Transition();
        transition2to1.setFromTaskDefinition(taskDefinition2);
        transition2to1.setToTaskDefinition(taskDefinition1);
        transition2to1.setConditionExpression("evaluator.evaluate2to1Reject(userId, params)");
        transition2to1.setAction(TransitionAction.REJECT);
        transition2to1.setProcessDefinition(processDefinition);
        transitions.add(transition2to1);

        Transition transition2to3 = new Transition();
        transition2to3.setFromTaskDefinition(taskDefinition2);
        transition2to3.setToTaskDefinition(taskDefinition3);
        transition2to3.setConditionExpression("evaluator.evaluate2to3Approve(userId, params)");
        transition2to3.setAction(TransitionAction.APPROVE);
        transition2to3.setProcessDefinition(processDefinition);
        transitions.add(transition2to3);

        Transition transition3to1 = new Transition();
        transition3to1.setFromTaskDefinition(taskDefinition3);
        transition3to1.setToTaskDefinition(taskDefinition1);
        transition3to1.setConditionExpression("evaluator.evaluate3to1Reject(userId, params)");
        transition3to1.setAction(TransitionAction.REJECT);
        transition3to1.setProcessDefinition(processDefinition);
        transitions.add(transition3to1);

        Transition transition3to4 = new Transition();
        transition3to4.setFromTaskDefinition(taskDefinition3);
        transition3to4.setToTaskDefinition(taskDefinition4);
        transition3to4.setConditionExpression("evaluator.evaluate3to4Approve(userId, params)");
        transition3to4.setAction(TransitionAction.APPROVE);
        transition3to4.setProcessDefinition(processDefinition);
        transitions.add(transition3to4);

        Transition transition4to1 = new Transition();
        transition4to1.setFromTaskDefinition(taskDefinition4);
        transition4to1.setToTaskDefinition(taskDefinition1);
        transition4to1.setConditionExpression("evaluator.evaluate4to1Reject(userId, params)");
        transition4to1.setAction(TransitionAction.REJECT);
        transition4to1.setProcessDefinition(processDefinition);
        transitions.add(transition4to1);

        Transition transition4to5 = new Transition();
        transition4to5.setFromTaskDefinition(taskDefinition4);
        transition4to5.setToTaskDefinition(taskDefinition5);
        transition4to5.setConditionExpression("evaluator.evaluate4to5Approve(userId, params)");
        transition4to5.setAction(TransitionAction.APPROVE);
        transition4to5.setProcessDefinition(processDefinition);
        transitions.add(transition4to5);

        Transition transition5to1 = new Transition();
        transition5to1.setFromTaskDefinition(taskDefinition5);
        transition5to1.setToTaskDefinition(taskDefinition1);
        transition5to1.setConditionExpression("evaluator.evaluate5to1Reject(userId, params)");
        transition5to1.setAction(TransitionAction.REJECT);
        transition5to1.setProcessDefinition(processDefinition);
        transitions.add(transition5to1);

        Transition transition5to6 = new Transition();
        transition5to6.setFromTaskDefinition(taskDefinition5);
        transition5to6.setToTaskDefinition(taskDefinition6);
        transition5to6.setConditionExpression("evaluator.evaluate5to6Approve(userId, params)");
        transition5to6.setAction(TransitionAction.APPROVE);
        transition5to6.setProcessDefinition(processDefinition);
        transitions.add(transition5to6);

        this.transitionRepository.saveAll(transitions);

    }
}