package com.moaaz.modernhome.Employee;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

	Employee toEntity(EmployeeRequest employeeRequest);
	EmployeeResponse toResponse(Employee employee);
}
