package com.kaori5153.assemflow.controller;


import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import com.kaori5153.assemflow.service.AssemblyProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 組み立て手順情報に関連する情報の検索、登録、更新など行うコントローラークラスです。
 */

@Controller
public class AssemblyProcedureController {

  private AssemblyProcedureService service;

  @Autowired
  public AssemblyProcedureController(AssemblyProcedureService service) {
    this.service = service;
  }

  /**
   * ユーザーが操作を選択するホーム画面を表示します。
   *
   * @return ホーム画面のビュー名
   */
  @Operation(
      summary = "操作選択画面を表示",
      description = "ユーザーに対して、組立手順関連の操作（登録・検索など）を選択させる画面を提供します。"
  )
  @GetMapping("/assemProcedure/form")
  public String showSelectOperation() {
    return "selectOperation";
  }

  /**
   * 全部品情報を一覧で表示します。
   *
   * @param model 全部品情報を追加するモデル
   * @return 全部品情報の表示画面のビュー名
   */
  @Operation(summary = "部品情報一覧", description = "部品情報の一覧を検索します")
  @GetMapping("/parts")
  public String getPartsList(Model model) {
    model.addAttribute("parts", service.getAllParts());
    return "parts";
  }

  /**
   * 検索する部品名を入力する画面を表示します。
   *
   * @return 部品名入力画面のビュー名
   */
  @Operation(
      summary = "部品名入力択画面を表示",
      description = "ユーザーに対して、検索したい部品の部品名入力画面を提供します。"
  )
  @GetMapping("/parts/search")
  public String showSearchPartForm() {
    return "searchPart";
  }

  /**
   * 部品IDで指定された部品の情報を表示します。
   *
   * @param partId 表示したい部品の部品ID
   * @param model  該当する部品情報を追加するモデル
   * @return 対象部品情報の表示画面のビュー名
   */

  @Operation(
      summary = "部品情報画面を表示",
      description = "部品IDで指定された部品情報を表示します。"
  )
  @GetMapping("/parts/{partId}")
  public String getPart(@PathVariable("partId") int partId, Model model) {
    model.addAttribute("part", service.getPartById(partId));
    return "part";
  }

  /**
   * 部品名で指定された部品の情報を表示します。
   *
   * @param partName 表示したい部品の部品名
   * @param model    該当する部品情報を追加するモデル
   * @return 対象部品情報の表示画面のビュー名
   */
  @Operation(
      summary = "部品情報画面を表示",
      description = "部品名で指定された部品情報を表示します。"
  )
  @GetMapping(value = "/parts/name", params = "partName")
  public String searchPart(@RequestParam String partName, Model model) {
    if (partName.isEmpty()) {
      return "redirect:/parts/name";
    } else {
      model.addAttribute("part", service.getPartByName(partName));
      return "part";
    }
  }

  /**
   * 組み立て手順を検索するための画面を表示します。
   *
   * @return 組み立て手順検索画面のビュー名
   */
  @Operation(
      summary = "完成品部品名入力択画面を表示",
      description = "ユーザーに対して、検索したい完成品の部品名入力画面を提供します。"
  )
  @GetMapping("/procedure/search")
  public String showSearchProcedureForm() {
    return "searchProcedure";
  }

  /**
   * 入力された完成部品名から対象となる組み立て手順情報を表示します。
   *
   * @param targetPartName ユーザーが入力した完成部品名
   * @param model          該当する組み立て手順情報を追加するモデル
   * @return 組み立て手順検索結果画面（targetProcedure）のビュー名または検索入力画面（/procedure/name）へのリダイレクト
   */
  @Operation(
      summary = "組み立て手順情報画面を表示",
      description = "完成品の部品名で指定された組み立て手順情報を表示します。"
  )
  @GetMapping(value = "/procedure/name", params = "targetPartName")
  public String searchProcedure(@RequestParam String targetPartName, Model model) {
    if (targetPartName.isEmpty()) {
      return "redirect:/procedure/name";
    } else {
      List<AssemblyProcedureDetail> procedureDetails = service.getAllAssemblyProcedureList();
      model.addAttribute("targetProcedure",
          service.getProcedureByTargetPartName(targetPartName, procedureDetails));
      return "targetProcedure";
    }
  }

