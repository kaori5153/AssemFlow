package com.kaori5153.assemflow.repository;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssemblyProcedureRepository {

  /**
   * 部品情報を全件検索します。
   *
   * @return 全件検索した部品情報の一覧
   */
  List<Parts> findAllParts();

  List<RequiredParts> findAllRequiredParts();

  List<AssemblyProcedure> findAllAssemblyProcedure();

  Parts findById(int partId);

  Parts findByPartName(String partName);

  RequiredParts findByPartId(int partId);

  AssemblyProcedure findByProcedureId(int procedureId);

  List<AssemblyProcedure> findByTargetPartId(int targetPartId);

  void insertNewPart(Parts part);

  void insertNewRequiredPart(RequiredParts requiredPart);

  void insertNewAssemblyProcedure(AssemblyProcedure assemblyProcedure);

  void updatePartById(Parts part);

  void updatePartByPartName(Parts part);

  void updateRequiredPartById(RequiredParts requiredPart);

  void updateAssemblyProcedureById(AssemblyProcedure assemblyProcedure);
}
