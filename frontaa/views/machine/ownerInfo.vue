/**
 * 个人信息
 */
<template>
<div>

   <el-form label-width="120px" :model="editForm" :rules="rules" ref="editForm">
        <el-form-item label="编号" prop="id">
          <el-input size="small" v-model="editForm.id" auto-complete="off" placeholder="请输入终端号" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="请输入配置序号" disabled></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="permission">
          <el-input size="small" v-model="editForm.permission" auto-complete="off" placeholder="请输入显示名称" disabled></el-input>
        </el-form-item>
      </el-form>
       <div>
          <el-button size="small" style="margin-left:100px" @click="openDialog">修改密码</el-button>
          <!-- <el-button size="small" type="primary" :loading="loading" class="title" @click="submitForm('editForm')">保存</el-button> -->
      </div>

      <el-dialog title="修改密码" :visible.sync="dialog" width="30%" @click="closeDialog">
        
        <el-form label-width="120px" :model="changePwd" :rules="rules" ref="changePwd">
          <el-form-item label="原密码" prop="oldPwd">
            <el-input size="small" type="password" v-model="changePwd.oldPwd" auto-complete="off" placeholder="请输入原密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPwd">
            <el-input size="small" type="password" v-model="changePwd.newPwd" auto-complete="off" placeholder="请输入新密码"></el-input>
          </el-form-item>
          <el-form-item label="重复新密码" prop="newPwd2">
            <el-input size="small" type="password" v-model="changePwd.newPwd2" auto-complete="off" placeholder="请重复新密码"></el-input>
          </el-form-item>
        
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="small" @click="closeDialog">取消</el-button>
          <el-button size="small" type="primary" class="title" @click="changPwd">保存</el-button>
        </div>
    </el-dialog>
</div>

</template>

<script>
import { getCookie } from '../../utils/util'
import {userUpdate,login} from '../../api/userMG'
export default {
  data(){
    return{
      editForm:{
        id:'',
        name:'',
        permission:''
      },
      changePwd:{
        oldPwd:'',
        newPwd:'',
        newPwd2:''
      },
      dialog:false,
      rules: {
        oldPwd: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' }
        ],
        newPwd2: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' }
        ]
      },
    }
  },
  created() {
    this.getdata(getCookie('user'),getCookie('pwd'))
  },
  methods: {
    getdata(acc,pwd){
      let params = {'account':acc,'password':pwd}
      login(params).then(res=>{
        this.editForm=res.data
      }).catch(err=>{
      })
    },
    openDialog(){
      this.changePwd.oldPwd=''
      this.changePwd.newPwd=''
      this.changePwd.newPwd2=''
      this.dialog = true
    },
    closeDialog(){
      this.dialog=false
    },
    changPwd(){
      if(this.changePwd.newPwd!=this.changePwd.newPwd2){
        this.$alert("两次密码不一致")
        return
      }
      // if(this.changePwd.oldPwd==this.changePwd.newPwd2){
      //   this.$alert("新密码不能和旧")
      //   return
      // }
      let param = {'id':this.editForm.id,'account':this.editForm.account,'password':this.changePwd.oldPwd,'newPwd':this.changePwd.newPwd}
      userUpdate(param).then(res=>{
          if (res.meta.status==200 ) {
                this.$message({
                  type: 'success',
                  message: '密码修改成功'
                })
                // this.getdata(this.formInline)
              } else {
                this.$message.error(res.meta.msg)
              }
              this.dialog=false
      }).catch(err=>{})
    }
  }
}


</script>

<style>

</style>

 