package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartItemMapper;
import org.csu.mypetstore.persistence.CartMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ItemMapper itemMapper;

    //新建购物车
    public void creatCart(String username)
    {
        cartMapper.addCart(username);
    }

    //删除购物车并删除其中的所有商品
    public void destroyCart(String username)
    {
        cartItemMapper.deleteCartItemByCart(cartMapper.getCartByUsername(username).getCartId());
        cartMapper.deleteCart(username);
    }

    //通过用户名获取购物车
    public Cart getCart(String username)
    {
        return cartMapper.getCartByUsername(username);
    }

    //获取购物车中的所有的商品
    //返回一个根据category排好序的序列
    public List<CartItem> getCartItemList(String cartId)
    {
        List<CartItem> cartItemList =  cartItemMapper.getCartItemList(cartId);
        List<CartItem> newCartItemList = new ArrayList<>();

        for(int i=0 ; i<cartItemList.size();i++)
        {
            if(cartItemList.get(i).getCategoryId().equals("FISH"))
            {
                newCartItemList.add(cartItemList.get(i));
            }
        }
        for(int i=0 ; i<cartItemList.size();i++)
        {
            if(cartItemList.get(i).getCategoryId().equals("DOGS"))
            {
                newCartItemList.add(cartItemList.get(i));
            }
        }
        for(int i=0 ; i<cartItemList.size();i++)
        {
            if(cartItemList.get(i).getCategoryId().equals("REPTILES"))
            {
                newCartItemList.add(cartItemList.get(i));
            }
        }
        for(int i=0 ; i<cartItemList.size();i++)
        {
            if(cartItemList.get(i).getCategoryId().equals("CATS"))
            {
                newCartItemList.add(cartItemList.get(i));
            }
        }
        for(int i=0 ; i<cartItemList.size();i++)
        {
            if(cartItemList.get(i).getCategoryId().equals("BIRDS"))
            {
                newCartItemList.add(cartItemList.get(i));
            }
        }
        return newCartItemList;
    }

    //获取购物车中特定的商品
    public CartItem getCartItem(String cartId, String itemId)
    {
        return cartItemMapper.getCartItem(cartId, itemId);
    }

   //修改商品数量并更新购物车商品状态
    public void updateCartItemQuantity(String cartId, String itemId, int quantity)
    {
        CartItem cartItem=cartItemMapper.getCartItem(cartId, itemId);
        if(quantity > 0)
        {
            if( cartItem== null)
            {
                cartItemMapper.insertCartItem(cartId,itemId,quantity);
            }
            else
            {
                cartItemMapper.updateQuantity(cartId,itemId,quantity);
            }

        }
        else if(cartItem != null)
        {
            cartItemMapper.deleteCartItem(cartId, itemId);
        }
    }

    //向购物车中添加商品
    public void addItem(String cartId, String itemId)
    {
            CartItem cartItem = cartItemMapper.getCartItem(cartId, itemId);
            if(cartItem == null)
            {
                cartItemMapper.insertCartItem(cartId,itemId,1);
            }
            else
            {
                cartItemMapper.updateQuantity(cartId,itemId,cartItem.getQuantity()+1);
            }

    }

    //获取某种商品总价钱
    public BigDecimal getCartItemTotalCost(String cartId,String cartItemId)
    {
       int quantity= cartItemMapper.getCartItem(cartId,cartItemId).getQuantity();
     //  System.out.println(itemMapper.getItem(cartItemId).getUnitCost().multiply(new BigDecimal(String.valueOf(quantity))));
       return itemMapper.getItem(cartItemId).getUnitCost().multiply(new BigDecimal(String.valueOf(quantity)));
    }

    //获取购物车商品总价
    public BigDecimal getCartTotalCost(String cartId)
    {
        BigDecimal subTotal = new BigDecimal(0);
        List<CartItem> cartItemList = cartItemMapper.getCartItemList(cartId);
        for (int i = 0;i<cartItemList.size();i++)
        {
          //  System.out.println(getCartItemTotalCost(cartId,cartItemList.get(i).getItemId()));
            subTotal=subTotal.add(getCartItemTotalCost(cartId,cartItemList.get(i).getItemId()));
        }
       // System.out.println("subTotal:"+subTotal);
        return subTotal;
    }


    //根据商品库存状态改变状态标签
    public void updateItemStatus(String itemId)
    {
        int quantity = itemMapper.getInventoryQuantity(itemId);
        Item item = itemMapper.getItem(itemId);
        if (quantity > 0 && item.getStatus().equals("N"))
        {
            itemMapper.setItemStatusP(itemId);
        }
        else if (quantity <= 0 && item.getStatus().equals("P"))
        {
            itemMapper.setItemStatusN(itemId);
        }
    }

    //修改商品库存数
    public void updateItemInventory(String itemId, int quantity)
    {
        itemMapper.updateInventoryQuantity(itemId,quantity);
    }

}
