/**
 * 系统管理  公司管理
 */
<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right"  style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>报修处理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
<!--    <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">查看所有报修信息</el-button>-->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="">
        <el-input size="small" v-model="formInline.name" placeholder="输入业主姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="searchevent">搜索</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->

    <!-- 分页组件 -->
    <!-- 编辑界面 -->
    <el-table size="small" :data="repairdata" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">

      <el-table-column sortable prop="name" label="业主姓名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="idNumber" label="身份证"  show-overflow-tooltip>
      </el-table-column>
  <el-table-column sortable prop="apartment" label="单元"  show-overflow-tooltip>
      </el-table-column>
  <el-table-column sortable prop="doorId" label="门牌号"  show-overflow-tooltip>
      </el-table-column>
  <el-table-column sortable prop="tel" label="电话号码"  show-overflow-tooltip>
      </el-table-column>
  <el-table-column sortable prop="categorya" label="类型"  show-overflow-tooltip>
      </el-table-column>
  <el-table-column sortable prop="description" label="具体描述"  show-overflow-tooltip>
      </el-table-column>
 <el-table-column sortable prop="complete" label="维修进度"  show-overflow-tooltip>
      </el-table-column>
<el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        </template>
      </el-table-column>

<!--      <el-table-column align="center" label="操作" min-width="50">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button size="mini" type="danger" @click="deleteWords(scope.$index, scope.row)">删除此报修信息</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>
















<el-dialog  title="报修处理" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
          
          
           <el-form label-width="80px" ref="editForm" :model="editForm">

      <el-form-item label="类型" prop="complete">
          <el-radio v-model="editForm.complete" label="已完成"  @change="ifcomplete" >已完成</el-radio>
          <el-radio v-model="editForm.complete" label="维修出现问题" @change="ifcomplete">维修出现问题</el-radio>
            
        </el-form-item>
  <el-form-item label="问题" v-show="iscomplete">   
        <el-input size="small" v-model="editForm.inputform" placeholder="输入未完成的问题" ></el-input>
        </el-form-item>


           </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog("edit")'>取消</el-button>
        <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button>
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
  repairList,
  WordsDelete,
  repairdelete,
  repanameList,
  repairhandle
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      iscomplete:false,
      editForm:{
        complete:'已完成',
        inputform:'输入未完成的问题',
        id:''
      },
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加报修信息',
      repairdata:[],
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
    this.getdata()

    // this.editForm.name =  JSON.parse(localStorage.getItem('userdata')).name

  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
      ifcomplete:function(){

        this.iscomplete=!this.iscomplete;
      },

// 编辑、添加提交方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
         let requestData = {
            'complete':this.editForm.complete='已完成'?'已完成':this.editForm.inputform,
            'id':this.editForm.id
          }
          // 请求方法
          repairhandle(requestData)
            .then(res => {
              this.loading = false
              if (res.meta.status==200) {
                this.editFormVisible = false
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '数据保存成功！'
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
              this.$message.error('保存失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },


    //搜索data
    searchdata(parameter) {
      this.loading = true
      repanameList(parameter)
        .then(res => {
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.meta.msg
            })
          } else {
            this.repairdata = res.data
          }
        })
        .catch(err => {
          console.log(err)
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },
    //搜索事件
    searchevent() {
      this.searchdata(this.formInline)
    },


    // 获取公司列表
    getdata() {
      this.loading = true
      repairList()
        .then(res => {
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.meta.msg
            })
          } else {
            this.repairdata = res.data
          }
        })
        .catch(err => {
          console.log(err)
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },

    //显示编辑界面
    handleEdit: function(index,row) {
      this.editFormVisible = true
      this.iscomplete=false
      this.editForm.id=row.id
      this.editForm.complete='已完成'
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
              if (res.meta.status==200) {
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
    deleteWords(index, row) {
      // console.log(getCookie('user'))
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          repairdelete(row.id)
            .then(res => {
              if (res.meta.status == 200) {
                this.$message({
                  type: 'success',
                  message: '该报修信息已删除!'
                })
                this.getdata()
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('报修信息删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

  }
}
</script>

<style scoped>

</style>

