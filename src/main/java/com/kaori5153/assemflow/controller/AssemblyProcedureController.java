package com.kaori5153.assemflow.controller;


import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.service.AssemblyProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
  public List<Parts> getPartsList() {
    return service.getAllParts();
  }

  @GetMapping("/parts/required")
  public List<RequiredParts> getRequiredPartsList() {
    return service.getAllRequiredPartsList();
  }

  @GetMapping("/procedure")
  public List<AssemblyProcedure> getAssemblyProcedureList() {
    return service.getAllAssemblyProcedureList();
  }

  @GetMapping("/parts/{id}")
  public Parts getPart(@PathVariable("id") int id) {
    return service.getPartById(id);
  }

  @GetMapping("/parts/name")
  public Parts getPartByName(@RequestParam String partName) {
    return service.getPartByName(partName);
  }

  @GetMapping("/parts/required/{id}")
  public RequiredParts getRequiredPartInfo(@PathVariable("id") int partId) {
    return service.getRequiredPartByPartId(partId);
  }

  @GetMapping("/procedure/{id}")
  public AssemblyProcedure getAssemblyProcedure(@PathVariable("id") int id) {
    return service.getAssemblyProcedureById(id);
  }


}
