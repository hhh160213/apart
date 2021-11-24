/**
* 基础菜单 商品管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>留言板</el-breadcrumb-item>
    </el-breadcrumb>

<!---->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="">
        <el-input size="small" v-model="formInline.uaname" placeholder="输入业主姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="searchevent">搜索</el-button>
      </el-form-item>
    </el-form>


    <!-- 留言 -->
    <el-table size="small" :data="arrdata" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">

      <el-table-column sortable prop="uaname" label="用户" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="content" label="内容"  show-overflow-tooltip>
      </el-table-column>


    </el-table>


  </div>
</template>

<script>
import {deptList, deptSave, deptDelete, userList, parikingList, repanameList} from '../../api/userMG'
import Pagination from '../../components/Pagination'
import {MachineConfigList, MachineConfigSave, OrderDelete, OrderList} from "../../api/payMG";
import { wordSList,WordsDelete,SingleUserWord} from '../../api/userMG'
import {getCookie} from "../../utils/util";

export default {
  data() {
    return {

      formInline: {
        uaname: '',
      },
      arrdata: []


    }
  },


  // 注册组件
  components: {
    Pagination
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

  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    //搜索事件
    searchevent() {
      this.searchdata(this.formInline)
    },

    //搜索data
    searchdata(parameter) {
      this.loading = true
      SingleUserWord(parameter)
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
    //删除留言
    deleteWords(index, row) {
      // console.log(getCookie('user'))
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          WordsDelete(row.id)
            .then(res => {
              if (res.meta.status == 200) {
                this.$message({
                  type: 'success',
                  message: '该留言已删除!'
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
              this.$message.error('留言删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    reply(index, row) {
      this.$prompt('请输入需要回复的信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        this.$message({
          type: 'success',
          message: '你添加的回复是: ' + value
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }

  }



      /***
       * 调用接口，注释上面模拟数据 取消下面注释
       */








  }
</script>

<style scoped>

@media screen and (max-width: 3000px) and (min-width: 767px) {
  .el-card-d {
    float: left;
    margin-top: 20px;
    margin-left: 10%;
    width: 80%;
    height: 90%;
  }

}


</style>


