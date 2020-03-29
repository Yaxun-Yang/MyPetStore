package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/main")
    public String viewMain()
    {
        return"/catalog/main";
    }

    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model)
    {

        //原解决方案未考虑用不合法的参数请求访问，此时也应该返回原页，通过重定向实现
        Category category = catalogService.getCategory(categoryId);
        if (categoryId != null) {
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("productList", productList);
            model.addAttribute("category", category);
            return "catalog/category";
        }
        return "redirect:catalog/main";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(String productId, Model model)
    {
        Product product = catalogService.getProduct(productId);
        if(product != null)
        {
            System.out.println(product.getName());
            List<Item> itemList = catalogService.getItemListByProduct(productId);
            model.addAttribute("itemList",itemList);
            model.addAttribute("product",product);
            return "catalog/product";
        }
        return "redirect:catalog/category?categoryId="+((Category)model.getAttribute("category")).getCategoryId();
    }

    @GetMapping("/viewItem")
    public  String viewItem(String itemId, Model model)
    {
        Item item = catalogService.getItem(itemId);
        if(item != null)
        {
            model.addAttribute("item",item);
            return "catalog/item";
        }
        return "redirect:catalog/product?productId="+((Product)model.getAttribute("product")).getProductId();
    }

}