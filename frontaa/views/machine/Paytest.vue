<template>
  <div>
    <el-form :rules="rules" :model="payForm" class="loginRoot">
      <h3 class="loginTitle">商品支付信息</h3>
      <el-form-item prop="outTradeNo">
        <el-input type="text" v-model="payForm.outTradeNo" auto-complete="off" placeholder="请输入订单号"/>
      </el-form-item>
      <el-form-item prop="totalAmount">
        <el-input type="text" v-model="payForm.totalAmount" auto-complete="off" placeholder="请输入付款金额"/>
      </el-form-item>
      <el-form-item prop="subject">
        <el-input type="subject" v-model="payForm.subject" auto-complete="off" placeholder="请输入订单名称"/>
      </el-form-item>
      <el-button type="primary" @click="pay()" style="width: 100%">下单</el-button>
    </el-form>
  </div>
</template>

<script>
// import axios from '../../utils/myaxios.js'//这里面用了反向代理和权限token验证的,如果需要用到axios,你自己需要重新import一个,myaxios.js是自己新建的一个js文件

export default {
  name: "PayItemInformation",
  data() {
    return {
      payForm: {
        outTradeNo: '',//设置默认值 如果里面填写admin ，界面上就会默认为admin
        totalAmount: '',
        subject: ''
      },
      rules: {
        outTradeNo: [{required: true, message: '请输入订单号', trigger: 'blur'}],//required: true必填，失去焦点时验证，如果为空，提示信息为 '请输入用户名称'
        totalAmount: [{required: true, message: '请输入付款金额', trigger: 'blur'}],
        subject: [{required: true, message: '请输入订单名称', trigger: 'blur'}]
      },
    }
  },
  methods:{
    pay () {
      this.$axios.post('/api/payed/alipayop',
        {
          'outTradeNo':this.payForm.outTradeNo,
          'totalAmount':this.payForm.totalAmount,
          'subject':this.payForm.subject
        })
        .then((res) =>{//返回成功调用此方法
          //console.info(res);
          document.querySelector('body').innerHTML = res.data;//查找到当前页面的body，将后台返回的form替换掉他的内容
          document.forms[0].submit();  //执行submit表单提交，让页面重定向，跳转到支付宝页面
        })
        .catch((err)=>{
          console.log(err)
        })
    }
  }
}
</script>

<style scoped>

</style>
