package com.kaori5153.assemflow.domain;

import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "組み立て手順の詳細DTO。組み立て手順、完成品部品、必要部品情報を含みます。")
public class AssemblyProcedureDetail {

  @Valid
  @Schema(description = "組立手順そのものを示します。手順IDや組み立て方法などの情報を含みます。", required = true)
  private AssemblyProcedure assemblyProcedure;

  @Valid
  @Schema(description = "完成品となる対象部品。組み立て手順を実行することで完成する部品を示します。", required = true)
  private Parts targetPart;

  @Valid
  @Schema(description = "組み立てに必要な部品の表示リスト。部品名や数量を含む、画面表示用の構造。", required = true)
  private List<RequiredPartView> requiredPartViews;

}
