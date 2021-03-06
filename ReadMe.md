这本来是个单纯的API文档，后来懒得写ReadMe，所以把这个改成ReadMe
那啥……既然都已经是ReadMe了，不如再放个项目笔记[blog](https://yaxun-yang.github.io/springmvc%E5%AD%A6%E4%B9%A0%E7%9B%AE%E5%BD%95/)

# MyPetStore API 说明文档
这是一个什么格式都没有的后端乱写的API接口，相信我机智的前端一定能看懂,顺便mark一下任劳任怨而的后端都干了些什么好事（maybe？）

## 已实现功能
- catalog的所有页面跳转( controller 添加参数错误的重定向解决方案)
- addItem的购物车跳转(小弹窗弹出成功，结果前端还逼着后端去实现数秒消失：(((
- 购物车实时更新(引入JSON)
- 购物车checkedAll绑定
- 登录发送短信验证码（阿里云短信服务）
- 引入AOP

## 全局设置
- web服务器端口使用8081
- 数据库端口使用3306
- 数据库名为`mypetstore`
- 数据库用户名`root`
- 数据库密码为空
- 数据库url win操作系统上需要添加参数`serverTimezone=UTC`， mac 上需要去掉参数


## 页面跳转请求

请求类型皆为 get

|请求页面|链接字段|参数|需要数据|
|--- |---|---|---|
|main|`/catalog/viewMain`|||
|category|`/catalog/viewCategory`|String:categoryId|Category:category List\<Product\>productList|
|product|`/catalog/viewProduct`|String:productId|Product:product List\<Item\>itemList|
|item|`/catalog/viewItem`|String:itemId|Item:item Product:product int:itemQuantity|
|searchProducts|`/catalog/viewSearchProducts`|String:keyword|List\<Product\>:productList|
|cart|`/cart/viewCart`||Cart:cart int:cartItemNumber List\<CartItem\>cartItemList BigDecimal:subTol |
|signInForm|`/account/viewSignInForm`|||
|newAccountForm|`/account/viewNewAccountForm`|||
|editAccountForm|`/account/viewEditAccountForm`|||

## 表单提交请求
请求类型皆为post

|表单提交页面|链接字段|需要数据|
|---|---|---|
|cart|`/cart/viewCheckOut`|List\<CartItem\>:cartItemList BigDecimal:subTol|
|signInForm|`/account/postSignInForm`|String:username String:password String:phoneNumber String:verifyCode|
|newAccountForm|`/account/postNewAccountForm`|String:username String:password String:repeatedPassword|
|editAccountForm|`/account/postEditAccountForm`|Account:account String:username String:password String:repeatedPassword String:firstName String:lastName String:email String:phone String:address1 String:address2 String:city String:state String:zip String:country|

## 功能请求
|功能描述|请求类型|链接字段|参数|返回数据（JSON格式）|
|---|---|---|---|---|
|购物车中商品数量实时更新|post|`/cart/updateItemQuantity`|String:itemId int:quantity|int:quantity BigDecimal:totCost BigDecimal:subTol|
|向购物车中添加新商品|get|`/cart/addItem`|String:itemId||
|向用户手机发送验证码|post|`/account/sendVerificationCode`|String:phoneNumber String:username String:password||

## 关于功能构想（将实现）
### 必须要做的
- 修改searchProduct.js使运行
- 购物车页面item以map形式输出

### 可能咕掉的
- 购物车中按照category checkout 绑定
- 登录页面验证码实现60秒倒计时
- 购物车内商品数量错误弹出弹窗提醒并不执行
- 购物车提醒弹窗的数秒消失
