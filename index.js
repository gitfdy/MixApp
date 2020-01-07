import React from 'react';
import {AppRegistry, StyleSheet, Text, View, NativeModules, TouchableOpacity} from 'react-native';

const ReactToast = NativeModules.ReactToast

class HelloWorld extends React.Component {
    render() {
        return (
            <View style={styles.container}>
                <TouchableOpacity
                    onPress={() => {
                        ReactToast.show('Hello, World', ReactToast.SHORT)
                    }}>
                    <Text style={styles.hello}>Hello, World</Text>
                </TouchableOpacity>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
    },
    hello: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
});

AppRegistry.registerComponent('MyReactNativeApp', () => HelloWorld);
