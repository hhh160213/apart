/**
 * 系统管理  角色管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>查看车位</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->

    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">

      <el-table-column sortable prop="id" label="车位号" >
      </el-table-column>
      <el-table-column sortable prop="state" label="状态" >
      </el-table-column>
      <el-table-column sortable prop="owner" label="持有业主" >
      </el-table-column>
      <el-table-column sortable prop="address" label="业主单元地址" >
      </el-table-column>

    </el-table>
    <!-- 分页组件 -->
<!--    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>-->

    <!-- 菜单权限 -->

  </div>
</template>

<script>
import {
  parikingList,
  roleSave,
  roleDelete,
  rolePwd,
  RoleRightTree,
  RoleRightSave
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      menuAccessshow: false, //控制数据权限显示与隐藏
      title: '添加',
      editForm: {
        id: '',
        owner: '',
        address: '',
        state: ''
      },
      // rules 表单验证
      rules: {
        systemNo: [
          { required: true, message: '请输入系统编码', trigger: 'blur' }
        ],
        roleNo: [
          { required: true, message: '请输入角色代码', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ]
      },
      formInline: {
        id:'',
        owner:'',
        address:''
      },
      // 删除
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      userparm: [], //搜索权限
      listData: [], //用户数据
      // 数据权限
      RoleRight: [],
      RoleRightProps: {
        children: 'children',
        label: 'name'
      },
      // 选中
      checkmenu: [],
      //参数role
      saveroleId: '',
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 200,
        total: 200
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

  watch: {},

  /**
   * 创建完毕
   */

  created() {
    this.getdata(this.formInline)
  },

  /**
   * 里面的方法只有被调用才会执行
   */

  methods: {
    // 获取角色列表
    getdata(parameter) {
      // // 模拟数据
      // this.loading = false
      // this.listData = res.data
      // // 分页赋值
      // this.pageparm.currentPage = this.formInline.page
      // this.pageparm.pageSize = this.formInline.limit
      // this.pageparm.total = res.count
      // // 模拟数据结束


      parikingList(parameter)
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
            this.pageparm.currentPage = 1
            this.pageparm.pageSize = 200
            console.log(res)
            this.pageparm.total = res.data.length
          }
        })
        .catch(err => {
          this.loading = false
          this.$message.error('获取角色列表失败，请稍后再试！')
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
        this.title = '修改'
        this.editForm.id = row.id
        this.editForm.state = row.state
        this.editForm.owner = row.owner
        this.editForm.address = row.address
      } else {
        this.title = '添加'
        this.editForm.id = ''
        this.editForm.state = ''
        this.editForm.owner = ''
        this.editForm.address = ''
      }
    },
    add(){
      if(confirm('确定增加一个空车位吗')==true){
        this.editForm.id=0
        roleSave(this.editForm).then(res=>{
          if (res.meta.status==200) {
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '添加成功！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
        })
      }
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          roleSave(this.editForm)
            .then(res => {
              this.editFormVisible = false
              this.loading = false
              if (res.meta.status==200) {
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '信息保存成功！'
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
              this.$message.error('信息保存失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },
    // 删除角色
    deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          roleDelete(row.roleId)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '角色已删除！'
                })
                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('角色删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },

    // 选中菜单
    changemenu(arr) {
      let change = []
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].checked) {
          change.push(arr[i].id)
        }
      }
      this.checkmenu = change
    },
    // tree 递归
    changeArr(list) {
      var temptree = [],
        tree = [],
        items = []
      for (var i in list) {
        if (!temptree[list[i].id]) {
          var trow = {
            id: list[i].id,
            pId: list[i].pId,
            name: list[i].name,
            open: list[i].open,
            checked: list[i].checked,
            children: []
          }
          temptree[list[i].id] = trow
          items.push(trow)
        }
        if (list[i].uid > 0) {
          temptree[list[i].id]['children'].push({
            id: list[i].id,
            pId: list[i].pId,
            name: list[i].name,
            open: list[i].open,
            checked: list[i].checked,
            children: []
          })
        }
      }

      for (var j in items) {
        if (temptree[items[j].pId]) {
          temptree[items[j].pId]['children'].push(temptree[items[j].id])
        } else {
          tree.push(temptree[items[j].id])
        }
      }
      temptree = null
      items = null
      return tree
    },

    // 关闭编辑、增加弹出框
    closeDialog(dialog) {
      if (dialog == 'edit') {
        this.editFormVisible = false
      } else if (dialog == 'perm') {
        this.menuAccessshow = false
      }
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

