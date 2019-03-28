## react-native-code-push添加一个应用命令：    
code-push app add ReactNativeCodePushDemo-ios ios react-native  
## RN的code-push测试跟生产环境的key  
Production对应的是生产的Deployment Key，Staging是开发时使用。  
 Name       │ Deployment Key                                                   │
├────────────┼──────────────────────────────────────────────────────────────────┤
│ Production │ QFzjyScqGXnfwNdkzCxIsBnaU7ekc79ceee2-a7d1-49f3-8759-0a8b003248f6 │
├────────────┼──────────────────────────────────────────────────────────────────┤
│ Staging    │ 2AicFWEwVJ3rxlFygYbQceaVg6qxc79ceee2-a7d1-49f3-8759-0a8b003248f6 
### 发布应用  
```bash  
code-push release-react appNameIos ios --m -d [Production|Staging]   # iOS版  
code-push release-react navigationApp android --m -d [Production|Staging] # android版  
eg:    
code-push release-react navigationApp android --t 0.0.2 --d Staging --description "骚2"  

### 查看 app 信息和上传状态
```bash
code-push deployment ls HCCP-android [--displayKeys|-k]  # 查看 app 信息
code-push deployment h HCCP-android [Production|Staging] # 查看上传版本状态
```bash 查看当前应用发布版本的所有信息 
code-push deployment history navigationApp Staging