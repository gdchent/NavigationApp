import {Image} from 'react-native'
import {createStackNavigator,createBottomTabNavigator,createMaterialTopTabNavigator } from 'react-navigation'
import HomeScreen from './HomeScreen' //引入首页
import Mine from './Mine'
import Detail from '../Detail'
import RecyclerView from '../RecyclerView'
// 主页面
const TabNavigator=createBottomTabNavigator({
   //首页
  Home: {
    screen: HomeScreen,
    navigationOptions:{
      tabBarLabel: '首页',
    }
  },
  Mine:{
    screen:Mine ,  //个人中心页面
    navigationOptions:{
      tabBarLabel: '个人中心',
      //下面是设置tabbar的图标
      tabBarIcon:({focused})=>{
        
      }
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
})

//导航页面
const AppNavigator = createStackNavigator({
  TabNavigator:{
    screen:TabNavigator,
  },
  Detail: {
    screen: Detail,
  },
  RecyclerView: {
    screen:RecyclerView,
  }
}, {
  initialRouteName: 'TabNavigator',
  defaultNavigationOptions:{
     header:null,  //隐藏标题栏目  个人觉得这里不好控制 
  }
})

export default AppNavigator