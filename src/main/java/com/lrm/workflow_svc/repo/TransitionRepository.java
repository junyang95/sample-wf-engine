package com.lrm.workflow_svc.repo;


import com.lrm.workflow_svc.entity.Transition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitionRepository extends CrudRepository<Transition, Long> {
}