  /**
   * 指定された完成部品の部品IDの組み立て手順情報を表示します。
   *
   * @param targetPartId 完成部品の部品ID
   * @param model        該当する組み立て手順情報を追加するモデル
   * @return 組み立て手順情報のビュー名
   */
  @Operation(
      summary = "組み立て手順情報画面を表示",
      description = "完成品の部品IDで指定された組み立て手順情報を表示します。"
  )
  @GetMapping("/procedure/{id}")
  public String getAssemblyProcedure(@PathVariable("id") int targetPartId, Model model) {
    List<AssemblyProcedureDetail> targetProcedure = service.getAssemblyProcedureById(targetPartId);
    if (targetProcedure.isEmpty()) {
      model.addAttribute("errorMessage",
          "完成品部品IDにエラーがあります。入力情報を確認してください");
      return "selectOperation";
    }
    model.addAttribute("targetProcedure", service.getAssemblyProcedureById(targetPartId));
    return "targetProcedure";
  }

  /**
   * 新規部品情報の登録画面を表示します。
   *
   * @param part  新規塘路凶の空の部品情報オブジェクト
   * @param model 登録フォームに必要なモデル情報
   * @return 部品情報の新規登録画面のビュー名
   */
  @Operation(
      summary = "部品情報の登録画面を表示",
      description = "空の部品情報オブジェクトをフォームに渡し、ユーザーが入力する画面を表示します。"
  )
  @GetMapping("/parts/new")
  public String newPart(@ModelAttribute("part") Parts part, Model model) {
    model.addAttribute("part", part);
    return "registerPart";
  }

