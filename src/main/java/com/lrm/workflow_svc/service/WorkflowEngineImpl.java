package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskDefinition;
import com.lrm.workflow_svc.entity.TaskInstance;
import com.lrm.workflow_svc.enums.NodeType;
import com.lrm.workflow_svc.enums.ProcessInstanceStatus;
import com.lrm.workflow_svc.enums.TaskInstanceStatus;
import com.lrm.workflow_svc.repo.ProcessDefinitionRepository;
import com.lrm.workflow_svc.repo.ProcessInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkflowEngineImpl implements WorkflowEngine {

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;


    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Override
    public ProcessInstance startProcess(Long processDefinitionId, String initiator, Map<String, Object> variables) {
        // 1. 根据流程定义ID查询对应的流程定义
        ProcessDefinition processDefinition = processDefinitionRepository.findById(processDefinitionId)
                .orElseThrow(() -> new RuntimeException("流程定义不存在, id: " + processDefinitionId));

        // 2. 创建新的流程实例
        ProcessInstance processInstance = new ProcessInstance();
//        processInstance.setId(generateNewProcessInstanceId());
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setInitiator(initiator);
        processInstance.setStatus(ProcessInstanceStatus.RUNNING);
        processInstance.setStartedAt(new Date());

        // 3. 找到流程定义中的开始节点（假设 nodeType 为 START 的节点为开始节点）
        Optional<TaskDefinition> startTaskDefinitionOpt = processDefinition.getTaskDefinitions().stream()
                .filter(td -> td.getNodeType() == NodeType.START)
                .findFirst();

        if (startTaskDefinitionOpt.isEmpty()) {
            throw new RuntimeException("流程定义中未找到开始节点, id: " + processDefinitionId);
        }

        TaskDefinition startTaskDefinition = startTaskDefinitionOpt.get();

        // 4. 根据开始节点创建任务实例
        TaskInstance startTaskInstance = new TaskInstance();
//        startTaskInstance.setId(generateNewTaskInstanceId());
        startTaskInstance.setProcessInstance(processInstance);
        startTaskInstance.setTaskDefinition(startTaskDefinition);
        startTaskInstance.setAssignee(initiator); // 假设发起人处理开始节点
        // 这里将任务状态设置为 PENDING（后续可以根据业务需求自动完成开始节点的逻辑）
        startTaskInstance.setStatus(TaskInstanceStatus.PENDING);
        startTaskInstance.setStartedAt(new Date());

        // 5. 设置当前任务，并将任务实例加入流程实例的任务列表
        processInstance.setCurrentTask(startTaskInstance);
        processInstance.setTaskInstances(List.of(startTaskInstance));

        // 6. 持久化流程实例，级联保存任务实例（假设 CascadeType.ALL 已配置）
        processInstanceRepository.save(processInstance);

        return processInstance;
    }


    @Override
    public void abortProcess(Long processInstanceId, String operator) {

    }

    @Override
    public List<TaskInstance> getTasksByProcessInstanceIdStatusFormName(Long processInstanceId, String status, String formName) {
        return List.of();
    }

    @Override
    public void nominateTask(Long taskInstanceId, String operator, String nominee) {

    }

    @Override
    public void completeTask(Long taskInstanceId, String operator, Map<String, Object> variables) {

    }

    @Override
    public List<TaskInstance> getTaskAssignedAsPotentialOwnerByProcessId(Long processInstanceId) {
        return List.of();
    }

    @Override
    public void reworkTask(Long taskInstanceId, String operator, Map<String, Object> variables) {

    }

    @Override
    public void reassignTask(Long taskInstanceId, String operator, String newAssignee) {

    }

    @Override
    public ProcessInstance getProcessInstance(Long processInstanceId) {
        return null;
    }

    @Override
    public List<TaskInstance> getPendingTasks(String userId) {
        return List.of();
    }

}
