package com.kaori5153.assemflow.service;

import com.kaori5153.assemflow.controller.converter.AssemblyProcedureConverter;
import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import com.kaori5153.assemflow.repository.AssemblyProcedureRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部品情報、組み立て必要部品情報、組み立て手順情報を取得・変換するサービスクラスです。
 */
@Service
public class AssemblyProcedureService {

  private AssemblyProcedureRepository repository;
  private AssemblyProcedureConverter converter;

  @Autowired
  public AssemblyProcedureService(AssemblyProcedureRepository repository,
      AssemblyProcedureConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 登録されている全ての部品情報を取得します。
   *
   * @return {@link Parts} の一覧
   */
  public List<Parts> getAllParts() {
    return repository.findAllParts();
  }

  /**
   * 全組立手順と全部品情報・全必要部品情報をまとめて取得し、 {@link AssemblyProcedureDetail} に変換して返却します。
   *
   * @return 換済みの組立手順詳細リスト
   */
  public List<AssemblyProcedureDetail> getAllAssemblyProcedureList() {
    List<AssemblyProcedure> allProcedures = repository.findAllAssemblyProcedure();
    List<Parts> allParts = repository.findAllParts();
    List<RequiredParts> allRequiredParts = repository.findAllRequiredParts();
    return converter.toAssemblyProcedureDetails(allProcedures, allParts, allRequiredParts);
  }

  /**
   * 指定された部品IDの部品情報を取得します。
   *
   * @param partId 取得対象の部品ID
   * @return 指定部品IDに一致する {@link Parts} オブジェクト
   */
  public Parts getPartById(int partId) {
    return repository.findById(partId);
  }

  /**
   * 指定された部品名に対応する部品情報を取得します。
   *
   * @param partName 取得対象の部品名
   * @return 指定された部品名に一致する {@link Parts} オブジェクト
   */
  public Parts getPartByName(String partName) {
    return repository.findByPartName(partName);
  }

  /**
   * 指定された必要部品IDの必要部品情報を取得します。
   *
   * @param requiredPartId 取得対象の必要部品ID
   * @return 指定された必要部品IDに一致する {@link RequiredParts} オブジェクト
   */
  public RequiredParts getRequiredPartByRequiredPartId(int requiredPartId) {
    return repository.findByRequiredPartId(requiredPartId);
  }

  /**
   * 指定された組み立て手順IDの情報を取得します。
   *
   * @param procedureId 取得対象の組み立て手順ID
   * @return 指定された組み立て手順IDに一致する{@link AssemblyProcedure}組立手順オブジェクト
   */
  public AssemblyProcedure getAssemblyProcedureByProcedureId(int procedureId) {
    return repository.findByProcedureId(procedureId);
  }

  /**
   * 指定された完成品の部品IDに紐づく組立手順一覧を取得し、 {@link AssemblyProcedureDetail} に変換して返却します。
   *
   * @param targetPartId 完成品の部品ID
   * @return 指定された完成部品IDに紐づく組立手順詳細リスト
   */
  public List<AssemblyProcedureDetail> getAssemblyProcedureById(int targetPartId) {
    List<AssemblyProcedure> procedures = repository.findByTargetPartId(targetPartId);
    List<Parts> allParts = repository.findAllParts();
    List<RequiredParts> allRequiredParts = repository.findAllRequiredParts();
    return converter.toAssemblyProcedureDetails(procedures, allParts, allRequiredParts);
  }

  /**
   * 指定された完成品部品名に一致する組み立て手順詳細を抽出します。
   *
   * @param targetPartName   完成品の部品名
   * @param procedureDetails 全手順詳細リスト
   * @return 完成品名に一致する組み立て手順詳細リスト
   */
  public List<AssemblyProcedureDetail> getProcedureByTargetPartName(String targetPartName,
      List<AssemblyProcedureDetail> procedureDetails) {
    return converter.getTargetPartProcedure(targetPartName, procedureDetails);
  }

  /**
   * 新しい部品情報をデータベースに登録します。
   *
   * @param part 登録対象の {@link Parts} 部品情報オブジェクト
   */
  public void resisterNewPart(Parts part) {
    repository.insertNewPart(part);
  }

  /**
   * 新しい必要部品情報を登録します。
   *
   * @param requiredPart 登録対象の {@link RequiredParts} 必要部品情報オブジェクト
   */
  public void resisterNewRequiredPart(RequiredParts requiredPart) {
    repository.insertNewRequiredPart(requiredPart);
  }

  /**
   * 新しい組み立て手順情報を登録します。
   *
   * @param assemblyProcedure 登録対象の {@link AssemblyProcedure} 組み立て手順情報オブジェクト
   */
  public void resisterNewAssemblyProcedure(AssemblyProcedure assemblyProcedure) {
    repository.insertNewAssemblyProcedure(assemblyProcedure);
  }

  /**
   * 指定された部品IDの部品情報を更新します。
   *
   * @param part 更新対象の {@link Parts} 部品情報オブジェクト
   */
  public void updatePartById(Parts part) {
    repository.updatePartById(part);
  }

  /**
   * 指定された必要部品IDの情報を更新します。
   *
   * @param requiredPart 更新対象の {@link RequiredParts} 必要部品オブジェクト
   */
  public void updateRequiredPart(RequiredParts requiredPart) {
    repository.updateRequiredPartById(requiredPart);
  }

  /**
   * 指定された組み立て手順IDの情報を更新します。
   *
   * @param assemblyProcedure 更新対象の {@link AssemblyProcedure} 組み立て手順オブジェクト
   */
  public void updateAssemblyProcedure(AssemblyProcedure assemblyProcedure) {
    repository.updateAssemblyProcedureById(assemblyProcedure);
  }

}
