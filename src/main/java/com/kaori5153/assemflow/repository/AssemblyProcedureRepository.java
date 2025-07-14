package com.kaori5153.assemflow.repository;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 組み立て手順詳細に紐づく情報を扱うリポジトリです。 部品情報、組み立て必要部品情報、組み立て手順情報の検索、登録、更新を行います。
 */
@Mapper
public interface AssemblyProcedureRepository {

  /**
   * 部品情報を全件検索します。
   *
   * @return 全件検索した部品情報の一覧
   */
  List<Parts> findAllParts();

  /**
   * 必要部品情報を全件検索します。
   *
   * @return 全件検索した必要部品情報の一覧
   */
  List<RequiredParts> findAllRequiredParts();

  /**
   * 組み立て手順情報を全件検索します。
   *
   * @return 全件検索した組み立て手順情報の一覧
   */
  List<AssemblyProcedure> findAllAssemblyProcedure();

  /**
   * 指定した部品IDに紐づく部品情報を検索します。
   *
   * @param partId 部品ID
   * @return 部品情報
   */
  Parts findById(int partId);

  /**
   * 指定した部品名に紐づく部品情報を検索します。
   *
   * @param partName 部品名
   * @return 部品情報
   */
  Parts findByPartName(String partName);

  /**
   * 指定した必要部品IDに紐づく必要部品情報を検索します。
   *
   * @param requiredPartId 必要部品情報ID
   * @return 必要部品情報
   */
  RequiredParts findByRequiredPartId(int requiredPartId);

  /**
   * 指定した組み立て手順IDに紐づく組み立て手順情報を検索します。
   *
   * @param procedureId 組み立て手順ID
   * @return 組み立て手順情報
   */
  AssemblyProcedure findByProcedureId(int procedureId);

  /**
   * 指定した完成品の部品IDに紐づく組み立て手順情報リストを検索します。
   *
   * @param targetPartId 完成品の部品ID
   * @return 組み立て手順情報リスト
   */
  List<AssemblyProcedure> findByTargetPartId(int targetPartId);

  /**
   * 新規部品を登録します。部品IDは自動採番します。
   *
   * @param part 登録する部品情報
   */
  void insertNewPart(Parts part);

  /**
   * 新規必要部品情報を登録します。必要部品情報IDは自動採番します。
   *
   * @param requiredPart 登録する必要部品情報
   */
  void insertNewRequiredPart(RequiredParts requiredPart);

  /**
   * 新規組み立て手順を登録します。
   *
   * @param assemblyProcedure 登録する組み立て手順情報
   */
  void insertNewAssemblyProcedure(AssemblyProcedure assemblyProcedure);

  /**
   * 部員情報を更新します。
   *
   * @param part 更新する部品情報
   */
  void updatePartById(Parts part);

  /**
   * 必要部品情報を更新します。
   *
   * @param requiredPart 更新する必要部品情報
   */
  void updateRequiredPartById(RequiredParts requiredPart);

  /**
   * 組み立て手順情報を更新します。
   *
   * @param assemblyProcedure 更新する組み立て手順情報
   */
  void updateAssemblyProcedureById(AssemblyProcedure assemblyProcedure);
}
