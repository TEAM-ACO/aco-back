package dev.aco.back.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Visitor.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long>{
    List<Visitor> findAllByCreatedDateTimeGreaterThan(LocalDateTime weekago);
}
