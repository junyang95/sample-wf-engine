package com.lrm.workflow_svc.util;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.enums.ProcessInstanceStatus;

import java.util.Date;

public class ProcessInstanceFactory {

    public static ProcessInstance createProcessInstance(ProcessDefinition processDefinition, String initiator) {
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setInitiator(initiator);

        return startProcessInstance(processInstance);
    }

    private static ProcessInstance startProcessInstance(ProcessInstance processInstance) {
        if (!processInstance.getStatus().equals(ProcessInstanceStatus.READY_TO_START)) {
            throw new RuntimeException("A Process Instance can only be started if it is in the READY_TO_START status");
        }

        processInstance.setStatus(ProcessInstanceStatus.STARTED);
        processInstance.setStartedAt(new Date());

        return processInstance;
    }
}
