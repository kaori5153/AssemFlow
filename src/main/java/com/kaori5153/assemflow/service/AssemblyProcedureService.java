package com.kaori5153.assemflow.service;

import com.kaori5153.assemflow.data.Parts;
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
   * 受講生情報とコース情報を検索して紐づけを行う。
   * @return 受講生詳細
   */
  public List<Parts> searchPartsList() {
    return repository.searchParts();
  }
}
