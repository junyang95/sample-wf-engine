package com.lrm.workflow_svc.init;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.TaskDefinition;
import com.lrm.workflow_svc.enums.LRMTaskName;
import com.lrm.workflow_svc.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @Override
    public void run(String... args) throws Exception {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setName("LRM_FIVE_WF");
        processDefinition.setDescription("LRM 5 level workflow");
        processDefinition.setVersion(1);

        processDefinitionRepository.save(processDefinition);

        List<TaskDefinition> taskDefinitions = new ArrayList<>();
        TaskDefinition taskDefinition1 = new TaskDefinition();
        taskDefinition1.setName(LRMTaskName.REVIEW_INITIATED);
        taskDefinition1.setProcessDefinition(processDefinition);
        taskDefinition1.setNodeType(LRMTaskName.REVIEW_INITIATED.getNodeType());
        taskDefinitions.add(taskDefinition1);

        TaskDefinition taskDefinition2 = new TaskDefinition();
        taskDefinition2.setName(LRMTaskName.PENDING_ENTITY_RISK_MGR_REVIEW);
        taskDefinition2.setProcessDefinition(processDefinition);
        taskDefinition2.setNodeType(LRMTaskName.PENDING_ENTITY_RISK_MGR_REVIEW.getNodeType());
        taskDefinitions.add(taskDefinition2);

        TaskDefinition taskDefinition3 = new TaskDefinition();
        taskDefinition3.setName(LRMTaskName.PENDING_QR_REVIEW);
        taskDefinition3.setProcessDefinition(processDefinition);
        taskDefinition3.setNodeType(LRMTaskName.PENDING_QR_REVIEW.getNodeType());
        taskDefinitions.add(taskDefinition3);

        TaskDefinition taskDefinition4 = new TaskDefinition();
        taskDefinition4.setName(LRMTaskName.PENDING_REGIONAL_RISK_HEAD_REVIEW);
        taskDefinition4.setProcessDefinition(processDefinition);
        taskDefinition4.setNodeType(LRMTaskName.PENDING_REGIONAL_RISK_HEAD_REVIEW.getNodeType());
        taskDefinitions.add(taskDefinition4);

        TaskDefinition taskDefinition5 = new TaskDefinition();
        taskDefinition5.setName(LRMTaskName.PENDING_GLRM_HEAD_REVIEW);
        taskDefinition5.setProcessDefinition(processDefinition);
        taskDefinition5.setNodeType(LRMTaskName.PENDING_GLRM_HEAD_REVIEW.getNodeType());
        taskDefinitions.add(taskDefinition5);

        taskDefinitionRepository.saveAll(taskDefinitions);
    }
}