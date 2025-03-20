package com.lrm.workflow_svc.entity;

import com.lrm.workflow_svc.entity.converter.StringArrayConverter;
import com.lrm.workflow_svc.enums.NodeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task_definition", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
public class TaskDefinition {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROCESS_DEFINITION_ID_FK", referencedColumnName = "id")
    private ProcessDefinition processDefinition;

    @Column(name = "NAME", nullable = true)
    private String name;
    // 枚举类型，表示节点类型，如 START、TASK、END
    @Column(name = "NODE_TYPE", nullable = true)
    @Enumerated(EnumType.STRING)
    private NodeType nodeType;

    // 允许的角色列表，决定哪些用户可以操作该节点
    @Column(name = "ALLOWED_ROLES", nullable = true)
    @Convert(converter = StringArrayConverter.class)
    private String[] allowedRoles;

    // 任务节点的执行者
    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;

    @OneToMany(mappedBy = "fromTaskDefinition",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Transition> outgoingTransitions;


    @OneToMany(mappedBy = "toTaskDefinition",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Transition> incomingTransitions;

    @OneToMany(mappedBy = "taskDefinition",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<TaskInstance> taskInstances;

}
