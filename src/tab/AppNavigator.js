import {createAppContainer,createStackNavigator,createBottomTabNavigator,createMaterialTopTabNavigator } from 'react-navigation'
import HomeScreen from './HomeScreen' //引入首页
import Mine from './Mine'
import Detail from '../Detail'
import {Image} from 'react-native'

// 主页面
const TabNavigator=createBottomTabNavigator({
   //首页
  Home: {
    screen: HomeScreen,
    navigationOptions:{
      tabBarLabel: '首页',
    }
  },
  mine:{
    screen:Mine ,  //个人中心页面
    navigationOptions:{
      tabBarLabel: '个人中心',
      //下面是设置tabbar的图标
      // tabBarIcon:({focused})=>{
        
      // }
    }
  }
},{
  initialLayout:{  //设置tabBar的宽跟高 
      width:44,
      height:44,
  },
  lazy:false,  //懒加载
  //tabBarComponent：<Image/>,
  tabBarPosition: 'bottom',  
  //order:['Home','Mine'], //点开源码 可以看到是string类型数组
  tabBarOptions:{
    activeTintColor: 'red',//标签和图标选中颜色
    activeBackgroundColor: 'yellow',//导航选中背景色
    inactiveTintColor: '#000', //标签和图标未选中颜色
  }
})

//导航页面
const AppNavigator = createStackNavigator({
  TabNavigator:{
    screen:TabNavigator,
  },
  Detail: {
    screen: Detail,
  }
}, {
  initialRouteName: 'TabNavigator',
  defaultNavigationOptions:{
     header:null,  //隐藏标题栏目  个人觉得这里不好控制 
  }
})

export default AppNavigator  