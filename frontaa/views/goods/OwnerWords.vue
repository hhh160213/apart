/**
* 基础菜单 商品管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>发表留言</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 留言 -->
    <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()" style="margin-bottom: 15px">发布一条友善的留言</el-button>
    <el-button size="small" type="primary" @click="lookarrdata()">查看/隐藏大家发布的留言</el-button>
<!--查看大家发布的留言-->
<!--    <el-dialog  :visible.sync="dialogTableVisible">-->
<!--      <el-table :data="arrdata">-->
<!--        <el-table-column property="uaname" label="用户" width="150"></el-table-column>-->
<!--        <el-table-column property="content" label="内容" width="200"></el-table-column>-->
<!--      </el-table>-->
<!--    </el-dialog>-->

    <el-dialog :title="title"  :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
      <el-form label-width="80px" ref="editForm" :model="editForm">
        <el-form-item label="当前用户" prop="uaname">
          <el-input size="small" v-model="editForm.uaname" auto-complete="off" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-input
          type="textarea"
          :rows="10"
          placeholder="请输入留言"
          v-model="editForm.content">
        </el-input>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click='closeDialog()'>取消</el-button>
        <el-button size="small" type="primary"   @click="submitword('editForm')">提交</el-button>
      </div>
    </el-dialog>

<!--    大家发布的留言-->
    <el-table size="small" :data="arrdata" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;"  v-if="appearance">

      <el-table-column sortable prop="uaname" label="用户" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="content" label="内容"  show-overflow-tooltip>
      </el-table-column>


    </el-table>
  </div>
</template>

<script>
// import {deptList, deptSave, deptDelete, userList, parikingList, userSave, wordSList} from '../../api/userMG'
import Pagination from '../../components/Pagination'
import {MachineConfigList, MachineConfigSave, OrderDelete, OrderList} from "../../api/payMG";
import {wordSubmit} from '../../api/userMG'
import {wordSList,WordsDelete,SingleUserWord} from '../../api/userMG'

import {getCookie} from "../../utils/util";

export default {
  data() {
    // let myname= JSON.parse(localStorage.getItem('userdata')).name

    return {
      appearance:false,
      title: '发表我的留言',
      arrdata: [],

      editFormVisible: false, //控制编辑页面显示与隐藏

      editForm: {
        uaname:  '',
        content: '这是一条留言',
      },


      dialogTableVisible: false,

    }
  },


  // 注册组件
  components: {
  }
  ,
  /**
   * 数据发生改变
   */

  /**
   * 创建完毕
   */
  created() {
    this.getdata()
    this.editForm.uaname =  JSON.parse(localStorage.getItem('userdata')).name

  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    // 获取员工列表
    getdata() {
      this.loading = true
      wordSList()
        .then(res => {
          this.loading = false
          if (res.meta.status != 200) {
            this.$message({
              type: 'info',
              message: res.meta.msg
            })
          } else {
            this.arrdata = res.data
          }
        })
        .catch(err => {
          console.log(err)
          this.loading = false
          this.$message.error('菜单加载失败，请稍后再试！')
        })
    },


    // 获取员工列表
    handleEdit: function() {
      this.editFormVisible = true
    },
    //删除留言
    closeDialog() {
        this.editFormVisible = false

    },
    lookarrdata(){
      this.appearance=!this.appearance;

    },
    // 编辑、添加提交方法
    submitword(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          // 请求方法
          wordSubmit(this.editForm)
            .then(res => {
              this.loading = false
              if (res.meta.status==200) {
                this.editFormVisible = false
                this.$message({
                  type: 'success',
                  message: '留言提交成功！'
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
              this.$message.error('留言失败，请稍后再试！')
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


