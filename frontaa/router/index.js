// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录
import login from '@/views/login';
// 首页
import index from '@/views/index';
/**
 * 基础菜单
 */
// 商品管理
import Words from '@/views/goods/Words';
import OwnerWords from '@/views/goods/OwnerWords';
// 机器信息管理
import Info from '@/views/machine/Info';
// 货道信息管理
import MachineAisle from '@/views/machine/MachineAisle';
/**
 * 订单管理
 */
// 交易订单
import Property from '@/views/pay/Property';
/**
 * 系统管理
 */
// 用户管理
import user from '@/views/system/user';
import Owneruser from '@/views/system/Owneruser';
// 菜单管理
import Module from '@/views/system/Module';
// 角色管理
import Parking from '@/views/system/Parking';
import OwnerParking from '@/views/system/OwnerParking';
import ParkingTest from '@/views/system/ParkingTest';
// 公司管理
import Repair from '@/views/system/Repair';
import AdminRepair from '@/views/system/AdminRepair';
// 系统环境变量
import Variable from '@/views/system/Variable';
// 权限管理
import Permission from '@/views/system/Permission';
/**
 * 支付管理
 */
// 支付配置信息
import Pay from '@/views/machine/Pay';
import Paytest from '@/views/machine/Paytest';
// 支付配置
import Config from '@/views/pay/Config';
/**
 * 数据监控
 */
// 监控查询
import druidLogin from '@/views/druid/login';



//车辆出入管理
import CarInOut from'@/views/system/CarInOut';

// 业主支付配置信息
import OwnerPay from '@/views/machine/OwnerPay';

// 图表界面
import statistics from '@/views/charts/statistics';


// 机器信息管理
import ownerInfo from '@/views/machine/ownerInfo';

// 启用路由
Vue.use(Router);

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch((err) => err);
};

// 导出路由
export default new Router({
    routes: [{
        path: '/',
        name: '',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/login',
        name: '登录',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/index',
        name: '首页',
        component: index,
        iconCls: 'el-icon-tickets',
        children: [
          {

            path: '/goods/Words',
            name: '留言板',
            component: Words,
            meta: {
                requireAuth: true
            },

        },
         {

            path: '/goods/OwnerWords',
            name: '业主留言板',
            component: OwnerWords,
            meta: {
                requireAuth: true
            },

        },


          {
            path: '/machine/Info',
            name: '机器信息管理',
            component: Info,
            meta: {
                requireAuth: true
            }
        },


        {
          path: '/machine/ownerInfo',
          name: '用户改密码',
          component: ownerInfo,
          meta: {
              requireAuth: true
          }
      }

      , {
            path: '/machine/MachineAisle',
            name: '货道信息管理',
            component: MachineAisle,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/pay/Property',
            name: '员工管理',
            component: Property,
            meta: {
                requireAuth: true
            }
        },


          {
            path: '/system/user',
            name: '用户管理',
            component: user,
            meta: {
                requireAuth: true
            }
        },

            {
            path: '/system/Owneruser',
            name: '用户管理',
            component: Owneruser,
            meta: {
                requireAuth: true
            }
        },





          {
            path: '/system/Module',
            name: '菜单管理',
            component: Module,
            meta: {
                requireAuth: true
            }
        },



          {
            path: '/system/Parking',
            name: '车位管理',
            component: Parking,
            meta: {
                requireAuth: true
            }
        },


          {
            path: '/system/ParkingTest',
            name: '车位管理',
            component: ParkingTest,
            meta: {
                requireAuth: true
            }
        },




          {
            path: '/system/OwnerParking',
            name: '车位管理',
            component: OwnerParking,
            meta: {
                requireAuth: true
            }
        },



          {
            path: '/system/Repair',
            name: '公司管理',
            component: Repair,
            meta: {
                requireAuth: true
            }
        },
           {
            path: '/system/AdminRepair',
            name: '公司管理',
            component: AdminRepair,
            meta: {
                requireAuth: true
            }
        },{
            path: '/system/CarInOut',
            name: '车辆出入管理',
            component: CarInOut,
            meta: {
                requireAuth: true
            }
        },



          {
            path: '/system/Variable',
            name: '系统环境变量',
            component: Variable,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/Permission',
            name: '权限管理',
            component: Permission,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/machine/Pay',
            name: '支付配置信息',
            component: Pay,
            meta: {
                requireAuth: true
            }
        }

        ,
        {
            path: '/machine/OwnerPay',
            name: '业主支付配置信息',
            component: OwnerPay,
            meta: {
                requireAuth: true
            }
        },
              {
            path: '/machine/Paytest',
            name: '业主支付配置信息',
            component: Paytest,
            meta: {
                requireAuth: true
            }
        },




        {
            path: '/pay/Config',
            name: '支付配置',
            component: Config,
            meta: {
                requireAuth: true
            }
        },



          {
            path: '/druid/login',
            name: '监控查询',
            component: druidLogin,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/charts/statistics',
            name: '数据可视化',
            component: statistics,
            meta: {
                requireAuth: true
            }
        }]
    }]
})
