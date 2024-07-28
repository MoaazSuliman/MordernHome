package com.moaaz.modernhome.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findAllByActiveIsTrue();

	Optional<Employee> findByEmail(String email);

	Optional<Employee> findByEmailAndPassword(String email, String password);
}
