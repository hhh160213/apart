/**
 * 订单管理 物业员工管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>物业员工管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="formInline.name" placeholder="输入员工姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column align="center" type="index" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="编号" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="name" label="姓名"  show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="permission" label="职位"  show-overflow-tooltip>
      </el-table-column>

      <el-table-column align="center" label="操作" min-width="150" v-if="!canChange">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" @click="initPwd(scope.$index, scope.row)">初始化密码</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">离职</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="50%" @click="closeDialog('editForm')">
      <el-form label-width="120px" :model="editForm" ref="editForm">
        <el-row>
          <el-col :span="20">
            <el-form-item label="公司编号">
              <el-input size="small" v-model="editForm.id" auto-complete="off" placeholder="请输入名称" disabled></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="请输入姓名" :disabled='canChange' ></el-input>
            </el-form-item>
            <el-form-item label="职位" prop="permission" >
              <!-- <el-form-item label="职位" prop="havpermissioneCar"> -->
                <el-radio v-model="editForm.permission" label="普通员工" :disabled='canChange'>普通员工</el-radio>
                <el-radio v-model="editForm.permission" label="经理" :disabled='canChange'>经理</el-radio>
              <!-- </el-form-item> -->
              <!-- <el-input size="small" v-model="editForm.permission" auto-complete="off" placeholder="请输入商户号" :disabled='canChange'></el-input> -->
            </el-form-item>
            
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="公司名称">
              <el-input size="small" v-model="editForm.deptName" auto-complete="off" placeholder="请输入名称" disabled></el-input>
            </el-form-item>
            <el-form-item label="交易单号">
              <el-input size="small" v-model="editForm.transId" auto-complete="off" placeholder="请输入商户号" disabled></el-input>
            </el-form-item>
            <el-form-item label="子支付方式">
              <el-input size="small" v-model="editForm.subPayType" auto-complete="off" placeholder="请输入商户号" disabled></el-input>
            </el-form-item>
            <el-form-item label="终端编号">
              <el-input size="small" v-model="editForm.machineNo" auto-complete="off" placeholder="请输入微信子商户" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品价格">
              <el-input size="small" v-model="editForm.goodsPrice" auto-complete="off" placeholder="请输入应用ID" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品名称">
              <el-input size="small" v-model="editForm.goodsName" auto-complete="off" placeholder="请输入通知回调" disabled></el-input>
            </el-form-item>
            <el-form-item label="订单状态">
              <el-input size="small" v-model="editForm.orderStatus" auto-complete="off" placeholder="请输入加密类型" disabled></el-input>
            </el-form-item>
            <el-form-item label="商户号">
              <el-input size="small" v-model="editForm.mchId" auto-complete="off" placeholder="请输入商户签名密钥" disabled></el-input>
            </el-form-item>
            <el-form-item label="编辑用户">
              <el-input size="small" v-model="editForm.editUser" auto-complete="off" placeholder="请输入支付宝卖家" disabled></el-input>
            </el-form-item>
          </el-col> -->
        </el-row>
        <!-- <el-form-item label="备注">
          <el-input size="small" v-model="editForm.remark" auto-complete="off" placeholder="请输入微信证书路径" disabled></el-input>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer" :hidden='canChange'>
        <el-button size="small" @click='closeDialog("editForm")'>取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { OrderList, OrderDelete,ConfigSave,InitPassword } from '../../api/payMG'
import Pagination from '../../components/Pagination'
import { getCookie } from '../../utils/util'
export default {
  data() {
    return {
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '编辑',
      canChange:true,
      editForm: {
        id: '',
        name: '',
        pemission:''
      },
      formInline: {
        page: 1,
        limit: 200,
        name: ''
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
    console.log(getCookie('permission'))
    if(getCookie('permission')!='普通员工'){
      this.canChange=false
    }
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    // 获取员工列表
    getdata(parameter) {
      this.loading = true
      OrderList(parameter)
        .then(res => {
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.meta.msg
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
      this.editForm = row
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          ConfigSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.meta.status==200) {
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '员工信息保存成功！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
            })
            .catch(err => {
              this.editFormVisible = false
              this.loading = false
              this.$message.error('员工信息保存失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },
    initPwd(index, row){
      let pwd
      let params
      this.$prompt("请输入经理账户密码:",{
        confirmButtonText: "确定",

        cancelButtonText: "取消",

        inputValidator: (value) => {  

             if(!value) {//对输入内容校验

                  return '撤销审核的原因不能为空！';
             }
             pwd=value
        }
      }).then(()=>{
        params = {'pwd':pwd,'id':row.id,'adminAcc':getCookie('user')};
        InitPassword(params)
          .then(res=>{
            // console.log()
            if (res.meta.status==200 ) {
                this.$message({
                  type: 'success',
                  message: '密码重置成功'
                })
                // this.getdata(this.formInline)
              } else {
                this.$message.error(res.meta.msg)
              }
          })
      })
      .catch((err) => {
        // console.log();
      }); 


    },
    // 删除员工
    deleteUser(index, row) {
      // console.log(getCookie('user'))
      if(getCookie('permission')=='普通员工'){
        // console.log("??")
        this.$alert("权限不足")
        return
      }
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          OrderDelete(row.id)
            .then(res => {
              if (res.meta.status==200 ) {
                this.$message({
                  type: 'success',
                  message: '该员工已删除!'
                })
                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('员工删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 关闭编辑、增加弹出框
    closeDialog(formName) {
      this.editFormVisible = false
      this.$refs[formName].resetFields()
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

 
 

 