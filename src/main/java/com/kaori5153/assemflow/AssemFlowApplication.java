package com.kaori5153.assemflow;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "組み立て手順管理システム"))
@SpringBootApplication
public class
AssemFlowApplication {

  public static void main(String[] args) {
    SpringApplication.run(AssemFlowApplication.class, args);
  }
}
