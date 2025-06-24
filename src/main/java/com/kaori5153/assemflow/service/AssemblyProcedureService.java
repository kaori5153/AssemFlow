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
  public List<Parts> searchPartsList() {
    return repository.searchParts();
  }

  public List<RequiredParts> searchRequiredPartsList() {
    return repository.searchRequiredParts();
  }

  public List<AssemblyProcedure> searchAssemblyProcedureList() {
    return repository.searchAssemblyProcedure();
  }

}
