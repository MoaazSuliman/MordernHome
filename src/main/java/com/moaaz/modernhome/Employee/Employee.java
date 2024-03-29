package com.moaaz.modernhome.Employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.User.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee extends Person {

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<EmployeeLog> employeeLogs;

}
