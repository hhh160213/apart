/**
* 左边菜单
*/
<template>
  <el-menu default-active="2" :collapse="collapsed" collapse-transition router :default-active="$route.path" unique-opened class="el-menu-vertical-demo" background-color="#334157" text-color="#fff" active-text-color="#ffd04b">
    <div class="logobox">
      <img class="logoimg" src="../assets/img/logo.png" alt="">
    </div>
    <el-submenu v-for="menu in allmenu" :key="menu.menuid" :index="menu.menuname">
      <template slot="title">
        <i class="iconfont" :class="menu.icon"></i>
        <span>{{menu.menuname}}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item v-for="chmenu in menu.menus" :index="'/'+chmenu.url" :key="chmenu.menuid">
          <i class="iconfont" :class="chmenu.icon"></i>
          <span>{{chmenu.menuname}}</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>
<script>
import { menu } from '../api/userMG'
export default {
  name: 'leftnav',
  data() {
    return {
      collapsed: false,
      allmenu: []
    }
  },
  // 创建完毕状态(里面是操作)
  created() {
    // 获取图形验证码
    let res;
    if(sessionStorage.getItem("logintype")=="1"){
      res = {
      success: true,
      data: [
        {
          menuid: 33,
          icon: 'li-icon-dingdanguanli',
          menuname: '人员管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 34,
              icon: 'icon-order-manage',
              menuname: '物业员工管理',
              hasThird: 'N',
              url: 'pay/Property',
              menus: null
            }
          ]
        },
        {
          menuid: 71,
          icon: 'li-icon-xitongguanli',
          menuname: '信息管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 72,
              icon: 'icon-cus-manage',
              menuname: '业主基本信息',
              hasThird: 'N',
              url: 'system/user',
              menus: null
            },

            {
              menuid: 73,
              icon: 'icon-news-manage',
              menuname: '车位管理',
              hasThird: 'N',
              url: 'system/Parking',
              menus: null
            },

            {
              menuid: 74,
              icon: 'icon-news-manage',
              menuname: '车辆出入管理',
              hasThird: 'N',
              url: 'system/CarInOut',
              menus: null
            },
               {
              menuid: 75,
              icon: 'icon-news-manage',
              menuname: '车牌识别',
              hasThird: 'N',
              url: 'system/ParkingTest',
              menus: null
            },





          ]
        },
        {
          menuid: 128,
          icon: 'li-icon-shangchengxitongtubiaozitihuayuanwenjian91',
          menuname: '交费管理',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 129,
              icon: 'icon-provider-manage',
              menuname: '缴费信息',
              hasThird: 'N',
              url: 'machine/Pay',
              menus: null
            },




          ]
        },


        {
          menuid: 330,
          icon: 'icon-cat-skuInfo',
          menuname: '反馈信息',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 331,
              icon: 'li-icon-shujujiankong',
              menuname: '留言信息',
              hasThird: 'N',
              url: 'goods/Words',
              menus: null
            },

            {
              menuid: 332,
              icon: 'li-icon-dingdanguanli',
              menuname: '报修大厅',
              hasThird: 'N',
              url: 'system/AdminRepair',
              menus: null
            }

          ]
        },
      ],

      msg: 'success'
    }
    }
    //业主模块
    else{
      res = {
      success: true,
      data: [
        {
          menuid: 71,
          icon: 'li-icon-xitongguanli',
          menuname: '信息查看',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 34,
              icon: 'icon-order-manage',
              menuname: '个人基本信息',
              hasThird: 'N',
              url: 'system/Owneruser',
              menus: null
            },





          ]
        },
        {
          menuid: 128,
          icon: 'li-icon-shangchengxitongtubiaozitihuayuanwenjian91',
          menuname: '缴费查看',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 129,
              icon: 'icon-provider-manage',
              menuname: '在线缴费',
              hasThird: 'N',
              url: 'machine/OwnerPay',
              menus: null
            },





          ]
        },


        {
          menuid: 330,
          icon: 'icon-cat-skuInfo',
          menuname: '反馈与报修',
          hasThird: null,
          url: null,
          menus: [
            {
              menuid: 331,
              icon: 'li-icon-shujujiankong',
              menuname: '发表留言',
              hasThird: 'N',
              url: 'goods/OwnerWords',
              menus: null
            },

            {
              menuid: 332,
              icon: 'li-icon-dingdanguanli',
              menuname: '我要报修',
              hasThird: 'N',
              url: 'system/Repair',
              menus: null
            }

          ]
        },

      ],
      msg: 'success'
    }
    }


          this.allmenu = res.data

    // menu(localStorage.getItem('logintoken'))
    //   .then(res => {
    //     console.log(JSON.stringify(res))
    //     if (res.success) {
    //       this.allmenu = res.data
    //     } else {
    //       this.$message.error(res.msg)
    //       return false
    //     }
    //   })
    //   .catch(err => {
    //     this.$message.error('菜单加载失败，请稍后再试！')
    //   })
    // 监听
    this.$root.Bus.$on('toggle', value => {
      this.collapsed = !value
    })
  }
}
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 240px;
  min-height: 400px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  border: none;
  text-align: left;
}
.el-menu-item-group__title {
  padding: 0px;
}
.el-menu-bg {
  background-color: #1f2d3d !important;
}
.el-menu {
  border: none;
}
.logobox {
  height: 40px;
  line-height: 40px;
  color: #9d9d9d;
  font-size: 20px;
  text-align: center;
  padding: 20px 0px;
}
.logoimg {
  height: 40px;
}
</style>
