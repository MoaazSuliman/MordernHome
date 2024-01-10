package com.moaaz.modernhome.Employee;

import com.moaaz.modernhome.User.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")

public class Employee extends Person {
}
