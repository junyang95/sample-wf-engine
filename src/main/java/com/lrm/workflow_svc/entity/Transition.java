package com.lrm.workflow_svc.entity;

import com.lrm.workflow_svc.enums.TransitionAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Entity
@Table(name = "transition", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
public class Transition {
    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROCESS_DEFINITION_ID_FK", referencedColumnName = "id")
    private ProcessDefinition processDefinition;


    // 来源节点
    @ManyToOne
    @JoinColumn(name = "FROM_TASK_DEFINITION_ID_FK", referencedColumnName = "id")
    private TaskDefinition fromTaskDefinition;

    // 目标节点
    @ManyToOne
    @JoinColumn(name = "TO_TASK_DEFINITION_ID_FK", referencedColumnName = "id")
    private TaskDefinition toTaskDefinition;

    // 操作类型，如 submit、rework、reassign
    @Column(name = "ACTION", nullable = true)
    @Enumerated(EnumType.STRING)
    private TransitionAction action;
    // 可选条件表达式
    // 用于条件判断，如 ${amount > 1000}
    @Column(name = "CONDITION", nullable = true)
    private String condition;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;
}
