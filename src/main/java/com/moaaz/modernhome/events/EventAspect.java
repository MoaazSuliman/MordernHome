package com.moaaz.modernhome.events;

import com.moaaz.modernhome.Auth.AuthService;
import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.Employee.Logs.EmployeeLogService;
import com.moaaz.modernhome.Employee.Logs.LogType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EventAspect {

	@Autowired
	private EmployeeLogService employeeLogService;

	@Autowired
	private AuthService authService;




	@Before("@annotation(com.moaaz.modernhome.events.EmployeeEvent)")
	public void handleEmployeeEvent(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		EmployeeEvent employeeEvent = methodSignature.getMethod().getAnnotation(EmployeeEvent.class);

		if (employeeEvent != null){
			EmployeeLog employeeLog =  EmployeeLog.builder()
					.logType(employeeEvent.type())
					.employeeAction(employeeEvent.action())
					.employee(authService.getLoggedInEmployee())
					.build();
			employeeLogService.saveLog(employeeLog);
		}
	}
}
