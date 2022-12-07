package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Visitor.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long>{
    
}
