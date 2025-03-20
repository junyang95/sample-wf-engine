package com.lrm.workflow_svc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

// 流程
@Entity
@Table(name = "process_definition", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProcessDefinition {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "process_definition_seq")
    @SequenceGenerator(name = "process_definition_seq", sequenceName = "process_definition_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = true)
    private String name;//WF5, WF2

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "VERSION", nullable = true)
    private Integer version;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;

    // 包含该流程中的所有节点和转移关系
    @OneToMany(mappedBy = "processDefinition",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<TaskDefinition> taskDefinitions;

    @OneToMany(mappedBy = "processDefinition",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Transition> transitions;
}
