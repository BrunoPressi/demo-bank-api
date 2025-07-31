package com.bank_api.dto.validator;

import com.bank_api.dto.UserCreateDTO;
import jakarta.validation.GroupSequence;

@GroupSequence({UserCreateDTO.Step1.class, UserCreateDTO.Step2.class, UserCreateDTO.Step3.class})
public interface ValidationOrder {}
