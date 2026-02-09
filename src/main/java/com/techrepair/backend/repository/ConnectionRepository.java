package com.techrepair.backend.repository;

import com.techrepair.backend.enums.ConnectionStatus;
import com.techrepair.backend.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByTechnicianId(Long technicianId);
    @Query("select c from Connection c where c.status = 'ACTIVE'")
    List<Connection> findActiveConnections();
}
