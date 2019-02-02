/*config example*/
xz:
	socket:
		enabled: false
		autoRun: false
		host: 127.0.0.1                       
		port: 9999                              
		bossThreadSize: 0                       
		workThreadSize: 0                       
		corePoolSize: 10                        
		maximumPoolSize: 100                    
		keepAliveTime: 10000                    
		taskQueueSize: 100                      
		scanPackage: com.xzcode                    
		idleCheckEnabled: true                  
		readerIdleTime: 5000                    
		writerIdleTime: 5000                    
		allIdleTime: 5000                        
		serverType: socket
		serializerType: msgpack
		websocketPath: /websocket             
		httpMaxContentLength: 65536             
	
