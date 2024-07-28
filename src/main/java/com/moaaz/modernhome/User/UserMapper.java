package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Order.Order;
import com.moaaz.modernhome.Order.OrderMapper;
import com.moaaz.modernhome.Order.OrderResponse;
import com.moaaz.modernhome.Order.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface UserMapper {


	User toEntity(UserRequest userRequest);


	UserResponse toResponse(User user);


}

