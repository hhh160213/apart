import axios from 'axios';
import { req } from './axiosFun';

/**
 * 支付配置信息
 **/
// 支付配置信息-获取支付配置信息列表
export const MachineConfigOwnerList = (params) => { return req("post", "/api/pay/getOwnerList", params) };
// 支付配置信息-缴费
export const MachineConfigPayCharge = (params) => { return req("post", "/api/pay/payCharge", params) };
export const MachineConfigPayAli = (params) => { return req("post", "/api/payCharge/confirm", params) };

// 支付配置信息-获取支付配置信息列表
export const MachineConfigList = (params) => { return req("post", "/api/pay/getList", params) };
// 支付配置信息-保存支付配置信息
export const MachineConfigSave = (params) => { return req("post", "/api/MachineConfig/save", params) };
// 支付配置信息-删除支付配置信息
export const MachineConfigDelete = (params) => { return axios.delete("/api/MachineConfig/delete?ids=" + params + "&token=" + localStorage.getItem('logintoken')).then(res => res.data) };

/**
 * 支付配置
 **/
// 支付配置-获取支付配置列表
export const ConfigList = (params) => { return req("post", "/api/Config/list", params) };
// 支付配置-保存支付配置
export const ConfigSave = (params) => { return req("post", "/api/property/update", params) };
// 支付配置-删除支付配置
export const ConfigDelete = (params) => { return axios.delete("/api/property/deleteProperty?id=" + params ).then(res => res.data) };

/**
 * 订单管理-交易订单
 **/
// 交易订单-获取交易订单列表
export const OrderList = (params) => { return req("post", "/api/property/getList", params) };
// 交易订单-s删除交易订单
export const OrderDelete = (params) => { return axios.delete("/api/property/deleteProperty?id=" + params ).then(res => res.data) };
// 交易订单-交易订单退款
export const OrderRefund = (params) => { return req("post", "/api/Order/refund", params) };


export const InitPassword = (params) => { return req("post", "/api/property/initPwd", params) };
