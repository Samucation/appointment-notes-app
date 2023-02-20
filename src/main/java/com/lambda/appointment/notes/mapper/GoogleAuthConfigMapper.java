package com.lambda.appointment.notes.mapper;

import com.lambda.appointment.notes.config.GoogleAuthConfig;
import com.lambda.appointment.notes.dto.GoogleAuthConfigDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GoogleAuthConfigMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    public static GoogleAuthConfigDTO toDTO(GoogleAuthConfig googleAuthConfig) {
        return modelMapper.map(googleAuthConfig, GoogleAuthConfigDTO.class);
    }

    public static GoogleAuthConfig toEntity(GoogleAuthConfigDTO googleAuthConfigDTO) {
        return modelMapper.map(googleAuthConfigDTO, GoogleAuthConfig.class);
    }

    public List<GoogleAuthConfigDTO> toDTOList(List<GoogleAuthConfig> googleAuthConfigs) {
        return googleAuthConfigs.stream()
                .map(entity -> toDTO(entity))
                .collect(Collectors.toList());
    }

    public List<GoogleAuthConfig> toEntityList(List<GoogleAuthConfigDTO> googleAuthConfigDTOS) {
        return googleAuthConfigDTOS.stream()
                .map(dto -> toEntity(dto))
                .collect(Collectors.toList());
    }
}
