package com.kaori5153.assemflow.controller;


import com.kaori5153.assemflow.data.AssemblyProcedure;
import com.kaori5153.assemflow.data.Parts;
import com.kaori5153.assemflow.data.RequiredParts;
import com.kaori5153.assemflow.domain.AssemblyProcedureDetail;
import com.kaori5153.assemflow.service.AssemblyProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Controller
public class AssemblyProcedureController {

  private AssemblyProcedureService service;

  @Autowired
  public AssemblyProcedureController(AssemblyProcedureService service) {
    this.service = service;
  }

  @Operation(summary = "部品情報一覧", description = "部品情報の一覧を検索します")
  @GetMapping("/parts")
  public String getPartsList(Model model) {
    model.addAttribute("parts", service.getAllParts());
    return "parts";
  }

  @GetMapping("/parts/required")
  public List<RequiredParts> getRequiredPartsList() {
    return service.getAllRequiredPartsList();
  }

  @GetMapping("/procedure")
  public String getAssemblyProcedureDetailsList(Model model) {
    model.addAttribute("procedure", service.getAllAssemblyProcedureList());
    return "procedure";
  }

  @GetMapping("/parts/{id}")
  public String getPart(@PathVariable("id") int id, Model model) {
    model.addAttribute("part", service.getPartById(id));
    return "part";
  }

  @GetMapping("/parts/name")
  public Parts getPartByName(@RequestParam String partName) {
    return service.getPartByName(partName);
  }

  @GetMapping("/parts/required/{id}")
  public RequiredParts getRequiredPartInfo(@PathVariable("id") int partId) {
    return service.getRequiredPartByPartId(partId);
  }

  @GetMapping("/procedure/{id}")
  public String getAssemblyProcedure(@PathVariable("id") int id, Model model) {
    model.addAttribute("targetProcedure", service.getAssemblyProcedureById(id));
    return "targetProcedure";
//    RestController確認用
//    public List<AssemblyProcedureDetail> getAssemblyProcedure(@PathVariable("id") int id) {
//    return service.getAssemblyProcedureById(id);
  }

  @GetMapping("/parts/new")
  public String newPart(@ModelAttribute("part") Parts part, Model model) {
    model.addAttribute("part", part);
    return "registerPart";
  }

  @PostMapping("/parts")
  public String registerPart(@ModelAttribute Parts part, BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error.toString()));
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
  @GetMapping("/parts/required/new")
  public String newRequiredPart(@ModelAttribute("requiredPart") RequiredParts requiredPart,
      Model model) {
    model.addAttribute("requiredPart", requiredPart);
    return "registerRequiredPart";
  }

  /**
   * 組み立てに必要な部品部品情報の登録処理を行います。
   *
   * @param requiredPart 入力された必要部品情報データ
   * @param result       バリデーション結果
   * @return 登録した部品の組み立て手順情報詳細へのリダイレクト
   */
  @PostMapping("/parts/required")
  public String registerRequiredPart(@ModelAttribute RequiredParts requiredPart,
      BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error.toString()));
      return "registerRequiredPart";
    }
    service.resisterNewRequiredPart(requiredPart);
    AssemblyProcedure procedure = service.getAssemblyProcedureByProcedureId(
        requiredPart.getProcedureId());
    int targetPartId = procedure.getTargetPartId();
    return "redirect:/procedure/" + targetPartId;
  }

  @GetMapping("/procedure/new")
  public String newProcedure(@ModelAttribute("assemProcedure") AssemblyProcedure assemProcedure,
      Model model) {
    model.addAttribute("assemProcedure", assemProcedure);
    return "registerAssemProcedure";
  }

  @PostMapping("/procedure")
  public String registerAssemProcedure(
      @ModelAttribute AssemblyProcedure assemProcedure, BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> System.out.println(error.toString()));
      return "registerAssemProcedure";
    }
    service.resisterNewAssemblyProcedure(assemProcedure);
    return "redirect:/parts/required/new";
  }

  @PutMapping("/parts")
  public ResponseEntity<String> updatePartById(@RequestBody Parts part) {
    service.updatePartById(part);
    return ResponseEntity.ok("更新処理完了");
  }

  @PutMapping("/parts/name")
  public ResponseEntity<String> updatePartByPartName(@RequestBody Parts part) {
    service.updatePartByPartName(part);
    return ResponseEntity.ok("更新処理完了");
  }

  @PutMapping("/parts/required")
  public ResponseEntity<String> updateRequiredPart(@RequestBody RequiredParts requiredPart) {
    service.updateRequiredPart(requiredPart);
    return ResponseEntity.ok("更新処理完了");
  }

  @PutMapping("/procedure")
  public ResponseEntity<String> updateAssemblyProcedure(
      @RequestBody AssemblyProcedure assemblyProcedure) {
    service.updateAssemblyProcedure(assemblyProcedure);
    return ResponseEntity.ok("更新処理完了");
  }

}
