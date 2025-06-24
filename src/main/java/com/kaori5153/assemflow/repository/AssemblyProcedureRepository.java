package com.kaori5153.assemflow.repository;

import com.kaori5153.assemflow.data.Parts;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssemblyProcedureRepository {

  /**
   * 部品情報を全件検索します。
   *
   * @return 全件検索した部品情報の一覧
   */
  List<Parts> searchParts();

}
