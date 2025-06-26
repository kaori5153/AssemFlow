package com.kaori5153.assemflow.service;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.repository.AssemblyProcedureRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssemblyProcedureService {

  private AssemblyProcedureRepository repository;

  @Autowired
  public AssemblyProcedureService(AssemblyProcedureRepository repository) {
    this.repository = repository;
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

  public List<AssemblyProcedure> getAllAssemblyProcedureList() {
    return repository.findAllAssemblyProcedure();
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

  public AssemblyProcedure getAssemblyProcedureById(int targetPartId){
    return repository.findByTargetPartId(targetPartId);
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

  public void updatePart(Parts part){
    repository.updatePartById(part);
  }

  public void updateRequiredPart(RequiredParts requiredPart){
    repository.updateRequiredPartById(requiredPart);
  }

  public void updateAssemblyProcedure(AssemblyProcedure assemblyProcedure){
    repository.updateAssemblyProcedureById(assemblyProcedure);
  }

}
