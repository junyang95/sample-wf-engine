package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.entity.*;
import com.lrm.workflow_svc.enums.NodeType;
import com.lrm.workflow_svc.enums.ProcessInstanceStatus;
import com.lrm.workflow_svc.enums.TaskInstanceStatus;
import com.lrm.workflow_svc.enums.TransitionAction;
import com.lrm.workflow_svc.repo.*;
import com.lrm.workflow_svc.util.ProcessInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkflowEngineImpl implements WorkflowEngine {

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
    private WorkflowAssignmentValidator workflowAssignmentValidator;

    @Override
    public ProcessInstance startProcess(Long processDefinitionId, String initiator, LRMProcessParams variables) {
        // 根据流程定义ID查询对应的流程定义
        ProcessDefinition processDefinition = processDefinitionRepository.findById(processDefinitionId)
                .orElseThrow(() -> new RuntimeException("流程定义不存在, id: " + processDefinitionId));

        // 校验流程启动权限(这个不是jbpm考虑的，而是为了方便统一)
        workflowAssignmentValidator.validateStartPermission(processDefinition, initiator, variables);

        // 找到起始任务定义（START_NODE）
        TaskDefinition startTaskDef = processDefinition.getTaskDefinitions().stream()
                .filter(td -> td.getNodeType() == NodeType.START_NODE)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("流程定义中未包含起始节点"));

        // 创建新的流程实例
        ProcessInstance processInstance = ProcessInstanceFactory.createProcessInstance(processDefinition, initiator);

        // 创建任务实例，关联到起始任务
        TaskInstance startTaskInstance = new TaskInstance();
        startTaskInstance.setTaskDefinition(startTaskDef);
        startTaskInstance.setProcessInstance(processInstance);
        startTaskInstance.setStatus(TaskInstanceStatus.CREATED);
        startTaskInstance.setStartedAt(new Date());

        // 将任务实例添加到流程实例中
        processInstance.setProcessParams(variables);
        processInstance.setTaskInstances(new ArrayList<>());
        processInstance.getTaskInstances().add(startTaskInstance);
        processInstance.setCurrentTask(startTaskInstance);

        // 持久化流程实例，级联保存任务实例（假设 CascadeType.ALL 已配置）
        processInstanceRepository.save(processInstance);

        return processInstance;
    }


    @Override
    public void abortProcess(Long processInstanceId, String operator) {
        // 1. 根据ID查询流程实例
        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId)
                .orElseThrow(() -> new RuntimeException("流程实例不存在，id: " + processInstanceId));

        // 获取当前process的参数
        LRMProcessParams processParams = processInstance.getProcessParams();

        workflowAssignmentValidator.validateAbortPermission(processInstance, operator, processParams);

        // 校验是否允许中止（例如，流程未完成）
        if (processInstance.getStatus() == ProcessInstanceStatus.COMPLETED ||
                processInstance.getStatus() == ProcessInstanceStatus.ABORTED) {
            throw new IllegalStateException("流程已经完成或中止");
        }

        processInstance.setStatus(ProcessInstanceStatus.ABORTED);
        processInstance.setEndedAt(new Date());

        // 将所有未完成任务标记为 EXITED 或 ABORTED
        processInstance.getTaskInstances().forEach(task -> {
            if (task.getStatus() != TaskInstanceStatus.COMPLETED) {
                task.setStatus(TaskInstanceStatus.EXITED);
                task.setCompletedAt(new Date());
                task.setComments("流程由 " + operator + " 中止");
            }
        });

        processInstance.setCurrentTask(null);

        // 持久化更新
        processInstanceRepository.save(processInstance);
    }

    @Override
    public List<TaskInstance> getTasksByProcessInstanceIdStatusFormName(Long processInstanceId, String status, String formName) {
        return List.of();
    }

    @Override
    public void nominateTask(Long taskInstanceId, String operator, String nominee) {

    }


    public void startTask(Long taskInstanceId, String operator) {
        // 根据ID查询任务实例
        TaskInstance taskInstance = taskInstanceRepository.findById(taskInstanceId)
                .orElseThrow(() -> new RuntimeException("任务实例不存在，id: " + taskInstanceId));

        // 校验任务状态
        if (taskInstance.getStatus() != TaskInstanceStatus.CREATED &&
                taskInstance.getStatus() != TaskInstanceStatus.READY) {
            throw new IllegalStateException("任务当前状态不允许启动");
        }
        // 获取当前process的参数
        LRMProcessParams processParams = taskInstance.getProcessInstance().getProcessParams();

        workflowAssignmentValidator.validateTaskStart(taskInstance, operator, processParams);

        // 标记任务启动
        taskInstance.setStatus(TaskInstanceStatus.IN_PROGRESS);
        taskInstance.setActualOwnerId(operator);
        taskInstance.setStartedAt(new Date());

        // 持久化更新任务状态
        taskInstanceRepository.save(taskInstance);
    }

    @Override
    public void completeTask(Long taskInstanceId, TransitionAction action, String operator, Map<String, Object> params) {
        //        // 将任务启动（标识任务正在处理）
        //        taskService.start(taskId, userId);
        //        // 完成任务，同时传入审批结果 decision
        //        taskService.complete(taskId, userId, results);


        // 根据ID查询任务实例
        TaskInstance taskInstance = taskInstanceRepository.findById(taskInstanceId)
                .orElseThrow(() -> new RuntimeException("任务实例不存在，id: " + taskInstanceId));

        // 校验任务状态
        if (taskInstance.getStatus() != TaskInstanceStatus.IN_PROGRESS &&
                taskInstance.getStatus() != TaskInstanceStatus.READY) {
            throw new IllegalStateException("任务当前状态不允许完成");
        }
        // 获取当前process的参数
        LRMProcessParams processParams = taskInstance.getProcessInstance().getProcessParams();

        // 获取当前任务定义
        TaskDefinition currentTaskDef = taskInstance.getTaskDefinition();

        // 查找匹配的转移路径
        Transition selectedTransition = currentTaskDef.getOutgoingTransitions().stream()
                .filter(t -> t.getAction() == action)
                .filter(t -> {
                    // 如果存在条件表达式，则解析条件
                    if (t.getConditionExpression() != null && !t.getConditionExpression().isEmpty()) {
                        // 这里假设有一个ConditionEvaluator接口
                        // 预留一个可以通过反射调用的方式，动态实现条件表达式的解析
                        // params也可以传入一些业务参数，用于条件表达式的计算
                        // like is gccFilledIn()?
                        workflowAssignmentValidator.validateTaskAction(taskInstance, operator, action, processParams);
                        return true;
                    }
                    return true;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未找到匹配的转移路径"));

        // 标记当前任务完成
        taskInstance.setStatus(TaskInstanceStatus.COMPLETED);
        taskInstance.setCompletedAt(new Date());
        taskInstance.setActualOwnerId(operator);

        // 根据转移找到目标任务定义
        TaskDefinition targetTaskDef = selectedTransition.getToTaskDefinition();

        ProcessInstance processInstance = taskInstance.getProcessInstance();

        // 如果目标是结束节点，直接更新流程状态
        if (targetTaskDef.getNodeType() == NodeType.END_NODE) {
            processInstance.setStatus(ProcessInstanceStatus.COMPLETED);
            processInstance.setEndedAt(new Date());
            processInstance.setCurrentTask(null);
        } else {
            // 创建新的任务实例
            TaskInstance nextTaskInstance = new TaskInstance();
            nextTaskInstance.setTaskDefinition(targetTaskDef);
            nextTaskInstance.setProcessInstance(processInstance);
            nextTaskInstance.setStatus(TaskInstanceStatus.CREATED);
            // 根据业务需求，还可以设置分配人等属性

            processInstance.getTaskInstances().add(nextTaskInstance);
            processInstance.setCurrentTask(nextTaskInstance);
        }

        // 持久化更新流程实例和任务实例状态
        // 比如 taskInstanceRepository.save(taskInstance);
        processInstanceRepository.save(processInstance);

        // 触发任务完成或流程流转事件
        // eventPublisher.publish(new TaskCompletedEvent(taskInstance));
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
