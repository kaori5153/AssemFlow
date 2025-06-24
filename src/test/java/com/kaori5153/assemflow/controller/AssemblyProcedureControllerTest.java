package com.kaori5153.assemflow.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.service.AssemblyProcedureService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AssemblyProcedureController.class)
class AssemblyProcedureControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AssemblyProcedureService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Nested
  class 正常動作テスト {

    @Test
    void 部品一覧検索が実行できて空のリストが返ってくること() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/parts"))
          .andExpect(status().isOk())
          .andExpect(content().json("[]"));

      verify(service, times(1)).searchPartsList();
    }

  }


}