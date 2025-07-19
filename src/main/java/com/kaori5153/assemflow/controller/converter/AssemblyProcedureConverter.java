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

/**
 * 組み立て手順リスト、部品情報リスト、必要部品リストを組み立て手順情報 {@link AssemblyProcedureDetail} に変換するコンポーネントです。
 */
@Component
public class AssemblyProcedureConverter {

  /**
   * 組立手順、部品リスト、必要部品リストを元に、 {@link AssemblyProcedureDetail} オブジェクトのリストを生成します。
   *
   * @param procedureList     組立手順のリスト
   * @param partsList         部品情報のリスト
   * @param requiredPartsList 各手順に紐づく必要部品リスト
   * @return ドメイン変換された組立手順詳細のリスト
   */
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

      detail.setTargetPart(
          partsMap.get(procedure.getTargetPartId()));  //完成品IDが部品マップのキーとなっているものをセットする。

      Parts targetPart = partsMap.get(procedure.getTargetPartId());
      if (targetPart != null) {
        detail.setTargetPart(targetPart);
      }


      List<RequiredPartView> requiredPartViews = getRequiredPartViews(  //必要部品で部品名が見えるように成形する。
          partsMap, requiredPartsList, procedure);
      detail.setRequiredPartViews(requiredPartViews);
      details.add(detail);
    }
    return details;
  }

  /**
   * 指定された手順 {@link AssemblyProcedure} に関連付けられた必要部品を {@link RequiredPartView} 形式に変換して返却します。
   *
   * @param partsMap          部品IDをキーとする部品マップ
   * @param requiredPartsList 全手順に対する必要部品のリスト
   * @param procedure         対象の組立手順
   * @return 指定手順に対応する必要部品のViewリスト
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

  /**
   * {@link RequiredParts} と {@link Parts} を元に {@link RequiredPartView} オブジェクトを生成しリストに追加します。
   *
   * @param requiredParts     必要部品エンティティ
   * @param parts             部品エンティティ（部品名取得に使用）
   * @param requiredPartViews 必要部品Viewリストへの追加対象
   */
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

  /**
   * 指定された完成品部品名に一致する組立手順詳細のみを抽出して取得します。 部品検索機能で使用されます。
   *
   * @param targetPartName   検索対象の部品名
   * @param procedureDetails 全組立手順詳細リスト
   * @return 完成品名に一致する組立手順詳細リスト
   */
  public static List<AssemblyProcedureDetail> getTargetPartProcedure(String targetPartName,
      List<AssemblyProcedureDetail> procedureDetails) {
    List<AssemblyProcedureDetail> detailList = new ArrayList<>();
    for (AssemblyProcedureDetail detail : procedureDetails) {
      if (detail.getTargetPart() != null && targetPartName.equals(detail.getTargetPart().getPartName())) {
        detailList.add(detail);
      }
    }
    return detailList;
  }
}
