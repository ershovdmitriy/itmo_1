package org.example.Web4.repository;

import org.example.Web4.model.PointResult;
import org.example.Web4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointResultRepository extends JpaRepository<PointResult, Long> {
    List<PointResult> findByUser(User user);
    void deleteByUser(User user);
}