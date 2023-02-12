package com.lambda.appointment.notes.mapper;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.model.User;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static UserDTO toDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    public static User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public List<UserDTO> toDTOList(List<User> entities) {
        return entities.stream()
                .map(entity -> toDTO(entity))
                .collect(Collectors.toList());
    }

    public List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream()
                .map(dto -> toEntity(dto))
                .collect(Collectors.toList());
    }
}
