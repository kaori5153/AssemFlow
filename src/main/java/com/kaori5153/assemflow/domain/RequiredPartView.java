package com.kaori5153.assemflow.domain;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "必要部品の表示用DTO。部品名と数量を含むビューオブジェクトです。")
public class RequiredPartView {

  @Schema(description = "必要部品の識別ID", example = "101")
  private int requiredPartId;

  @Schema(description = "部品ID", example = "501")
  private int partId;

  @Schema(description = "部品名。表示用として使用されます", example = "ねじ M4")
  private String partName;

  @Schema(description = "組み立てに必要な個数", example = "4")
  private int quantity;

}
