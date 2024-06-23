package com.moaaz.modernhome.Employee.Logs;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeLogMapper {


    @Mapping(target = "employeeId" , source = "employeeLog.employee.id")
    EmployeeLogResponse toResponse(EmployeeLog employeeLog);
}
