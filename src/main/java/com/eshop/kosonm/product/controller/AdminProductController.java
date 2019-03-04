package com.eshop.kosonm.product.controller;

import com.eshop.kosonm.product.Product;
import com.eshop.kosonm.product.ProductDao;
import com.eshop.kosonm.product.ProductForm;
import com.eshop.kosonm.product.ProductFormValidator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class AdminProductController {

    private ProductDao productDao;
    private ProductFormValidator productFormValidator;

    @Autowired
    public AdminProductController(ProductDao productDao, ProductFormValidator productFormValidator) {
        this.productDao = productDao;
        this.productFormValidator = productFormValidator;
    }

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator);
        }
    }

    @GetMapping("/admin/product")
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;

        if (StringUtils.isNotEmpty(code)) {
            Product product = productDao.findProduct(code);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }

    @PostMapping("/admin/product")
    public String productSave(Model model, @ModelAttribute("productForm") @Validated ProductForm productForm,
            BindingResult result, final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDao.save(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            return "product";
        }

        return "redirect:/productList";
    }

}