  /**
   * 新規部品の登録処理を行います。
   *
   * @param part   入力された部品情報データ
   * @param result バリデーション結果
   * @return 部品情報一覧画面へのリダイレクト
   */
  @Operation(
      summary = "部品情報の登録処理",
      description = "フォームから送信された部品情報の登録し、バリデーションエラー時は再入力画面へ、成功時は部品情報一覧画面へ遷移します。"
  )
  @PostMapping("/parts")
  public String registerPart(@Valid @ModelAttribute("part") Parts part, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("part", part);
      return "registerPart";
    }
    service.resisterNewPart(part);
    return "redirect:/parts";
  }

  /**
   * 組み立てに必要な部品情報の新規登録画面を表示します。
   *
   * @param requiredPart 新規登録用の空の必要部品情報オブジェクト
   * @param model        登録フォームに必要なモデル情報
   * @return 必要部品情報の新規登録画面のビュー名
   */
  @Operation(
      summary = "必要部品情報の登録画面を表示",
      description = "空の必要部品情報オブジェクトをフォームに渡し、ユーザーが入力する画面を表示します。"
  )
  @GetMapping("/parts/required/new")
  public String newRequiredPart(@ModelAttribute("requiredPart") RequiredParts requiredPart,
      Model model) {
    model.addAttribute("requiredPart", requiredPart);
    return "registerRequiredPart";
  }

  /**
   * 組み立てに必要な部品情報の登録処理を行います。
   *
   * @param requiredPart 入力された必要部品情報データ
   * @param result       バリデーション結果
   * @param action       ユーザーのアクション
   * @return 組み立て必要部品登録画面のビュー名(registerRequiredPart)またはユーザーが選択した操作へのリダイレクト
   */
  @Operation(
      summary = "必要部品情報の登録",
      description = "フォームから送信された必要部品情報の登録し、バリデーションエラー時は再入力画面へ、成功時はユーザーが選択した操作画面へ遷移します。"
  )
  @PostMapping("/parts/required")
  public String registerRequiredPart(
      @Valid @ModelAttribute("requiredPart") RequiredParts requiredPart,
      BindingResult result, Model model, @RequestParam("action") String action) {
    if (result.hasErrors()) {
      model.addAttribute("requiredPart", requiredPart);
      return "registerRequiredPart";
    }
    service.resisterNewRequiredPart(requiredPart);
    if ("add".equals(action)) {
      return "redirect:/parts/required/new";
    } else if ("finish".equals(action)) {
      AssemblyProcedure procedure = service.getAssemblyProcedureByProcedureId(
          requiredPart.getProcedureId());
      int targetPartId = procedure.getTargetPartId();
      return "redirect:/procedure/" + targetPartId;
    }
    return "registerRequiredPart";
  }

  /**
   * 組み立て手順情報の新規登録画面を表示します。
   *
   * @param assemProcedure 新規登録用の空の組み立て手順情報オブジェクト
   * @param model          登録フォームに必要なモデル情報
   * @return 組み立て手順情報の新規登録画面のビュー名
   */
  @Operation(
      summary = "組み立て手順情報の登録画面を表示",
      description = "空の組み立て手順情報オブジェクトをフォームに渡し、ユーザーが入力する画面を表示します。"
  )
  @GetMapping("/procedure/new")
  public String newProcedure(@ModelAttribute("assemProcedure") AssemblyProcedure assemProcedure,
      Model model) {
    model.addAttribute("assemProcedure", assemProcedure);
    return "registerAssemProcedure";
  }

  /**
   * 組み立て手順情報の新規登録処理を行います。
   *
   * @param assemProcedure 入力された組み立て手順情報データ
   * @param result         バリデーション結果
   * @return 組み立て手順情報登録画面のビュー名(registerAssemProcedure)または組み立て必要部品新規登録画面へのリダイレクト
   */
  @Operation(
      summary = "組み立て手順情報の登録処理",
      description = "フォームから送信された組み立て手順情報を登録し、バリデーションエラー時は再入力画面へ、成功時は必要部品情報入力画面へ遷移します。"
  )
  @PostMapping("/procedure")
  public String registerAssemProcedure(
      @Valid @ModelAttribute("assemProcedure") AssemblyProcedure assemProcedure,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("assemProcedure", assemProcedure);
      return "registerAssemProcedure";
    }
    service.resisterNewAssemblyProcedure(assemProcedure);
    return "redirect:/parts/required/new";
  }

  /**
   * 指定された部品IDに対応する部品情報を取得し、情報更新画面を表示します。
   *
   * @param partId 情報を更新したい部品ID
   * @param model  更新フォームに必要なモデル情報
   * @return 部品情報の更新画面のビュー名
   */
  @Operation(summary = "部品情報を取得し更新画面を表示",
      description = "指定された部品IDに紐づく部品情報を取得し、更新フォーム画面に表示します。")
  @GetMapping("/parts/update/{id}")
  public String getPartByID(@PathVariable("id") int partId, Model model) {
    model.addAttribute("updatePart", service.getPartById(partId));
    model.addAttribute("partId", partId);
    model.addAttribute("message", "更新する情報を入力してください");
    return "updatePartById";
  }

  /**
   * 指定された部品IDの情報を更新する処理を行います。
   *
   * @param partId 更新対象の部品ID
   * @param part   フォームから送信された更新対象部品データ
   * @param result バリデーション結果
   * @param model  ビューに渡すモデルオブジェクト
   * @return 部品情報更新画面のビュー名(updatePartById)または部品情報一覧へのリダイレクト
   */
  @Operation(summary = "部品情報を更新",
      description = "指定された部品IDの情報を更新。バリデーションエラー時は再入力画面へ、成功時は部品情報一覧画面へ遷移します。")
  @PostMapping("/parts/update/{id}")
  public String updatePartById(@PathVariable("id") int partId,
      @Valid @ModelAttribute("updatePart") Parts part, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("updatePart", part);
      model.addAttribute("partId", partId);
      return "updatePartById";
    }
    service.updatePartById(part);
    return "redirect:/parts";
  }

  /**
   * 指定された必要部品IDに対応する必要部品情報を取得し、情報更新画面を表示します。
   *
   * @param requiredPartId 情報を更新したい必要部品情報ID
   * @param model          更新フォームに必要なモデル情報
   * @return 必要部品情報の更新画面のビュー名
   */
  @Operation(summary = "必要部品情報を取得し更新画面を表示",
      description = "指定された必要部品IDに紐づく必要部品情報を取得し、更新フォーム画面に表示します。")
  @GetMapping("/parts/required/update/{id}")
  public String getRequiredPartByID(@PathVariable("id") int requiredPartId, Model model) {
    model.addAttribute("updateRequiredPart",
        service.getRequiredPartByRequiredPartId(requiredPartId));
    model.addAttribute("requiredPartId", requiredPartId);
    model.addAttribute("message", "更新する情報を入力してください");
    return "updateRequiredPart";
  }

  /**
   * 指定された必要部品IDの情報を更新する処理を行います。
   *
   * @param requiredPartId 更新対象の必要部品ID
   * @param requiredPart   フォームから送信された更新対象必要部品データ
   * @param result         バリデーション結果
   * @param model          ビューに渡すモデルオブジェクト
   * @return 必要部品情報更新画面のビュー名(updateRequiredPart)または組み立て手順詳細へのリダイレクト
   */
  @Operation(summary = "必要部品情報を更新",
      description = "指定された必要部品IDの情報を更新。バリデーションエラー時は再入力画面へ、成功時は組み立て手順情報詳細画面へ遷移します。")
  @PostMapping("/parts/required/update/{id}")
  public String updateRequiredPart(@PathVariable("id") int requiredPartId,
      @Valid @ModelAttribute("updateRequiredPart") RequiredParts requiredPart, BindingResult result,
      Model model, @RequestParam("action") String action) {
    if (result.hasErrors()) {
      model.addAttribute("updateRequiredPart", requiredPart);
      model.addAttribute("requiredPartId", requiredPartId);
      return "updateRequiredPart";
    }
    if ("add".equals(action)) {
      return "redirect:/parts/required/new";
    } else if ("finish".equals(action)) {
      service.updateRequiredPart(requiredPart);
      AssemblyProcedure procedure = service.getAssemblyProcedureByProcedureId(
          requiredPart.getProcedureId());
      int targetPartId = procedure.getTargetPartId();
      return "redirect:/procedure/" + targetPartId;
    }
    return "updateRequiredPart";
  }

  /**
   * 指定された組み立て手順IDに対応する組み立て手順情報を取得し、情報更新画面を表示します。
   *
   * @param procedureId 情報を更新したい組み立て手順ID
   * @param model       更新フォームに必要なモデル情報
   * @return 組み立て手順情報の更新画面のビュー名
   */
  @Operation(summary = "組み立て手順情報を取得し更新画面を表示",
      description = "指定された組み立て手順IDに紐づく組み立て手順情報を取得し、更新フォーム画面に表示します。")
  @GetMapping("/procedure/update/{id}")
  public String getAssemblyProcedureByID(@PathVariable("id") int procedureId, Model model) {
    model.addAttribute("updateProcedure", service.getAssemblyProcedureByProcedureId(procedureId));
    model.addAttribute("procedureId", procedureId);
    model.addAttribute("message", "更新する情報を入力してください");
    return "updateAssemblyProcedure";
  }

  /**
   * 指定された組み立て手順IDの情報を更新する処理を行います。
   *
   * @param procedureId       更新対象の組み立て手順ID
   * @param assemblyProcedure フォームから送信された更新対象組み立て手順データ
   * @param result            バリデーション結果
   * @param model             ビューに渡すモデルオブジェクト
   * @return 組み立て手順更新画面のビュー名(updateAssemblyProcedure)または組み立て手順詳細へのリダイレクト
   */
  @Operation(summary = "組み立て手順情報を更新",
      description = "指定された組み立て手順IDの情報を更新。バリデーションエラー時は再入力画面へ、成功時は組み立て手順情報詳細画面へ遷移します。")
  @PostMapping("/procedure/update/{id}")
  public String updateAssemblyProcedure(@PathVariable("id") int procedureId,
      @Valid @ModelAttribute("updateProcedure") AssemblyProcedure assemblyProcedure,
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("updateProcedure", assemblyProcedure);
      model.addAttribute("procedureId", procedureId);
      return "updateAssemblyProcedure";
    }
    service.updateAssemblyProcedure(assemblyProcedure);
    return "redirect:/procedure/" + assemblyProcedure.getTargetPartId();
  }

}
