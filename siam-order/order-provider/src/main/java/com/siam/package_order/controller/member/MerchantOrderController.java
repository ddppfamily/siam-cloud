package com.siam.package_order.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.package_common.service.AliyunSms;
import com.siam.package_weixin_basic.service.WxNotifyService;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.package_common.util.DateUtilsExtend;
import com.siam.package_common.util.GsonUtils;
import com.siam.package_common.util.StringUtils;
import com.siam.package_feign.mod_feign.goods.CouponsMemberRelationFeignClient;
import com.siam.package_feign.mod_feign.user.MemberBillingRecordFeignClient;
import com.siam.package_feign.mod_feign.user.MemberFeignClient;
import com.siam.package_order.entity.Order;
import com.siam.package_order.entity.OrderRefund;
import com.siam.package_order.entity.OrderRefundProcess;

import com.siam.package_order.model.example.OrderExample;
import com.siam.package_order.model.param.OrderParam;
import com.siam.package_order.service.OrderRefundGoodsService;
import com.siam.package_order.service.OrderRefundProcessService;
import com.siam.package_order.service.OrderRefundService;
import com.siam.package_order.service.OrderService;
import com.siam.package_user.auth.cache.MemberSessionManager;
import com.siam.package_user.auth.cache.MerchantSessionManager;
import com.siam.package_user.entity.Member;
import com.siam.package_user.entity.MemberBillingRecord;
import com.siam.package_user.entity.Merchant;
import com.siam.package_user.model.example.MemberBillingRecordExample;
import com.siam.package_user.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping(value = "/rest/merchant/order")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端订单模块相关接口", description = "MerchantOrderController")
public class MerchantOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AliyunSms aliyunSms;

