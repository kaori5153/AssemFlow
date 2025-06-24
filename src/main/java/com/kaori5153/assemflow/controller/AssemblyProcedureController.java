package com.kaori5153.assemflow.controller;


import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.service.AssemblyProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class AssemblyProcedureController {

  private AssemblyProcedureService service;

  @Autowired
  public AssemblyProcedureController(AssemblyProcedureService service) {
    this.service = service;
  }

  @Operation(summary = "部品情報一覧", description = "部品情報の一覧を検索します")
  @GetMapping("/parts")
  public List<Parts> getStudentList() {
    return service.searchPartsList();
  }

}
