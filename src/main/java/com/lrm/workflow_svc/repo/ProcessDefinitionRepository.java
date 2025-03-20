package com.lrm.workflow_svc.repo;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessDefinitionRepository extends CrudRepository<ProcessDefinition, Long> {
}
