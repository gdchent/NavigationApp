import React, { Component, PureComponent } from 'react';
import { StyleSheet, View, Text } from 'react-native'
import { TouchableOpacity } from 'react-native-gesture-handler';

//RecyclerView列表页
class RecyclerView extends PureComponent {

    render() {
        return (
            <View style={styles.container}>
                <TouchableOpacity
                    onPress={() => {
                        this.startActivity()
                    }}
                >
                    <Text>列表页面</Text>
                </TouchableOpacity>
            </View>
        )
    }

    //开启Activity页面
    startActivity = () => {
        const { navigation } = this.props
        navigation.navigate('Mine', {

        })
    }

    componentWillUnmount =()=>{
        console.log('recyclerview-unwill')
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
    }
})
export default RecyclerView