package com.lambda.appointment.notes.mapper;

import com.lambda.appointment.notes.config.ConfigEnvironment;
import com.lambda.appointment.notes.dto.ConfigEnvironmentDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigEnvironmentMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static ConfigEnvironmentDTO toDTO(ConfigEnvironment configEnvironment) {
        return modelMapper.map(configEnvironment, ConfigEnvironmentDTO.class);
    }

    public static ConfigEnvironment toEntity(ConfigEnvironmentDTO configEnvironmentDTO) {
        return modelMapper.map(configEnvironmentDTO, ConfigEnvironment.class);
    }

    public List<ConfigEnvironmentDTO> toDTOList(List<ConfigEnvironment> configEnvironmentList) {
        return configEnvironmentList.stream()
                .map(entity -> toDTO(entity))
                .collect(Collectors.toList());
    }

    public List<ConfigEnvironment> toEntityList(List<ConfigEnvironmentDTO> configEnvironmentDTOS) {
        return configEnvironmentDTOS.stream()
                .map(dto -> toEntity(dto))
                .collect(Collectors.toList());
    }
}
