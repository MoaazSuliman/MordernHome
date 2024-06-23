package com.moaaz.modernhome.Employee.Logs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moaaz.modernhome.Employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLogResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long employeeId;

    @Transient
    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    private EmployeeAction employeeAction;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    private LocalDateTime creationDate;

    private boolean isAdmin;
}
