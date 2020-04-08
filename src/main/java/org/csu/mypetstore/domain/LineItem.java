package org.csu.mypetstore.domain;


import java.math.BigDecimal;

public class LineItem{

    private int orderId;
    private int quantity;
    private String itemId;
    private BigDecimal unitPrice;
    private Item item;
    private BigDecimal total;

    public LineItem() {
    }

    public LineItem( CartItem cartItem) {
        this.quantity = cartItem.getQuantity();
        this.itemId = cartItem.getItemId();
        this.unitPrice = cartItem.getItem().getUnitCost();
        this.item = cartItem.getItem();
        calculateTotal();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitprice) {
        this.unitPrice = unitprice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        calculateTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    private void calculateTotal() {
        if (item != null && item.getUnitCost() != null) {
            total = item.getUnitCost().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }
}
