package com.kaori5153.assemflow.controller.converter;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import com.kaori5153.assemflow.domain.RequiredPartView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AssemblyProcedureConverter {

  public List<AssemblyProcedureDetail> toAssemblyProcedureDetails(
      List<AssemblyProcedure> procedureList,
      List<Parts> partsList,
      List<RequiredParts> requiredPartsList) {

    Map<Integer, Parts> partsMap = new HashMap<>();
    for (Parts p : partsList) {
      partsMap.put(p.getPartId(), p);
    }

    List<AssemblyProcedureDetail> details = new ArrayList<>();
    for (AssemblyProcedure procedure : procedureList) {
      AssemblyProcedureDetail detail = new AssemblyProcedureDetail();
      detail.setAssemblyProcedure(procedure);

      detail.setTargetPart(partsMap.get(procedure.getTargetPartId()));  //完成品IDが部品マップのキーとなっているものをセットする。

      List<RequiredPartView> requiredPartViews = getRequiredPartViews(  //必要部品で部品名が見えるように成形する。
          partsMap, requiredPartsList, procedure);
      detail.setRequiredPartViews(requiredPartViews);
      details.add(detail);
    }
    return details;
  }

  /**
   * 指定された手順に対応する必要部品リスト（名前付き）を生成する。
   * @param partsMap
   * @param requiredPartsList
   * @param procedure
   * @return
   */
  private static List<RequiredPartView> getRequiredPartViews(Map<Integer, Parts> partsMap,
      List<RequiredParts> requiredPartsList, AssemblyProcedure procedure) {
    List<RequiredPartView> requiredPartViews = new ArrayList<>();

    for (RequiredParts requiredParts : requiredPartsList) {
      if (procedure.getProcedureId() == requiredParts.getProcedureId()) { //手順IDが等しい場合
        Parts parts = partsMap.get(requiredParts.getPartId());  //  部品IDキーに紐づく部品情報を代入
        if (parts != null) {     //部品がある場合
          addToViewList(requiredParts, parts, requiredPartViews); //Viewリストに追加
        }
      }
    }
    return requiredPartViews;
  }

  private static void addToViewList(RequiredParts requiredParts, Parts parts,
      List<RequiredPartView> requiredPartViews) {
    requiredPartViews.add(
        RequiredPartView.builder().requiredPartId(requiredParts.getRequiredPartId())
            .partId(requiredParts.getPartId())
            .partName(parts.getPartName())
            .quantity(requiredParts.getQuantity())
            .build()
    );
  }

}
