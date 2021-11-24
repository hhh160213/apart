/**
* 支付管理 支付配置信息
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>缴费信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="收费类别">
        <el-select v-model="formInline.payName" placeholder="收费类别">
          <el-option label="所有" value=""></el-option>
          <el-option label="水费" value="水费"></el-option>
          <el-option label="电费" value="电费"></el-option>
          <el-option label="煤气费" value="煤气费"></el-option>
          <el-option label="维修费" value="维修费"></el-option>
          <el-option label="停车费" value="停车费"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="支付状态">
        <el-select v-model="formInline.status" placeholder="支付状态">
          <el-option label="所有" value=""></el-option>
          <el-option label="已支付" value="已支付"></el-option>
          <el-option label="未支付" value="未支付"></el-option>
          <el-option label="逾期未付" value="逾期未付"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <!-- <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加</el-button> -->
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="编号" width="100">
      </el-table-column>
      <el-table-column sortable prop="payName" label="项目" width="200">
      </el-table-column>
      <el-table-column sortable prop="status" label="状态" width="100">
      </el-table-column>
      <el-table-column sortable prop="startTime" label="开始时间" width="100">
      </el-table-column>
      <el-table-column sortable prop="endTime" label="结束时间" width="100">
      </el-table-column>
      <el-table-column sortable prop="deadLine" label="支付时间" width="130">
      </el-table-column>
      <el-table-column sortable prop="apartment" label="单元" width="100">
      </el-table-column>
      <el-table-column sortable prop="doorId" label="门牌" width="100">
      </el-table-column>
      <el-table-column sortable prop="payMoney" label="支付金额" width="100">
      </el-table-column>
      <el-table-column sortable prop="payType" label="支付方式" width="200">
      </el-table-column>
      <el-table-column sortable prop="payPeople" label="支付人" width="100">
      </el-table-column>
      <el-table-column sortable prop="payPeopleTel" label="支付人电话" width="200">
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">缴费</el-button>
          <!-- <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click="closeDialog">
      <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="缴费id" prop="payId">
          <el-input size="small" v-model="editForm.payId" auto-complete="off" placeholder="请输入缴费Id"></el-input>
        </el-form-item>
        <el-form-item label="缴费人" prop="payPeople">
          <el-input size="small" v-model="editForm.payPeople" auto-complete="off" placeholder="请输入缴费人"></el-input>
        </el-form-item>
        <el-form-item label="缴费类别" prop="payType">
          <el-input size="small" v-model="editForm.payName" auto-complete="off" placeholder="请输入缴费类别"></el-input>
        </el-form-item>
          <el-form-item label="缴费金额" prop="payMoney">
          <el-input size="small" v-model="editForm.payMoney" auto-complete="off" placeholder="请输入缴费金额"></el-input>
        </el-form-item>
        <el-form-item label="订单编号" prop="paysubject">
          <el-input size="small" v-model="editForm.paysubject" auto-complete="off" placeholder="订单编号"></el-input>
        </el-form-item>

        <el-form-item label="支付方式" prop="payType">
          <el-select size="small" v-model="editForm.payType" placeholder="请选择" class="userRole">
            <el-option v-for="type in payType" :label="type.key" :value="type.value" :key="type.value"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button size="small" @click="pay()" type="primary">支付宝缴费</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  MachineConfigList,
  MachineConfigSave,
  MachineConfigOwnerList,
  MachineConfigPayCharge,
  MachineConfigDelete
} from '../../api/payMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加',
      payType: [
        { key: '现金', value: 1 },
        { key: '支付宝', value: 2 },
        { key: '微信', value: 3 },
        { key: 'POS通', value: 4 },
        { key: '闪付', value: 5 },
        { key: 'POS通C扫B', value: 6 },
        { key: '银联二维码', value: 8 },
        { key: '会员余额支付', value: 9 }
      ],
      editForm: {
        payId:'',
        payPeople:'',
        payName:'',
        payType: '',
        payMoney:'',
        paysubject:'',
        token: localStorage.getItem('logintoken')
      },
      // rules表单验证
      rules: {
        payId: [
          { required: true, message: '请输入缴费id', trigger: 'blur' }
        ],
        payPeople: [
          { required: true, message: '请输入缴费人', trigger: 'blur' }
        ],
        payName: [
          { required: true, message: '请输入缴费类别', trigger: 'blur' }
        ],
        payType: [
          { required: true, message: '请选择支付方式', trigger: 'blur' }
        ],
      },
      formInline: {
        page: 1,
        limit: 200,
        payName: '',
        status: '',
        // token: localStorage.getItem('logintoken')
      },
      // 删除部门
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  // 注册组件
  components: {
    Pagination
  },
  /**
   * 数据发生改变
   */

  /**
   * 创建完毕
   */
  created() {
    this.getdata(this.formInline)
    this.randomNumber();
    this.editForm.paysubject=this.randomNumber();
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
     setTimeDateFmt(s) {  // 个位数补齐十位数
  return s < 10 ? '0' + s : s;
},

 randomNumber() {
  const now = new Date()
  let month = now.getMonth() + 1
  let day = now.getDate()
  let hour = now.getHours()
  let minutes = now.getMinutes()
  let seconds = now.getSeconds()
  month = this.setTimeDateFmt(month)
  day = this.setTimeDateFmt(day)
  hour = this.setTimeDateFmt(hour)
  minutes = this.setTimeDateFmt(minutes)
  seconds = this.setTimeDateFmt(seconds)
  let orderCode = now.getFullYear().toString() + month.toString() + day + hour + minutes + seconds + (Math.round(Math.random() * 1000000)).toString();
  console.log(orderCode)
  return orderCode;
},




pay () {
      this.$axios.post('/api/payed/alipayop',
        {
          'outTradeNo':this.editForm.payId,
          'totalAmount':this.editForm.payMoney,
          'subject':this.editForm.payName
        })
        .then((res) =>{//返回成功调用此方法
          //console.info(res);
          document.querySelector('body').innerHTML = res.data;//查找到当前页面的body，将后台返回的form替换掉他的内容
          document.forms[0].submit();  //执行submit表单提交，让页面重定向，跳转到支付宝页面
        })
        .catch((err)=>{
          console.log(err)
        })
    },



    // 获取公司列表
    getdata(parameter) {
      MachineConfigOwnerList(parameter)
        .then(res => {
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.msg
            })
          } else {
            this.listData = res.data
            // 分页赋值
            this.pageparm.currentPage = this.formInline.page
            this.pageparm.pageSize = this.formInline.limit
            this.pageparm.total = res.data.length
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    // 搜索事件
    search() {
      this.getdata(this.formInline)
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '缴费单'
        this.editForm.payId=row.id
        this.editForm.payPeople = row.payPeople
        this.editForm.payName = row.payName
        this.editForm.payType = row.payType
        this.editForm.payMoney = row.payMoney
        // this.editForm.paysubject = row.paysubject
      } else {
        this.title = '缴费单'
        this.editForm.payId=''
        this.editForm.payPeople = ''
        this.editForm.payName = ''
        this.editForm.payType = ''
        this.editForm.payMoney = ''
        this.editForm.paysubject = ''
      }
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          MachineConfigPayCharge(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.data) {
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '缴费成功！'
                })
              } else  {
                this.$message({
                  type: 'info',
                  message: "缴费失败！"
                })
              }

            })
            .catch(err => {
              this.editFormVisible = false
              this.loading = false
              this.$message.error('支付配置信息保存失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    }
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>
