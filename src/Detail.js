import React ,{Component} from 'react' 
import {Platform, StyleSheet, Text, View} from 'react-native';
class Detail extends React.Component{

    render(){

        const {navigation}=this.props
        console.log('navigation_re',navigation)
        let para=navigation.getParam('paras',"defaultValue")
        console.log('get',para)
        return (
            <View style={{flex:1}}>
                <Text>我是text组件</Text>
            </View>
        )
    }
}
export default Detail