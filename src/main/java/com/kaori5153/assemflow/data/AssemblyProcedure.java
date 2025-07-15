package com.kaori5153.assemflow.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "組み立て手順詳細")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AssemblyProcedure {

  @Schema(description = "組み立て手順ID", type = "int", example = "1", required = true)
  @Max(value = 999, message = "組み立て手順IDは999以下の数値で入力してください")
  private Integer procedureId;

  @Schema(description = "組立説明文", type = "String", example = "ねじを締める", required = true)
  @NotNull
  @Size(min = 1, max = 500, message = "1文字以上500文字以下で入力してください")
  private String description;

  @Schema(description = "完成品ID", type = "int", example = "1", required = true)
  @Max(value = 999, message = "完成品IDは999以下の数値で入力してください")
  private Integer targetPartId;

}
