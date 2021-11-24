/**
* 系统管理  公司管理
*/
<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报修处理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加报修信息</el-button>
<!--    <el-button size="small" type="primary" @click="lookrepairdata()">查看所有报修信息</el-button>-->
    <el-form :inline="true" :model="formInline" class="user-search" v-if="disappear">
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="输入业主姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="searchdata">搜索</el-button>
      </el-form-item>
    </el-form>

    <!--时间线-->


    <!--查看所有报修信息-->
    <el-table :data="repairownerdata" style="margin-top: 15px">
      <el-table-column property="name" label="用户" width="80"></el-table-column>
      <el-table-column property="idNumber" label="身份证" width="80"></el-table-column>
      <el-table-column property="apartment" label="单元" width="80"></el-table-column>
      <el-table-column property="doorId" label="门牌号" width="80"></el-table-column>
      <el-table-column property="tel" label="电话号码" width="80"></el-table-column>
      <el-table-column property="categorya" label="类型" width="80"></el-table-column>
      <el-table-column property="description" label="具体描述" width="80"></el-table-column>
      <el-table-column property="complete" label="维修状态" width="80"></el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm">
        <el-form-item label="业主姓名" prop="name">
          <el-input size="small" v-model="editForm.name" placeholder="请输入业主姓名"></el-input>
        </el-form-item>

        <el-form-item label="身份证" prop="idNumber">
          <el-input size="small" v-model="editForm.idNumber" auto-complete="off" placeholder="请输入身份证"></el-input>
        </el-form-item>

        <el-form-item label="单元" prop="apartment">
          <el-input size="small" v-model="editForm.apartment" auto-complete="off" placeholder="请输入单元"></el-input>
        </el-form-item>
        <el-form-item label="门牌号" prop="doorId">
          <el-input size="small" v-model="editForm.doorId" auto-complete="off" placeholder="请输入门牌号(数字)"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input size="small" v-model="editForm.tel" auto-complete="off" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="categorya">
          <el-radio v-model="editForm.categorya" label="电工">水工</el-radio>
          <el-radio v-model="editForm.categorya" label="水工">电工</el-radio>
          <el-radio v-model="editForm.categorya" label="木工">木工</el-radio>
          <el-radio v-model="editForm.categorya" label="其他">其他</el-radio>
        </el-form-item>
        <el-form-item label="报修描述" prop="description">
          <el-input size="small" v-model="editForm.description" auto-complete="off" placeholder="请填写报修描述"></el-input>
        </el-form-item>
   

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("edit")'>取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitword('editForm')">提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  deptList,
  deptSave,
  deptDelete,
  repairSave,
  wordSubmit,
  wordSList,
  repanameList,
  userList,
  userallList, repairList

} from '../../api/userMG'
import Pagination from '../../components/Pagination'

export default {
  data() {
    return {
      disappear:false,
      // myappear: false,
      dialogTableVisible: false,
      repairownerdata: [],
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加报修信息',
      editForm: {
        name: '',
        idNumber: '',
        doorId: '',
        apartment: '',
        tel: '',
        categorya: '',
        description: '',
        // token: localStorage.getItem('logintoken')
      },

      formInline: {
        name: '',
      },

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

    // this.getdata(this.formInline) 

    this.editForm.name = JSON.parse(localStorage.getItem('userdata')).name
    this.formInline.name = JSON.parse(localStorage.getItem('userdata')).name
    console.log(this.formInline)
    console.log(this.formInline.name)
    this.getallthedata(this.formInline) 

    this.searchdata();
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    searchdata() {
      this.getdata(this.formInline)
      // this.myappear = true

    },
    lookrepairdata() {
      this.myappear = !this.myappear
    },
    // 获取公司列表
    getdata(parameter) {
      this.loading = true
      // 获取用户列表
      repanameList(parameter)
        .then(res => {
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.meta.msg
            })
          } else {
            this.repairownerdata = res.data

          }
        })
        .catch(err => {
          console.log(err)
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },
// 获取公司列表
    getallthedata(parameter) {
      this.loading = true 
      // 获取用户列表
      userallList(parameter)   
        .then(res => {
          console.log(res.data)
          // console.log(res)
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.meta.msg
            })
          } else {
            this.editForm.idNumber = res.data[0].idNumber
            this.editForm.doorId = res.data[0].doorId
            this.editForm.apartment = res.data[0].apartment
            this.editForm.tel = res.data[0].tel

          }
        })
        .catch(err => {
          console.log(err)
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },

    //显示编辑界面
    handleEdit: function () {
      this.editFormVisible = true
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false
    },
    // 编辑、增加页面保存方法
    submitword(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          // 请求方法
          repairSave(this.editForm)
            .then(res => {
              this.loading = false
              if (res.meta.status == 200) {
                this.editFormVisible = false
                this.$message({
                  type: 'success',
                  message: '报修提交成功！'
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
              this.$message.error('报修提交失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },


  }
}
</script>

<style scoped>

</style>

