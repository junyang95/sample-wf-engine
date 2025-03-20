package com.lrm.workflow_svc.entity;

import com.lrm.workflow_svc.enums.ProcessInstanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "process_instance", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
public class ProcessInstance {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_instance_seq")
    @SequenceGenerator(name = "process_instance_seq", sequenceName = "process_instance_seq", allocationSize = 1)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "PROCESS_DEFINITION_ID_FK", referencedColumnName = "id")
    private ProcessDefinition processDefinition;

    @Column(name = "INITIATOR", nullable = true)
    private String initiator;

    @Column(name = "STATUS", nullable = true)
    @Enumerated(EnumType.STRING)
    private ProcessInstanceStatus status;  // 如 RUNNING, COMPLETED, REJECTED 等

    @Column(name = "STARTED_AT", nullable = true)
    private Date startedAt;

    @Column(name = "ENDED_AT", nullable = true)
    private Date endedAt;

    // 当前正在处理的任务实例
    @OneToOne
    @JoinColumn(name = "CURRENT_TASK_INSTANCE_ID_FK", referencedColumnName = "id")
    private TaskInstance currentTask;

    @OneToMany(mappedBy = "processInstance",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<TaskInstance> taskInstances;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;
}