//    @Autowired
//    private MerchantTokenService merchantTokenService;
//
//    @Autowired
//    private MerchantFeignClient merchantFeignClient;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private OrderRefundGoodsService orderRefundGoodsService;

    @Autowired
    private OrderRefundProcessService orderRefundProcessService;

    @Autowired
    private WxNotifyService wxNotifyService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private MemberBillingRecordFeignClient memberBillingRecordFeignClient;

    @Autowired
    private CouponsMemberRelationFeignClient couponsMemberRelationFeignClient;

    private Lock lock = new ReentrantLock();

    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "memberId", value = "用户id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderNo", value = "订单编号，供客户查询", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsTotalQuantity", value = "商品总数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsTotalPrice", value = "商品总金额", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "packingCharges", value = "包装费", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "deliveryFee", value = "配送费", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "actualPrice", value = "实付款", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "shoppingWay", value = "购物方式 1=自取 2=配送", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "deliveryAddressId", value = "收货地址id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "contactRealname", value = "联系人姓名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactProvince", value = "省份", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactCity", value = "城市", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactArea", value = "区/县", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactStreet", value = "详细地址(具体到街道门牌号)", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactSex", value = "联系人性别 0=无 1=先生 2=女士", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "订单描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "订单状态 -1=售后处理状态 1=未付款 2=待处理 3=待自取(已处理) 4=待配送(已处理) 5=已配送 6=已完成 7=售后处理中 8=已退款 9=售后处理完成 10=已取消", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tradeId", value = "用户交易id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderLogisticsId", value = "物流id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isInvoice", value = "是否开票", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "invoiceId", value = "发票id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDeleted", value = "是否删除 0=正常 1=已删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopId", value = "接单门店id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopName", value = "接单门店名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) OrderParam param, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        param.setShopId(loginMerchant.getShopId());
        Page page = orderService.getListByPageWithAsc(param);

        return BasicResult.success(page);
    }


    @ApiOperation(value = "修改订单状态")
    @PostMapping(value = "/updateStatus")
    public BasicResult updateStatus(@RequestBody @Validated(value = {}) OrderParam param){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if(param.getFlag()!= Quantity.INT_1 && param.getFlag()!=Quantity.INT_2 && param.getFlag()!=Quantity.INT_3 && param.getFlag()!=Quantity.INT_4){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("标识错误");
            return basicResult;
        }

        Order dbOrder = orderService.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该订单不存在");
            return basicResult;
        } else if (loginMerchant.getShopId() != dbOrder.getShopId()){
            throw new StoneCustomerException("您没有权限操作该订单");
        }

        //获取该订单的对应用户
        Member orderMember = memberFeignClient.selectByPrimaryKey(dbOrder.getMemberId());

        int status = 0;
        switch (param.getFlag()){
            //处理订单
            case Quantity.INT_1:
                if(dbOrder.getStatus() != Quantity.INT_2){
                    basicResult.setSuccess(false);
                    basicResult.setCode(BasicResultCode.ERR);
                    basicResult.setMessage("该订单状态非待处理，不允许修改");
                    return basicResult;
                }
                //自取订单，将状态改为3=待自取(已处理)；配送订单，将状态改为4=待配送(已处理)
                if(dbOrder.getShoppingWay() == Quantity.INT_1){
                    status = Quantity.INT_3;
                }else if(dbOrder.getShoppingWay() == Quantity.INT_2){
                    status = Quantity.INT_4;
                }
                break;

            //标记完成(自取订单)
            case Quantity.INT_2:
                if(dbOrder.getStatus() != Quantity.INT_3){
                    basicResult.setSuccess(false);
                    basicResult.setCode(BasicResultCode.ERR);
                    basicResult.setMessage("该订单状态非待自取，不允许修改");
                    return basicResult;
                }
                //将状态改为6=已完成
                status = Quantity.INT_6;
                break;

            //标记配送
            case Quantity.INT_3:
                if(dbOrder.getStatus() != Quantity.INT_4){
                    basicResult.setSuccess(false);
                    basicResult.setCode(BasicResultCode.ERR);
                    basicResult.setMessage("该订单状态非待配送，不允许修改");
                    return basicResult;
                }
                //将状态改为5=已配送
                status = Quantity.INT_5;
                break;

            //标记完成(配送订单)
            case Quantity.INT_4:
                if(dbOrder.getStatus() != Quantity.INT_5){
                    basicResult.setSuccess(false);
                    basicResult.setCode(BasicResultCode.ERR);
                    basicResult.setMessage("该订单状态非已配送，不允许修改");
                    return basicResult;
                }
                //将状态改为6=已完成
                status = Quantity.INT_6;
                break;
        }

        // 更新Order数据
        Order updateOrder = new Order();
        updateOrder.setId(dbOrder.getId());
        updateOrder.setStatus(status);
        if(status == Quantity.INT_6){
            updateOrder.setOrderCompletionTime(new Date());
        }
        orderService.updateByPrimaryKeySelective(updateOrder);

        //发送短信提醒自提
        if(status==Quantity.INT_3){
            // 发送短信提醒自提
            aliyunSms.sendPickUpOrderCompleteMessage(dbOrder.getContactPhone(), String.valueOf(dbOrder.getQueueNo()), dbOrder.getShopName());
            //发送微信服务通知
            wxNotifyService.sendPickUpOrderCompleteMessage(orderMember.getOpenId(), String.valueOf(dbOrder.getQueueNo()), dbOrder.getShopName(), dbOrder.getShopAddress(), dbOrder.getDescription());

        }else if(status==Quantity.INT_5){
            // 发送短信提醒正在配送中
            aliyunSms.sendTakeOutOrderDeliveryMessage(dbOrder.getContactPhone());
            //发送微信服务通知
            String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
            wxNotifyService.sendTakeOutOrderDeliveryMessage(orderMember.getOpenId(), String.valueOf(dbOrder.getQueueNo()), dbOrder.getShopName(), addressStr, dbOrder.getDescription());
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @ApiOperation(value = "查询单个订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单表主键id", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) OrderParam param){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderService.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该订单不存在");
            return basicResult;
        } else if (loginMerchant.getShopId() != dbOrder.getShopId()){
            throw new StoneCustomerException("您没有权限操作该菜单");
        }

        basicResult.setData(dbOrder);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @ApiOperation(value = "批量修改订单的是否已打印状态为已打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idListStr", value = "订单表主键id", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/batchUpdateIsPrintedTrue")
    public BasicResult batchUpdateIsPrintedTrue(@RequestBody @Validated(value = {}) OrderParam param){
        BasicResult basicResult = new BasicResult();
//        String idListStr
        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if(StringUtils.isEmpty(param.getIdListStr())){
            throw new StoneCustomerException("订单id不能为空");
        }

        List<Integer> idList = GsonUtils.toList(param.getIdListStr(), Integer.class);
        if(idList!=null && idList.size()>0){
            orderService.batchUpdateIsPrintedTrue(idList);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @ApiOperation(value = "今日订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "memberId", value = "用户id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderNo", value = "订单编号，供客户查询", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsTotalQuantity", value = "商品总数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsTotalPrice", value = "商品总金额", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "packingCharges", value = "包装费", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "deliveryFee", value = "配送费", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "actualPrice", value = "实付款", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "shoppingWay", value = "购物方式 1=自取 2=配送", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "deliveryAddressId", value = "收货地址id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "contactRealname", value = "联系人姓名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactPhone", value = "联系电话", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactProvince", value = "省份", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactCity", value = "城市", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactArea", value = "区/县", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactStreet", value = "详细地址(具体到街道门牌号)", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contactSex", value = "联系人性别 0=无 1=先生 2=女士", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "订单描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "订单状态 -1=售后处理状态 1=未付款 2=待处理 3=待自取(已处理) 4=待配送(已处理) 5=已配送 6=已完成 7=售后处理中 8=已退款 9=售后处理完成 10=已取消", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tradeId", value = "用户交易id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderLogisticsId", value = "物流id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isInvoice", value = "是否开票", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "invoiceId", value = "发票id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDeleted", value = "是否删除 0=正常 1=已删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopId", value = "接单门店id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopName", value = "接单门店名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "startCreateTime", value = "下单开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endCreateTime", value = "下单结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/todayOrderList")
    public BasicResult todayOrderList(@RequestBody @Validated(value = {}) OrderParam param, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        param.setShopId(loginMerchant.getShopId());
        Page page = orderService.getListByTodayOrderWithAsc(param);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "查询所有订单标签页的待处理数量")
    @PostMapping(value = "/selectAllTabWaitHandleNum")
    public BasicResult selectAllTabWaitHandleNum(@RequestBody @Validated(value = {}) OrderParam param){
        BasicData basicResult = new BasicData();
        Map dataMap = new HashMap();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        //自取订单-待制作订单
        OrderExample example = new OrderExample();
        example.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andShoppingWayEqualTo(Quantity.INT_1).andStatusEqualTo(Quantity.INT_2);
        int waitHandleNum = orderService.countByExample(example);
        dataMap.put("waitHandleNum", waitHandleNum);

        //自取订单-待自取订单
        example = new OrderExample();
        example.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andShoppingWayEqualTo(Quantity.INT_1).andStatusEqualTo(Quantity.INT_3);
        int waitPickUpNum = orderService.countByExample(example);
        dataMap.put("waitPickUpNum", waitPickUpNum);

        //外卖订单-待配送订单
        example = new OrderExample();
        example.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andShoppingWayEqualTo(Quantity.INT_2).andStatusEqualTo(Quantity.INT_4);
        int waitDeliveryNum = orderService.countByExample(example);
        dataMap.put("waitDeliveryNum", waitDeliveryNum);

        //外卖订单-已配送订单
        example = new OrderExample();
        example.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andShoppingWayEqualTo(Quantity.INT_2).andStatusEqualTo(Quantity.INT_5);
        int deliveredNum = orderService.countByExample(example);
        dataMap.put("deliveredNum", deliveredNum);

        basicResult.setData(dataMap);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @ApiOperation(value = "查询所有已付款未打印的订单")
    @PostMapping(value = "/waitPrintOrderList")
    public BasicResult waitPrintOrderList(@RequestBody @Validated(value = {}) OrderParam param){
        BasicData basicResult = new BasicData();
        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        List<Order> orders = null;

        lock.lock();
        try {
            //排除掉状态为1=未付款、10=已取消的订单
            List<Integer> excludedStatusList = new ArrayList<>();
            excludedStatusList.add(Quantity.INT_1);
            excludedStatusList.add(Quantity.INT_10);
            OrderExample example = new OrderExample();
            example.createCriteria().andShopIdEqualTo(loginMerchant.getShopId()).andStatusNotIn(excludedStatusList).andIsPrintedEqualTo(false);
            orders = orderService.selectByExample(example);

            //为了解决以下两个问题，此处需要同步修改以上订单的打印状态为已打印，需要做并发控制
            //1.浏览器打开多个商家端页面时，不造成重复打印的问题
            //2.商家端将数据传递给打印机后中途打印失败时(如打印机异常、卡纸、缺纸的情况)，不再对该订单进行打印
            if(orders!=null && orders.size()>0){
                //定义修改条件
                List<Integer> idList = new ArrayList<>();
                orders.forEach(order -> {
                    idList.add(order.getId());
                });
                example = new OrderExample();
                example.createCriteria().andIdIn(idList);
                //定义需要修改的信息
                Order order = new Order();
                order.setIsPrinted(true);
                //批量修改以上订单的打印状态为已打印
                orderService.updateByExampleSelective(order, example);
            }
        } finally {
            lock.unlock();
        }

        basicResult.setData(orders);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @ApiOperation(value = "订单统计(支付成功订单数量、取消订单数量、退款订单数量，按自取或者外卖分开)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startCreateTime", value = "下单开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endCreateTime", value = "下单结束时间", required = false, paramType = "query", dataType = "Date"),
    })
    @PostMapping(value = "/countOrder")
    public BasicResult countOrder(@RequestBody @Validated(value = {}) OrderParam order, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        order.setShopId(loginMerchant.getShopId());
        Map count = orderService.countOrder(order);
        basicResult.setData(count);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @ApiOperation(value = "售后处理订单列表")
    @PostMapping(value = "/afterSalesList")
    public BasicResult afterSalesList(@RequestBody @Validated(value = {}) OrderParam param, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        param.setShopId(loginMerchant.getShopId());
        Page page = orderService.getAfterSalesListByPageWithAsc(param);

        return BasicResult.success(page);
    }

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    /**
     * 售后处理订单-处理
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/auditAfterSalesOrder")
    public BasicResult auditAfterSalesOrder(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) OrderParam orderParam, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        if(orderParam.getStatus() == Quantity.INT_2 && org.apache.commons.lang3.StringUtils.isBlank(orderParam.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderService.selectByPrimaryKey(orderParam.getId());
        if(dbOrder == null) throw new StoneCustomerException("该订单不存在");
        if(dbOrder.getShopId() != loginMerchant.getShopId()) throw new StoneCustomerException("您没有权限处理该订单");

        OrderRefund dbOrderRefund = orderRefundService.selectByOrderId(orderParam.getId());
        if(dbOrderRefund == null) throw new StoneCustomerException("该订单无退款记录");
        if(dbOrderRefund.getStatus()!=Quantity.INT_1 && dbOrderRefund.getStatus()!=Quantity.INT_2){
            throw new StoneCustomerException("该售后订单已被处理过，不允许操作");
        }

        //获取该订单的对应用户
        Member orderMember = memberFeignClient.selectByPrimaryKey(dbOrder.getMemberId());

        if(orderParam.getStatus() == Quantity.INT_1){
            //审核通过

            //修改订单记录状态-售后处理完成
            Order updateOrder = new Order();
            updateOrder.setId(orderParam.getId());
            updateOrder.setStatus(Quantity.INT_9);
            orderService.updateByPrimaryKeySelective(updateOrder);

            //进行订单自动退款操作
            boolean isRefundSuccess = false;
            if(dbOrder.getPaymentMode().equals(Quantity.INT_1)){
                //微信支付
                isRefundSuccess = wxPayService.refund(dbOrder.getOrderNo(), dbOrder.getActualPrice(), dbOrderRefund.getRefundAmount());

            }else if(dbOrder.getPaymentMode().equals(Quantity.INT_2)){
                //余额支付
                //增加用户的余额
                Member updateMember = new Member();
                updateMember.setId(orderMember.getId());
                updateMember.setBalance(orderMember.getBalance().add(dbOrderRefund.getRefundAmount()));
                updateMember.setTotalConsumeBalance(orderMember.getTotalConsumeBalance().subtract(dbOrderRefund.getRefundAmount()));
                memberFeignClient.updateByPrimaryKeySelective(updateMember);
                orderMember = memberFeignClient.selectByPrimaryKey(dbOrder.getMemberId());

                //添加账单记录
                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(orderMember.getId());
                memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REFUND_RETURN_BALANCE);
                memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
                memberBillingRecord.setNumber(dbOrderRefund.getRefundAmount());
                memberBillingRecord.setMessage("用户申请退款-余额退回");
                memberBillingRecord.setCreateTime(new Date());
                memberBillingRecordFeignClient.insertSelective(memberBillingRecord);

                //退款成功的操作
                orderService.updateRefundStatus(dbOrder.getOrderNo());

                isRefundSuccess = true;
            }
            if(!isRefundSuccess){
                throw new StoneCustomerException("退款失败，请联系管理员");
            }

            //5）只有在退了使用优惠券的商品时，优惠券才会被退回
            if(dbOrderRefund.getIsUsedCoupons()){
                //退回优惠卷
                Integer couponsMemberRelationId = dbOrder.getCouponsMemberRelationId();
                if (couponsMemberRelationId != null) {
                    couponsMemberRelationFeignClient.updateCouponsUsed(couponsMemberRelationId,false);
                }
            }

            //修改退款状态 -- 退款成功
            OrderRefund updateOrderRefund = new OrderRefund();
            updateOrderRefund.setId(dbOrderRefund.getId());
            updateOrderRefund.setStatus(Quantity.INT_7);
            orderRefundService.updateByPrimaryKeySelective(updateOrderRefund);

            //添加退款流程
            OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
            orderRefundProcess.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess.setName("退款成功");
            orderRefundProcess.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess);

            //发送服务通知
//            wxNotifyService.sendOrderRefundSuccessMessage(orderMember.getOpenId(), dbOrder.getShopName(), dbOrder.getOrderNo(), dbOrder.getDescription(), dbOrderRefund.getRefundAmount(), new Date());
            //退回下单奖励积分
            MemberBillingRecordExample example = new MemberBillingRecordExample();
            example.createCriteria().andTypeEqualTo(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS)
                    .andOrderIdEqualTo(dbOrder.getId());
            List<MemberBillingRecord> list = memberBillingRecordFeignClient.selectByExample(example);
            if(!list.isEmpty()){
                MemberBillingRecord dbMemberBillingRecord = list.get(0);
                Member dbMember = memberFeignClient.selectByPrimaryKey(dbOrder.getMemberId());
                //获取用户当前积分数 -- 未到账积分
                BigDecimal unreceivedPointsNum = dbMember.getUnreceivedPoints().subtract(dbMemberBillingRecord.getNumber());
                //修改用户的积分数
                Member member = new Member();
                member.setId(dbOrder.getMemberId());
                member.setUnreceivedPoints(unreceivedPointsNum);
                memberFeignClient.updateByPrimaryKeySelective(member);
                dbMember = memberFeignClient.selectByPrimaryKey(dbOrder.getMemberId());

                MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                memberBillingRecord.setMemberId(dbOrder.getMemberId());
                memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_POINTS);
                memberBillingRecord.setType(MemberBillingRecord.TYPE_ORDER_REWARD_POINTS_RETURN);
                memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
                memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                memberBillingRecord.setMessage("订单退款-下单奖励积分退回");
                memberBillingRecord.setOrderId(dbOrder.getId());
                memberBillingRecord.setCreateTime(new Date());
                memberBillingRecordFeignClient.insertSelective(memberBillingRecord);

                //之前的账单记录标注已退回
                MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                updateMemberBillingRecord.setIsReturn(true);
                memberBillingRecordFeignClient.updateByPrimaryKeySelective(updateMemberBillingRecord);
            }

            //退回下单佣金奖励
            List typeList = new ArrayList();
            typeList.add(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION);
            typeList.add(MemberBillingRecord.TYPE_OWN_COMMISSION);
            example = new MemberBillingRecordExample();
            example.createCriteria().andTypeIn(typeList)
                    .andOperateTypeEqualTo(MemberBillingRecord.OPERATE_TYPE_ADD)
                    .andCoinTypeEqualTo(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)
                    .andOrderIdEqualTo(dbOrder.getId());
            list = memberBillingRecordFeignClient.selectByExample(example);
            if(!list.isEmpty()){
                for (MemberBillingRecord dbMemberBillingRecord : list) {
                    Integer type = null;
                    String message = "";
                    if(dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION)){
                        type = MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION_RETURN;
                        message = "订单退款-一级邀请人佣金奖励退回";
                    }else if(dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION)){
                        type = MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION_RETURN;
                        message = "订单退款-二级邀请人佣金奖励退回";
                    }else if(dbMemberBillingRecord.getType().equals(MemberBillingRecord.TYPE_OWN_COMMISSION)){
                        type = MemberBillingRecord.TYPE_OWN_COMMISSION_RETURN;
                        message = "订单退款-下单用户佣金奖励退回";
                    }

                    Member dbMember = memberFeignClient.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
                    //增加用户的邀请新用户注册奖励金额
                    BigDecimal updateUnreceivedInviteRewardAmount = dbMember.getUnreceivedInviteRewardAmount().subtract(dbMemberBillingRecord.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);

                    Member updateMember = new Member();
                    updateMember.setId(dbMember.getId());
                    updateMember.setUnreceivedInviteRewardAmount(updateUnreceivedInviteRewardAmount);
                    updateMember.setUpdateTime(new Date());
                    memberFeignClient.updateByPrimaryKeySelective(updateMember);
                    dbMember = memberFeignClient.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());

                    //增加用户账单记录
                    MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
                    memberBillingRecord.setMemberId(dbMember.getId());
                    memberBillingRecord.setType(type);
                    memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_SUB);
                    memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT);
                    memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
                    memberBillingRecord.setMessage(message);
                    memberBillingRecord.setOrderId(dbOrder.getId());
                    memberBillingRecord.setCreateTime(new Date());
                    memberBillingRecordFeignClient.insertSelective(memberBillingRecord);

                    //之前的账单记录标注已退回
                    MemberBillingRecord updateMemberBillingRecord = new MemberBillingRecord();
                    updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
                    updateMemberBillingRecord.setIsReturn(true);
                    memberBillingRecordFeignClient.updateByPrimaryKeySelective(updateMemberBillingRecord);
                }
            }
        }else if(orderParam.getStatus() == Quantity.INT_2){
            //审核不通过

            //修改订单记录状态-售后处理完成
            Order updateOrder = new Order();
            updateOrder.setId(orderParam.getId());
            updateOrder.setStatus(Quantity.INT_9);
            orderService.updateByPrimaryKeySelective(updateOrder);

            //修改退款状态 -- 退款已关闭
            OrderRefund updateOrderRefund = new OrderRefund();
            updateOrderRefund.setId(dbOrderRefund.getId());
            updateOrderRefund.setStatus(Quantity.INT_6);
            orderRefundService.updateByPrimaryKeySelective(updateOrderRefund);

            //添加退款流程 -- 商家拒绝退款
            OrderRefundProcess orderRefundProcess = new OrderRefundProcess();
            orderRefundProcess.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess.setName("商家拒绝退款");
            orderRefundProcess.setDescription(orderParam.getOpinion());
            orderRefundProcess.setCreateTime(new Date());
            orderRefundProcessService.insertSelective(orderRefundProcess);

            //添加退款流程 -- 退款关闭
            OrderRefundProcess orderRefundProcess_second = new OrderRefundProcess();
            orderRefundProcess_second.setOrderRefundId(dbOrderRefund.getId());
            orderRefundProcess_second.setName("退款关闭");
            orderRefundProcess_second.setCreateTime(DateUtilsPlus.addSeconds(new Date(), 5));
            orderRefundProcessService.insertSelective(orderRefundProcess_second);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("审核成功");
        return basicResult;
    }


    /**
     * 查询订单统计数据
     * PS；一次性将所有数据全部查出来，而不是每次选择时间段时来实时请求数据
     */
    @PostMapping(value = "/statistic")
    public BasicResult statisticOrder(@RequestBody @Validated(value = {}) Order param) throws ParseException {
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Map resultMap = new HashMap();

        //只统计状态为[6=已完成 7=售后处理中 9=售后处理完成]的订单数据

        //日期、订单数量、订单金额

        //1、查询开始日期(系统/订单服务第一次上线日期) - 今天
        /*String startDate = "2020-05-10";*/
        String startDate = orderService.selectStartDateOrder(loginMerchant.getShopId());
        if(startDate == null){
            startDate = DateUtilsPlus.formatDate(new Date(), "YYYY-MM-dd");
        }
        String endDate = DateUtilsPlus.formatDate(new Date(), "YYYY-MM-dd");
        List<String> betweenDays = DateUtilsPlus.getBetweenDays(startDate, endDate);

        //2、构造日期列表
        Map<String, Map> filterMap = new HashMap();
        List<Map<String, Object>> resultList = new ArrayList();
        for (String dateStr : betweenDays) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", dateStr); //前端的变量名为date
            map.put("orderCount", 0);
            map.put("orderAmount", 0);
            resultList.add(map);
            //记录日期对应的集合元素，便于后续赋值
            filterMap.put(dateStr, map);
        }

        //3、查询每一个日期对应的 订单数量、订单金额
        List<Map<String, Object>> statisticList = orderService.selectStatisticOrder(loginMerchant.getShopId());
        statisticList.forEach(statisticMap -> {
            if(filterMap.containsKey(statisticMap.get("orderDate"))){
                //修改订单数据
                Map map = filterMap.get(statisticMap.get("orderDate"));
                map.put("orderCount", statisticMap.get("orderCount"));
                map.put("orderAmount", statisticMap.get("merchantIncome")); //这里赋值的是商家实际收入，不是订单实付款
            }
        });

        resultMap.put("resultList", resultList);


        //订单表筛选条件-当前登录商家
        OrderParam order = new OrderParam();
        order.setShopId(loginMerchant.getShopId());

        //本月订单总数、上月订单总数、同比情况
        order.setStartTime(DateUtilsExtend.getBeginDayOfMonth());
        order.setEndTime(DateUtilsExtend.getEndDayOfMonth());
        int thisMonthCountPaid = orderService.selectCountCompleted(order);

        order.setStartTime(DateUtilsExtend.getBeginDayOfLastMonth());
        order.setEndTime(DateUtilsExtend.getEndDayOfLastMonth());
        int lastMonthCountPaid = orderService.selectCountCompleted(order);

        //本周订单总数、上周订单总数、同比情况
        order.setStartTime(DateUtilsExtend.getBeginDayOfWeek());
        order.setEndTime(DateUtilsExtend.getEndDayOfWeek());
        int thisWeekCountPaid = orderService.selectCountCompleted(order);

        order.setStartTime(DateUtilsExtend.getBeginDayOfLastWeek());
        order.setEndTime(DateUtilsExtend.getEndDayOfLastWeek());
        int lastWeekCountPaid = orderService.selectCountCompleted(order);

        //本月销售总额、上月销售总额、同比情况
        order.setStartTime(DateUtilsExtend.getBeginDayOfMonth());
        order.setEndTime(DateUtilsExtend.getEndDayOfMonth());
        BigDecimal thisMonthSumActualPrice = orderService.selectSumActualPrice(order);

        order.setStartTime(DateUtilsExtend.getBeginDayOfLastMonth());
        order.setEndTime(DateUtilsExtend.getEndDayOfLastMonth());
        BigDecimal lastMonthSumActualPrice = orderService.selectSumActualPrice(order);

        //本周销售总额、上周销售总额、同比情况
        order.setStartTime(DateUtilsExtend.getBeginDayOfWeek());
        order.setEndTime(DateUtilsExtend.getEndDayOfWeek());
        BigDecimal thisWeekSumActualPrice = orderService.selectSumActualPrice(order);

        order.setStartTime(DateUtilsExtend.getBeginDayOfLastWeek());
        order.setEndTime(DateUtilsExtend.getEndDayOfLastWeek());
        BigDecimal lastWeekSumActualPrice = orderService.selectSumActualPrice(order);

        resultMap.put("thisMonthCountPaid", thisMonthCountPaid);
        resultMap.put("lastMonthCountPaid", lastMonthCountPaid);
        resultMap.put("thisWeekCountPaid", thisWeekCountPaid);
        resultMap.put("lastWeekCountPaid", lastWeekCountPaid);
        resultMap.put("thisMonthSumActualPrice", thisMonthSumActualPrice);
        resultMap.put("lastMonthSumActualPrice", lastMonthSumActualPrice);
        resultMap.put("thisWeekSumActualPrice", thisWeekSumActualPrice);
        resultMap.put("lastWeekSumActualPrice", lastWeekSumActualPrice);

        basicResult.setData(resultMap);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}