package com.lrm.workflow_svc.init;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


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
        processDefinition.setName("Test 2 Node Process");
        processDefinition.setDescription("A process with 2 nodes");
        processDefinition.setVersion(1);

        processDefinitionRepository.save(processDefinition);
    }
}