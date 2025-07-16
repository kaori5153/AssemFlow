package com.kaori5153.assemflow.exception;

import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SpelEvaluationException.class)
  public String handleSpelError(SpelEvaluationException ex, Model model) {
    model.addAttribute("errorMessage", "エラーが発生しました。入力内容をご確認ください。");
    return "selectOperation";
  }
}
