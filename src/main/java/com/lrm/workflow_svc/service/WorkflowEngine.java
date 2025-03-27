package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import com.lrm.workflow_svc.entity.ProcessInstance;
import com.lrm.workflow_svc.entity.TaskInstance;
import com.lrm.workflow_svc.enums.TransitionAction;

import java.util.List;
import java.util.Map;

public interface WorkflowEngine {

    /**
     * 启动流程，创建流程实例及第一个任务。
     *
     * @param processDefinitionId 流程定义ID
     * @param initiator 发起人ID
     * @param variables 流程变量（如审批参数等，可选）
     * @return 新的流程实例对象
     */
    ProcessInstance startProcess(Long processDefinitionId, String initiator, LRMProcessParams variables);

    /**
     * 中止流程。
     *
     * @param processInstanceId 流程实例ID
     * @param operator 操作人ID
     */
    void abortProcess(Long processInstanceId, String operator);

    /**
     * 根据流程实例ID、任务状态和表单名称（任务名称）查询任务。
     *
     * @param processInstanceId 流程实例ID
     * @param status 任务状态，如"PENDING"、"COMPLETED"等
     * @param formName 表单名称或任务名称
     * @return 符合条件的任务列表
     */
    List<TaskInstance> getTasksByProcessInstanceIdStatusFormName(Long processInstanceId, String status, String formName);

    /**
     * 提名任务给指定人，通常用于多人协作时的候选提名操作。
     *
     * @param taskInstanceId 任务实例ID
     * @param operator 操作人ID（发起提名的人）
     * @param nominee 被提名的用户ID
     */
    void nominateTask(Long taskInstanceId, String operator, String nominee);

    void startTask(Long taskInstanceId, String operator);

    /**
     * 完成任务，触发流程状态转换，并可更新流程变量。
     *
     * @param taskInstanceId 任务实例ID
     * @param operator 操作人ID（当前任务处理人）
     * @param variables 可选的流程变量更新
     */
    void completeTask(Long taskInstanceId, TransitionAction action, String operator, Map<String, Object> variables);

    /**
     * 根据流程实例ID获取分配给候选人的任务列表。
     *
     * @param processInstanceId 流程实例ID
     * @return 分配给候选人的任务列表
     */
    List<TaskInstance> getTaskAssignedAsPotentialOwnerByProcessId(Long processInstanceId);

    /**
     * 退回任务（重新处理），通常是将任务退回到前一环节。
     *
     * @param taskInstanceId 任务实例ID
     * @param operator 操作人ID（当前任务处理人）
     * @param variables 可选的流程变量更新
     */
    void reworkTask(Long taskInstanceId, String operator, Map<String, Object> variables);

    /**
     * 重新分配任务给其他人。
     *
     * @param taskInstanceId 任务实例ID
     * @param operator 操作人ID（当前任务处理人）
     * @param newAssignee 新的处理人ID
     */
    void reassignTask(Long taskInstanceId, String operator, String newAssignee);

    /**
     * 根据流程实例ID获取流程实例详情。
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例对象
     */
    ProcessInstance getProcessInstance(Long processInstanceId);

    /**
     * 获取指定用户待处理的任务列表。
     *
     * @param userId 用户ID
     * @return 待处理任务列表
     */
    List<TaskInstance> getPendingTasks(String userId);
}
