/**
 * 系统管理  公司管理
 */
<template>
  <div v-loading="loading"
      element-loading-text="拼命加载中">
    <el-breadcrumb separator-class="el-icon-arrow-right"  style="margin-bottom: 15px">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>识别车牌</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-upload
      class="upload-demo"
      ref="upload"
      action=""
      accept=".jpg,.png,.JPG"
      :on-preview="handlePreview"
      :file-list="fileList"
      :auto-upload="false"
      :on-change="getFile">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
<!--      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
<!--      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
    </el-upload>
    <!--列表-->
    车牌号:<el-input size="small" type="text"  v-model="msg"  class="elinput"></el-input>
    <!-- 分页组件 -->
    <!-- 编辑界面 -->
    <div slot="footer" class="dialog-footer" style="padding:30px">
      <el-button size="small" @click='getIn()'>进入小区</el-button>
      <el-button size="small" type="primary" :loading="loading" class="title" @click="getOut()">离开小区</el-button>
    </div>
  </div>
</template>

<script>
import {
  carIn,
  carOut
} from '../../api/userMG'
import axios from 'axios'
// axios.defaults.baseURL="http://localhost:9999/";
export default {
  data() {
    return {
      loading:false,
      file: {},
      fileList: [],
      // headers:{'Content-Type':'application/x-www-form-urlencoded'},
      msg:'',
    }
  },
  // 注册组件
  components: {
  },
  /**
   * 数据发生改变
   */

  /**
   * 创建完毕
   */
  created() {
    // this.getdata()

    // this.editForm.name =  JSON.parse(localStorage.getItem('userdata')).name

  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    getFile(file, fileList) {
      // console.log(file);
      this.loading=true
      this.getBase64(file.raw).then(res => {
        // console.log(res)
        // debugger

        // let imgBase = res.replace(/\s+/g,'')
        var imgsop = res.replace(/^data:image\/\w+;base64,/, "");//去掉base64位头部
        var imgend=encodeURIComponent(imgsop)
        // console.log(imgsop+"----------------------------------------------------------------------")
        // console.log(imgend)
        // carNoCheck('image='+imgend)
        this.$axios({
          method:'post',
          url: '/baidu',
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          data:'image='+imgend
        }).then(reppo=>{
          this.loading=false
          if (reppo.data.words_result)
          {
            this.msg=reppo.data.words_result.number
          }else{
            this.msg=''
            this.$message.error('无法识别')
          }
            // console.log('请求结果'+reppo)
        }).catch(err=>{
          this.loading=false
          console.log(err)
          // this.$message.error('报修提交失败，请稍后再试！')

        })
      })
    },
    getBase64(file) {
      return new Promise(function(resolve, reject) {
        let reader = new FileReader();
        let imgResult = "";
        reader.readAsDataURL(file);
        reader.onload = function() {
          imgResult = reader.result;
        };
        reader.onerror = function(error) {
          reject(error);
        };
        reader.onloadend = function() {
          resolve(imgResult);
        };
      });
    },

    uploaddata(file){
      let temp = file.content;
      let index = temp.indexOf(",");
      let img = temp.slice(index + 1);
      let bsurl = 'https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate?access_token=24.f17d1c4b5002cd8f289edcc90c749c93.2592000.1633488496.282335-24804174';
      this.$axios({
        method:'post',
        url: bsurl,
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        data: "image=" + encodeURIComponent(img)
      }).then(res=>{
        if (res.data.words_result)
        {
          this.$message({
            type: 'info',
            message: '文件上传成功'
          })
          this.msg=res.data.words_result.number
        }
      })
    },


    handlePreview(file) {
      // console.log(file);
    },
    submitUpload(file) {
      console.log(file);

    },
    getIn(){
      if(!this.msg){
        this.$message.error("无车牌号信息")
        return
      }
      let data = {"carNo":this.msg}
      carIn(data).then(res=>{
        this.loading = false
              if (res.meta.status==200) {
                this.$message({
                  type: 'success',
                  message: '车牌信息保存成功！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
      })
      .catch(err=>{

      })
    },
    getOut(){
    if(!this.msg){
        this.$message.error("无车牌号信息")
        return
      }
      let data = {"carNo":this.msg}
      carOut(data).then(res=>{
        this.loading = false
              if (res.meta.status==200) {
                this.$message({
                  type: 'success',
                  message: '车牌信息保存成功！'
                })
              } else {
                this.$message({
                  type: 'info',
                  message: res.meta.msg
                })
              }
      })
    }











  }
}
</script>

<style scoped>
.elinput{
  width: 200px;
}


.el-upload__tip{
  font-weight: bold;
  font-size: 15px;
}
</style>

