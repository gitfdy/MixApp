import React from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    NativeModules,
    TouchableOpacity,
    TextInput,
    NativeEventEmitter,
    requireNativeComponent,
    UIManager,
    findNodeHandle
} from 'react-native';
import TextView from './js/NativeViewSource/TextView'
const ReactToast = NativeModules.ReactToast

class HelloWorld extends React.Component {

    state = {
        inputValue: ''
    }

    componentDidMount() {
        this.emitter = new NativeEventEmitter(ReactToast);
        this.emitter.addListener('toRN', (event) => {
            alert(event.content)
        })
    }
    _onClick=()=>{
    //如果TextView封装组件那边没有处理props就可以同过下面这种方式处理数据
    // RNModule.showToast(event.nativeEvent.message, RNModule.SHORT)
    UIManager.dispatchViewManagerCommand(
        findNodeHandle(this.nativeText),"1",["blue"]
    )

    }
    render() {
        const {inputValue} = this.state
        return (
            <View style={styles.container}>
                <TextInput placeholder={"填入提示信息fix3"} style={styles.input} onChangeText={inputValue => this.setState({
                    inputValue
                })}/>
                <TouchableOpacity
                    onPress={async () => {

                        ReactToast.showBoolean(inputValue, (callback) => {
                            console.log(callback)

                            ReactToast.show(callback.success, ReactToast.SHORT)
                        })
                        await ReactToast.usePromise(inputValue)
                    }}>
                    <Text style={styles.hello}>调用原生方法并返回</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={async () => {
                        let {content} = await ReactToast.usePromise(inputValue)
                        ReactToast.show(content, ReactToast.SHORT)
                    }}>
                    <Text style={styles.hello}>使用promise</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={() => {
                       // ReactToast.sendEvent()
                    }}>
                    <Text style={styles.hello}>执行原生中的发送监听方法</Text>
                </TouchableOpacity>

                   <TouchableOpacity
                                    onPress={() => {
                                        ReactToast.startActivity("com.example.mixapp.OtherActivity").then(x=>{
                                        alert(x)
                                        })
                                    }}>
                                    <Text style={styles.hello}>跳转到activity 并获取返回结果</Text>
                   </TouchableOpacity>
                   <TextView ref={v=>this.nativeText=v}  onClick={()=>this._onClick()} style={{ width: 200, height: 50, backgroundColor: "red" }} text="这是native视图" />

            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    hello: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    input: {
        height: 40,
        width: 260,
        borderWidth: 1
    }
});
//可注册多个App,在MyReactActivity中根据startReactApplication的第二个参数来判断加载的哪个应用
AppRegistry.registerComponent('MyReactNativeApp', () => HelloWorld);
