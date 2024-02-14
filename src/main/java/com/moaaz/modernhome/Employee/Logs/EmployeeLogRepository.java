package com.moaaz.modernhome.Employee.Logs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeLogRepository extends JpaRepository<EmployeeLog , Long> {
}
