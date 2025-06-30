package com.kaori5153.assemflow.service;

import com.kaori5153.assemflow.controller.converter.AssemblyProcedureConverter;
import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import com.kaori5153.assemflow.repository.AssemblyProcedureRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssemblyProcedureService {

  private AssemblyProcedureRepository repository;
  private AssemblyProcedureConverter converter;

  @Autowired
  public AssemblyProcedureService(AssemblyProcedureRepository repository,AssemblyProcedureConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   *
   * @return 部品情報一覧
   */
  public List<Parts> getAllParts() {
    return repository.findAllParts();
  }

  public List<RequiredParts> getAllRequiredPartsList() {
    return repository.findAllRequiredParts();
  }

  public List<AssemblyProcedureDetail> getAllAssemblyProcedureList() {
    List<AssemblyProcedure> allProcedures = repository.findAllAssemblyProcedure();
    List<Parts> allParts = repository.findAllParts();
    List<RequiredParts> allRequiredParts = repository.findAllRequiredParts();
    return converter.toAssemblyProcedureDetails(allProcedures,allParts,allRequiredParts);
  }

  public Parts getPartById(int partId){
    return repository.findById(partId);
  }

  public Parts getPartByName(String partName){
    return repository.findByPartName(partName);
  }

  public RequiredParts getRequiredPartByPartId(int partId){
    return repository.findByPartId(partId);
  }

  public List<AssemblyProcedureDetail>  getAssemblyProcedureById(int targetPartId){
    List<AssemblyProcedure> procedures = repository.findByTargetPartId(targetPartId);
    List<Parts> allParts = repository.findAllParts();
    List<RequiredParts> allRequiredParts = repository.findAllRequiredParts();
    return converter.toAssemblyProcedureDetails(procedures,allParts,allRequiredParts);
  }

  public void resisterNewPart(Parts part){
    repository.insertNewPart(part);
  }

  public void resisterNewRequiredPart(RequiredParts requiredPart){
    repository.insertNewRequiredPart(requiredPart);
  }

  public void resisterNewAssemblyProcedure(AssemblyProcedure assemblyProcedure){
    repository.insertNewAssemblyProcedure(assemblyProcedure);
  }

  public void updatePartById(Parts part){
    repository.updatePartById(part);
  }

  public void updatePartByPartName(Parts part){
    repository.updatePartByPartName(part);
  }

  public void updateRequiredPart(RequiredParts requiredPart){
    repository.updateRequiredPartById(requiredPart);
  }

  public void updateAssemblyProcedure(AssemblyProcedure assemblyProcedure){
    repository.updateAssemblyProcedureById(assemblyProcedure);
  }

}
