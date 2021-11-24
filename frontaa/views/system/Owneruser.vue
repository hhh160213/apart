/**
 * 系统管理 用户管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人基本信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->




      <div style="padding:10px">
          <el-button size="small"  @click="handleEdit">修改密码</el-button>
          <!-- <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button> -->
      </div>
      <el-table
        :data="userData"
        border
        v-loading="loading"
        prop="name"
        element-loading-text="拼命加载中"
        style="width: 100%">
        <el-table-column prop="name" label='属性'>
        </el-table-column>
        <el-table-column prop="date" label="内容">
        </el-table-column>
      </el-table>


    <!--列表-->
    <!-- <el-table size="small" @selection-change="selectChange" :data="userData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">

      <el-table-column align="center" sortable prop="name" label="姓名" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="sex" label="性别" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="age" label="年龄" width="80">
      </el-table-column>
      <el-table-column align="center" sortable prop="idNumber" label="身份证" min-width="150">
      </el-table-column>
      <el-table-column align="center" sortable prop="birthPlace" label="籍贯" min-width="100">
      </el-table-column>
   <el-table-column align="center" sortable prop="apartment" label="单元" min-width="80">
      </el-table-column>

   <el-table-column align="center" sortable prop="doorId" label="门牌号" min-width="100">
      </el-table-column>
  <el-table-column align="center" sortable prop="tel" label="电话" min-width="120">
      </el-table-column>

  <el-table-column align="center" sortable prop="haveCar" label="私家车" width="100">
      </el-table-column>
    </el-table> -->
    <!-- 分页组件 -->
<!--    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>-->
    <!-- 编辑界面 -->
   <el-dialog :title="title" :visible.sync="editFormVisible" width="30%" @click='closeDialog("edit")'>
     <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
       <el-form-item label="旧密码" prop="oldpwd">
         <el-input type="password" size="small" v-model="editForm.oldpwd" auto-complete="off" placeholder="请输入旧密码"></el-input>
       </el-form-item>

     </el-form>
     <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
       <el-form-item label="新密码" prop="newpwd">
         <el-input type="password" size="small" v-model="editForm.newpwd" auto-complete="off" placeholder="请输入新密码"></el-input>
       </el-form-item>

     </el-form>
     <el-form label-width="80px" ref="editForm" :model="editForm" :rules="rules">
       <el-form-item label="重复新密码" prop="newpwd2">
         <el-input type="password" size="small" v-model="editForm.newpwd2" auto-complete="off" placeholder="请重复新密码"></el-input>
       </el-form-item>

     </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button size="small" @click='closeDialog("edit")'>取消</el-button>
       <el-button size="small" type="primary" :loading="loading" class="title" @click="changePwd('editForm')">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 导入请求方法
import {
  userList,
  userSave,
  userDelete,
  userPwd,
  userExpireToken,
  userFlashCache,
  userLock,
  UserDeptTree,
  UserDeptSave,
  UserDeptdeptTree,
  UserChangeDept,
  changeUserPwd
} from '../../api/userMG'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      title: '修改密码',
      editFormVisible: false, //控制编辑页面显示与隐藏
      dataAccessshow: false, //控制数据权限显示与隐藏
      unitAccessshow: false, //控制所属单位隐藏与显示
      // 编辑与添加
      editForm: {
        name: '',
        age: '',
        idNumber: '',
        birthPlace: '',
        doorId: '',
        apartment: '',
        tel: '',
        sex: '',
        haveCar: '',
        oldpwd:'',
        newpwd:'',
        newpwd2:''
      },
      // 部门参数
      unitparm: {
        userIds: '',
        deptId: '',
        deptName: ''
      },
      // 选择数据
      selectdata: [],
      // rules表单验证
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        oldpwd: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newpwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        newpwd2: [
          { required: true, message: '请重复新密码', trigger: 'blur' }
        ],

        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' }
        ],
        idNumber: [
          { required: true, message: '请输入身份证号', trigger: 'blur' }
        ],
        birthPlace: [
          { required: true, message: '请输入籍贯', trigger: 'blur' }
        ],
        doorId: [
          { required: true, message: '请输入门牌号', trigger: 'blur' }
        ],
        apartment: [
          { required: true, message: '请输入单元地址', trigger: 'blur' }
        ],
        haveCar: [
          { required: true, message: '请输入有车情况', trigger: 'blur' }
        ],
        tel: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          {
            pattern: /^1(3\d|47|5((?!4)\d)|7(0|1|[6-8])|8\d)\d{8,8}$/,
            required: true,
            message: '请输入正确的手机号',
            trigger: 'blur'
          }
        ],
        userEmail: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          {
            pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            required: true,
            message: '请输入正确的邮箱',
            trigger: 'blur'
          }
        ],
        sex: [{ required: true, message: '请选择性别', trigger: 'blur' }]
      },
      // 删除用户
      seletedata: {
        ids: '',
        token: localStorage.getItem('logintoken')
      },
      // 重置密码
      resetpsd: {
        userId: '',
        token: localStorage.getItem('logintoken')
      },
      // 用户下线
      offline: {
        token: localStorage.getItem('logintoken')
      },
      // 请求数据参数
      formInline: {
        page: 1,
        limit: 10,
        name: '',
        tel: ''
      },
      //用户数据
      userData: [],
      // 数据权限
      UserDept: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      car1:'',
      car2:'',
      // 选中
      checkmenu: [],
      //参数role
      saveroleId: '',
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
    // 获取数据方法
    getdata(parameter) {
      this.loading = true
      // 模拟数据开始
      let res = {
        code: 0,
        msg: null,
        count: 12,
      }

      this.loading = false
      let newDate = window.localStorage.getItem('userdata')
      console.log("newDate"+newDate)
      newDate = JSON.parse(newDate)
      this.userData = [
        {'name':'姓名','date':newDate.name},
        {'name':'年龄','date':newDate.age},
        {'name':'性别','date':newDate.sex},
        {'name':'身份证号码','date':newDate.idNumber},
        {'name':'户籍所在地','date':newDate.birthPlace},
        {'name':'单元号','date':newDate.apartment},
        {'name':'门牌号','date':newDate.doorId},
        {'name':'电话号码','date':newDate.tel},
        {'name':'有无私家车','date':newDate.haveCar}
      ]

      this.formInline.name=newDate.name
      this.formInline.tel=newDate.tel
      // 获取用户列表
      userList(parameter).then(res => {
        if (res.meta.status != 200) {
          this.$message({
            type: 'info',
            message: res.meta.msg
          })
        } else {
          if(res.data[0].car1!=null){
            this.userData.push({'name':'车牌号1','date':res.data[0].car1})
          }
           if(res.data[0].car2!=null && res.data[0].car2!=res.data[0].car1){
            this.userData.push({'name':'车牌号2','date':res.data[0].car2})
          }

        }
      })
    },

    //搜索事件
    search() {
      this.getdata(this.formInline)
    },
    // 修改type
    editType: function(index, row) {
      this.loading = true
      let parm = {
        lock: '',
        userId: '',
        token: localStorage.getItem('logintoken')
      }
      parm.userId = row.userId
      let lock = row.isLock
      if (lock == 'N') {
        parm.lock = 'Y'
      } else {
        parm.lock = 'N'
      }
      // 修改状态
      userLock(parm).then(res => {
        this.loading = false
        if (res.success == false) {
          this.$message({
            type: 'info',
            message: res.msg
          })
        } else {
          this.$message({
            type: 'success',
            message: '状态修改成功'
          })
          this.getdata(this.formInline)
        }
      })
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      if (row != undefined && row != 'undefined') {
        this.title = '修改用户'
        this.editForm.id = row.id
        this.editForm.name = row.name
        this.editForm.age = row.age
        this.editForm.idNumber = row.idNumber
        this.editForm.birthPlace = row.birthPlace
        this.editForm.doorId = row.doorId
        this.editForm.apartment = row.apartment
        this.editForm.sex = row.sex
        this.editForm.tel = row.tel
        this.editForm.haveCar = row.haveCar

      } else {
        this.title = '修改密码'
        this.editForm.name = ''
        this.editForm.age = ''
        this.editForm.idNumber = ''
        this.editForm.birthPlace = ''
        this.editForm.doorId = ''
        this.editForm.apartment = ''
        this.editForm.sex = ''
        this.editForm.tel = ''
        this.editForm.haveCar = ''
        this.editForm.oldpwd = ''
        this.editForm.newpwd=''
        this.editForm.newpwd2=''
      }
    },

    changePwd(param){
      this.$refs[param].validate(valid=>{
        if(valid){
          if(this.editForm.newpwd!=this.editForm.newpwd2){
            this.$message({
              type: 'info',
              message: '两次新密码不一致'
            })
            return false
          }
          if(this.editForm.oldpwd==this.editForm.newpwd){
             this.$message({
              type: 'info',
              message: '新旧密码一致'
            })
            return false
          }
          let mydate = JSON.parse(window.localStorage.getItem('userdata'))
          debugger
          var paramer = {"id":mydate.id,"oldpwd":this.editForm.oldpwd,"newpwd":this.editForm.newpwd}
          changeUserPwd(paramer).then(res=>{
            this.loading = false
             if (res.meta.status==200) {
                this.editFormVisible = false
                this.getdata(this.formInline)
                this.$message({
                  type: 'success',
                  message: '密码修改成功！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
          })
        }else{

          return false
        }
      })
    },
    // 编辑、添加提交方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          // 请求方法
          userSave(this.editForm)
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
    handleClick(data, checked, node) {
      if (checked) {
        this.$refs.tree.setCheckedNodes([])
        this.$refs.tree.setCheckedNodes([data])
        this.unitparm.deptId = data.id
        this.unitparm.deptName = data.name
        //交叉点击节点
      } else {
      }
    },
    // 选择复选框事件
    selectChange(val) {
      this.selectdata = val
    },
    // 关闭编辑、增加弹出框
    closeDialog(dialog) {
      if (dialog == 'edit') {
        this.editFormVisible = false
      } else if (dialog == 'perm') {
        this.dataAccessshow = false
      } else if (dialog == 'unit') {
        this.unitAccessshow = false
      }
    },
    // 删除用户
    deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          // 删除
          userDelete(row.id)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '数据已删除!'
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
              this.$message.error('数据删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除！'
          })
        })
    },
    // 重置密码
    resetpwd(index, row) {
      this.resetpsd.userId = row.userId
      this.$confirm('确定要重置密码吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          userPwd(this.resetpsd)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '重置密码成功！'
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
              this.$message.error('重置密码失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消重置密码！'
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
      return change
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

